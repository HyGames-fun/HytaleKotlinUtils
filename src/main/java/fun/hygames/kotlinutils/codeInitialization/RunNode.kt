package `fun`.hygames.kotlinutils.codeInitialization

import com.hypixel.hytale.logger.HytaleLogger
import com.hypixel.hytale.server.core.plugin.JavaPlugin
import `fun`.hygames.kotlinutils.HytaleKotlinUtils
import `fun`.hygames.kotlinutils.Scheduler
import `fun`.hygames.kotlinutils.codeInitialization.CodeInitializerUtil.ktInvoke
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
import java.lang.reflect.Method
import java.util.logging.Level

data class RunNode(
    val plugin: JavaPlugin?,
    val runOn: RunOn,
    val priority: Int,
    val after: String?,

    val method : Method?,
) {
    var subNodes: Int2ObjectOpenHashMap<ArrayList<RunNode>>? = null

    fun run() {
        if (subNodes == null) return
        val sorted = ArrayList(subNodes!!.keys)

        sorted.sort()

        // Positive
        for (i in sorted) {
            if (i < 0) continue
            runMethods(i)
        }

        // Negative
        for (i in sorted.indices.reversed()) {
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
                invokeMethodWithInjection(subNode)
            } catch (e: Exception) {
                println(subNode.method.declaringClass.getSimpleName() + ":" + subNode.method.name)
                e.printStackTrace()
            }
        }

        for (subNode in subNodes!![priorityIndex]) {
            subNode.run()
        }
    }

    fun addSubNode(node: RunNode){
        subNodes = Int2ObjectOpenHashMap()

        if (!subNodes!!.containsKey(node.priority)){
            subNodes!![node.priority] = ArrayList()
        }

        subNodes!![node.priority].add(node)
    }

    val pluginData : CodeInitializer.PluginData
        get() = CodeInitializer.pluginsData[plugin]!!

    companion object {
        private fun invokeMethodWithInjection(runNode: RunNode) {
            try {
                if (runNode.plugin == null) return
                if (runNode.method == null) return

                val method = runNode.method

                if (method.parameterCount == 0) {
                    method.ktInvoke()
                    return
                }

                val args = Array<Any?>(method.parameterCount) { _ -> null }

                val parameters = method.parameters
                val parametersTypes = method.parameterTypes

                for (i in 0..<parameters.size) {
                    val type = parametersTypes[i]
                    val di = DependencyInjectionManager.dependencyInjectionByParameterClass[type] ?: continue

                    args[i] = di.inject(runNode, parameters[i])
                }

                method.ktInvoke(*args)
            } catch (exception: Exception){
                HytaleKotlinUtils.logger.at(Level.SEVERE).log("Error in node method invoke. \n  Info Method: ${runNode.method!!.name},\n     Method Arguments: ${runNode.method.parameterTypes.map { a -> a.simpleName }.toList()}")
                exception.printStackTrace()
            }
        }
    }
}
