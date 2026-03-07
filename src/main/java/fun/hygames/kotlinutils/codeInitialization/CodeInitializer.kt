package `fun`.hygames.kotlinutils.codeInitialization

import com.google.common.reflect.ClassPath
import com.hypixel.hytale.event.IAsyncEvent
import com.hypixel.hytale.event.IBaseEvent
import com.hypixel.hytale.server.core.plugin.JavaPlugin
import `fun`.hygames.kotlinutils.HytaleKotlinUtils.Companion.infoLogger
import `fun`.hygames.kotlinutils.HytaleKotlinUtils.Companion.logger
import `fun`.hygames.kotlinutils.Scheduler
import `fun`.hygames.kotlinutils.codeInitialization.CodeInitializerUtil.ktInvoke
import `fun`.hygames.kotlinutils.codeInitialization.typeProcessor.MissingTypeProcessorException
import `fun`.hygames.kotlinutils.codeInitialization.typeProcessor.TypeProcessors
import `fun`.hygames.kotlinutils.internal.ErrorReport
import `fun`.hygames.kotlinutils.internal.ReflectionUtils
import `fun`.hygames.kotlinutils.invoke
import java.lang.invoke.MethodHandle
import java.lang.reflect.Method

object CodeInitializer {

    private var plugins : Int = 0
    private var registeredPlugins : Int = 0

    private val packages = HashSet<String>()

    private val pluginInstances = ArrayList<JavaPlugin>()
    val pluginsData = HashMap<JavaPlugin, PluginData>()

    fun <T : JavaPlugin> addPlugin(plugin: T) {
        registeredPlugins++

        pluginInstances.add(plugin)
        pluginsData[plugin] = PluginData()
        packages.add(plugin::class.java.packageName)

        infoLogger("Plugin ${plugin.name} added")
        if (registeredPlugins >= plugins) initialize()
    }

    fun setPluginsCount(count: Int){
        plugins = count
    }

    private fun initialize(){
        infoLogger("Initializing...")

        val classesOfPlugins = Array<ArrayList<Class<*>>>(pluginInstances.size) { ArrayList() } // Plugins -> Classes of plugin

        try {
            for (i in 0..<pluginInstances.size) {
                val plugin = pluginInstances[i]
                val pluginPackage = plugin::class.java.packageName
                val classes = classesOfPlugins[i]

                infoLogger("Loading classes for ${plugin.name}...")

                // TODO Optimize
                ClassPath.from(plugin::class.java.classLoader).allClasses
                    .filter { info -> packageHas(info.packageName, pluginPackage) }
                    .forEach { classes.add(it.load()) }

                infoLogger("Loaded ${classes.size} classes in ${plugin.name}")
            }

            for (i in 0..<pluginInstances.size){
                val plugin = pluginInstances[i]

                for (clazz in classesOfPlugins[i]) {
                    try {
                        processRegister(clazz, plugin)
                        processRun(clazz, plugin)
                        processEvents(clazz, plugin)
                    } catch (e: Exception){
                        e.printStackTrace()
                    }
                }
            }

            infoLogger("Linking nodes...")
            RunNodeManager.linkNodes()

            infoLogger("Run nodes...")
            RunNodeManager.startNode.run()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun processRegister(clazz: Class<*>, plugin: JavaPlugin){
        val register = clazz.getAnnotation(Register::class.java) ?: return

        if (register.type.isBlank()) return

        val processor = TypeProcessors[register.type] ?: throw MissingTypeProcessorException(register.type)

        processor.run(register, plugin, clazz)
    }

    private fun processRun(clazz: Class<*>, plugin: JavaPlugin){
        for (method in clazz.methods) {
            val run = method.getAnnotation(Run::class.java) ?: continue

            method.isAccessible = true

            val after = run.after.ifBlank { null }

            val node = RunNode(plugin, run.on, run.priority, after, method)
            val nodeName = run.name.ifBlank { plugin.name + ":" + clazz.getSimpleName() + ":" + method.name }

            RunNodeManager.nodes[nodeName] = node
        }
    }

    private fun processEvents(clazz: Class<*>, plugin: JavaPlugin){
        for (method in clazz.methods) {
            val eventAnnotation = method.getAnnotation(Event::class.java) ?: continue
            println("Registering ${method.name} in ${plugin.name}")

            val param = method.parameterTypes[0]

            if (param.isNestmateOf(IBaseEvent::class.java)) return

            val eventClass = param as Class<IBaseEvent<Any>>

            if (eventAnnotation.async) {
                processEventsAsync(eventAnnotation, plugin, method, param as Class<IAsyncEvent<Any>>)
            } else {
                processEventsSync(eventAnnotation, plugin, method, eventClass)
            }
        }
    }

    private fun processEventsSync(eventAnnotation : Event, plugin: JavaPlugin, method: Method, eventClass: Class<IBaseEvent<Any>>){
        val consumer = ReflectionUtils.createConsumer(method, eventClass)

        when (eventAnnotation.type) {
            EventType.GLOBAL -> plugin.eventRegistry.registerGlobal(eventAnnotation.priority, eventClass, consumer)
            EventType.KEYED -> {
                if (eventAnnotation.key.isBlank()) {
                    ErrorReport("Used EventType.KEYED, without key. Use EventType.GLOBAL. ${method.javaClass.simpleName}:${method.name}")
                    return
                }

                plugin.eventRegistry.register(eventAnnotation.priority, eventClass, eventAnnotation.key, consumer)
            }
            EventType.UNHANDLED -> plugin.eventRegistry.registerUnhandled(eventAnnotation.priority, eventClass, consumer)
        }
    }

    private fun processEventsAsync(eventAnnotation : Event, plugin: JavaPlugin, method: Method, eventClass: Class<IAsyncEvent<Any>>){
        when (eventAnnotation.type) {
            EventType.GLOBAL -> plugin.eventRegistry.registerAsyncGlobal(eventAnnotation.priority, eventClass) { event -> event }
            EventType.KEYED -> {
                if (eventAnnotation.key.isBlank())
                    plugin.eventRegistry.register(eventAnnotation.priority, eventClass) { event -> method.invoke(event) }
                else
                    plugin.eventRegistry.register(eventAnnotation.priority, eventClass, eventAnnotation.key) { event -> method.invoke(event) }
            }
            EventType.UNHANDLED -> plugin.eventRegistry.registerUnhandled(eventAnnotation.priority, eventClass) { event -> method.invoke(event) }
        }
    }

    private fun packageHas(string: String, pluginPackage: String): Boolean {
        return string.contains(pluginPackage)
    }

    class PluginData {
        var scheduler: Scheduler? = null
    }
}