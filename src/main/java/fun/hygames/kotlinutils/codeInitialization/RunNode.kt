package `fun`.hygames.kotlinutils.codeInitialization

import com.hypixel.hytale.server.core.plugin.JavaPlugin
import `fun`.hygames.kotlinutils.codeInitialization.dependecyInjection.DependencyInjectionManager
import `fun`.hygames.kotlinutils.codeInitialization.dependecyInjection.Inject
import `fun`.hygames.kotlinutils.internal.ErrorReport
import `fun`.hygames.kotlinutils.internal.MethodUtil.ktInvoke
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
import java.lang.reflect.Method

data class RunNode(
    val plugin: JavaPlugin?,
    val runOn: RunOn,
    val priority: Int,
    val after: String?,

    val method : Method?
) {
    var subNodes: Int2ObjectOpenHashMap<ArrayList<RunNode>>? = null

    var hasPositivePriority = false

    fun run() {
        if (subNodes == null) return
        val sorted = ArrayList(subNodes!!.keys)

        if (hasPositivePriority) {
            sorted.sort()
            // Positive
            for (i in sorted) {
                if (i < 0) continue
                runMethods(i)
            }
        } else
            sorted.sortDescending()

        // Negative
        if (hasPositivePriority) sorted.reverse()

        for (i in sorted) {
            val value = sorted[i]
            if (value >= 0) continue
            runMethods(value)
        }
    }

    private fun runMethods(priorityIndex: Int) {
        if (subNodes == null) return
        for (subNode in subNodes!![priorityIndex]) {
            if (subNode.method == null) continue

            try {
                subNode.invokeWithInjection(emptyMap())
            } catch (e: Exception) {
                println(subNode.method.declaringClass.getSimpleName() + ":" + subNode.method.name)
                e.printStackTrace()
            }
        }

        for (subNode in subNodes!![priorityIndex]) {
            subNode.run()
        }
    }

    internal fun addSubNode(node: RunNode){
        if (subNodes == null)
            subNodes = Int2ObjectOpenHashMap()

        if (!subNodes!!.containsKey(node.priority)){
            subNodes!![node.priority] = ArrayList()
        }

        subNodes!![node.priority].add(node)

        if (node.priority >= 0) {
            hasPositivePriority = true
        }
    }

    fun name() : String {
        if (plugin == null) return ""
        if (method == null) return ""
        return plugin.name + ":" + method.declaringClass.simpleName + ":" + method.name
    }

    val pluginData : CodeInitializer.PluginData
        get() = CodeInitializer.pluginsData[plugin]!!

    internal fun invokeWithInjection(arguments: Map<String, Any>) {
        if (plugin == null) return
        if (method == null) return

        if (method.parameterCount == 0) {
            method.ktInvoke()
            return
        }

        val args = createArgs(method, arguments)

        try {
            method.ktInvoke(*args)
        } catch (exception: Exception) {
            if (exception.cause == null) {
                ErrorReport("Error in node method invoke. No cause. Probably, error in RunNode.invokeMethodWithInjection. Please, send feedback with logs")
                exception.printStackTrace()
                return
            }
            val element = exception.cause!!.stackTrace[0]
            ErrorReport(
                "Error in node method invoke. ${element.fileName}:${element.methodName}:${element.lineNumber}. Arguments: ${
                    method.parameterTypes.map { type -> type.simpleName }.toList()
                }."
            )
            exception.cause!!.printStackTrace()
        }
    }

    private fun createArgs(method: Method, arguments: Map<String, Any>) : Array<Any?> {
        val args = arrayOfNulls<Any?>(method.parameterCount)

        val parameters = method.parameters
        val parametersTypes = method.parameterTypes

        for (i in 0..<parameters.size) {
            val type = parametersTypes[i]
            val inject = parameters[i].getAnnotation(Inject::class.java)

            val parameterInjection = try {
                DependencyInjectionManager.getParameterInjection(type, inject) ?: continue
            } catch (e: Exception){
                ErrorReport("Injection error in node ${name().ifBlank { "Undefined" }}. Message: \"${e.message}\"")
                e.printStackTrace()
                continue
            }

            args[i] = parameterInjection.inject(this, parameters[i], arguments)
        }
        return args
    }
}
