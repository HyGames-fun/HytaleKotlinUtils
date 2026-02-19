package `fun`.hygames.kotlinutils.codeInitialization

import com.google.common.reflect.ClassPath
import `fun`.hygames.kotlinutils.codeInitialization.typeProcessor.TypeProcessors
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap

object CodeInitializer {

    private var plugins : Int = 0
    private var registeredPlugins : Int = 0

    private val packages = HashSet<String>()
    private val pluginClasses = HashSet<Class<*>>()

    private val startNode = RunNode(RunOn.START, 0, "", null, Int2ObjectOpenHashMap())
    private val stopNode = RunNode(RunOn.STOP, 0, "", null, Int2ObjectOpenHashMap())

    private val nodes = HashMap<String, RunNode>()

    fun addPlugin(packageName: String, pluginClass: Class<*>){
        registeredPlugins++

        packages.add(packageName)
        pluginClasses.add(pluginClass)

        if (registeredPlugins >= plugins) initialize()
    }

    private fun initialize(){
        println("Initializing...")
        val classInfos = HashSet<ClassPath.ClassInfo>()

        try {
            for (clazz in pluginClasses) {
                // TODO Optimize
                println("Loading classes for ${clazz.simpleName}" )
                classInfos.addAll(ClassPath.from(clazz.classLoader).allClasses)
            }

            for (info in classInfos){
                if (!collectionHas(packages, info.packageName)) continue

                val clazz: Class<*> = info.load()

                processRegister(clazz)
                processRun(clazz)
            }

            println("Linking noes...")
            linkNodes()

            println("Run nodes...")
            startNode.run()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun processRegister(clazz: Class<*>){
        val register = clazz.getAnnotation(Register::class.java) ?: return

        if (register.type.isBlank()) return

        val processor = TypeProcessors[register.type] ?: return

        processor.run(register, clazz)
    }

    private fun processRun(clazz: Class<*>){
        for (method in clazz.methods) {
            val run = method.getAnnotation(Run::class.java) ?: continue

            println(method.name)

            method.isAccessible = true

            val node = RunNode(run.on, run.priority, run.after, method, Int2ObjectOpenHashMap())
            val nodeName = run.name.ifBlank { clazz.getSimpleName() + ":" + method.name }

            nodes[nodeName] = node
        }
    }

    private fun linkNodes() {
        for (node in nodes.values) {
            if (node.runOn == RunOn.TEST) continue

            var parentNode = when (node.runOn) {
                RunOn.START -> startNode
                RunOn.STOP -> stopNode
            }

            if (node.after.isNotBlank()) {
                val afterNode = nodes[node.after]
                if (afterNode != null) parentNode = afterNode
            }

            if (!parentNode.subNodes.containsKey(node.priority)){
                parentNode.subNodes[node.priority] = ArrayList()
            }

            parentNode.subNodes[node.priority].add(node)

        }
    }

    private fun collectionHas(collection: MutableCollection<String>, string: String): Boolean {
        for (s in collection) {
            if (string.contains(s)) return true
        }
        return false
    }
}