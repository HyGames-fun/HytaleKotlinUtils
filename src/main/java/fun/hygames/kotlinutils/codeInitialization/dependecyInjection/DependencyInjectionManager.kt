package `fun`.hygames.kotlinutils.codeInitialization.dependecyInjection

import com.hypixel.hytale.logger.HytaleLogger
import com.hypixel.hytale.server.core.plugin.JavaPlugin
import com.hypixel.hytale.server.core.universe.PlayerRef
import `fun`.hygames.kotlinutils.Players
import `fun`.hygames.kotlinutils.Scheduler
import kotlin.reflect.KClass

object DependencyInjectionManager {
    private val dependencyInjectionByParameterClass = HashMap<Class<*>, ParameterInjection>()
    private val extraDependencyInjectionByInjector = HashMap<String, HashMap<Class<*>, ParameterInjection>>()

    internal fun getParameterInjection(clazz: Class<*>, inject: Inject) : ParameterInjection? {
        if (inject.injector.isBlank())
            return dependencyInjectionByParameterClass[clazz]

        val byInjector = extraDependencyInjectionByInjector[inject.injector]
            ?: throw Exception("Injector ${inject.injector} not exist!")

        val parameterInjection = byInjector[clazz]
            ?: throw Exception("Injector ${inject.injector} not exist for class ${clazz.simpleName}!")

        return parameterInjection
    }

    fun register(clazz: KClass<*>, parameterInjection: ParameterInjection){
        dependencyInjectionByParameterClass[clazz.java] = parameterInjection
    }

    fun register(injector: String, clazz: KClass<*>, parameterInjection: ParameterInjection){
        val map = extraDependencyInjectionByInjector[injector]
        if (map == null) extraDependencyInjectionByInjector[injector] = HashMap()

        extraDependencyInjectionByInjector[injector]!![clazz.java] = parameterInjection
    }

    init {
        register(Scheduler::class) { node, parameter, args ->
            val pluginData = node.pluginData
            if (pluginData.scheduler == null)
                pluginData.scheduler = Scheduler(node.plugin!!)
            return@register pluginData.scheduler
        }

        register(JavaPlugin::class) { node, _, _ -> node.plugin }

        register(HytaleLogger::class) { node, _, _ -> node.plugin!!.logger }

        register(PlayerRef::class) { _, _, args ->
            if (args.contains("_player")) return@register args["_player"]
        }

        register("byName", PlayerRef::class) { node, parameter, args ->
            val name = parameter.getAnnotation(Inject::class.java).name

            val username = args[name] as String
            return@register Players[username]
        }

        registerSimple(Int::class)
        registerSimple(Float::class)
        registerSimple(Double::class)
        registerSimple(String::class)
    }

    private fun registerSimple(clazz: KClass<*>){
        register(clazz) { _, parameter, args ->
            val name = parameter.getAnnotation(Inject::class.java).name

            return@register args[name]
        }
    }
}