package `fun`.hygames.kotlinutils.codeInitialization

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
import java.lang.reflect.Method
import java.lang.reflect.Modifier

data class RunNode(
    val runOn: RunOn,
    val priority: Int,
    val after: String,

    val method : Method?,
    val subNodes: Int2ObjectOpenHashMap<ArrayList<RunNode>>
) {
    fun run(){
        val sorted = ArrayList(subNodes.keys)

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

    private fun runMethods(priorityIndex: Int){
        for (subNode in subNodes[priorityIndex]) {
            if (subNode.method == null) continue

            try {
                if (Modifier.isStatic(subNode.method.modifiers)) {
                    subNode.method.invoke(null)
                } else {
                    subNode.method.invoke(subNode.method.declaringClass.kotlin.objectInstance)
                }
            } catch (e: Exception) {
                println(subNode.method.declaringClass.getSimpleName() + ":" + subNode.method.name)
                e.printStackTrace()
            }
        }

        for (subNode in subNodes[priorityIndex]){
            subNode.run()
        }
    }
}
