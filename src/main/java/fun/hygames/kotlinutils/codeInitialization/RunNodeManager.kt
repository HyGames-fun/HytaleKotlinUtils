package `fun`.hygames.kotlinutils.codeInitialization

object RunNodeManager {

    val startNode = RunNode(null, RunOn.START, 0, null, null)
    val stopNode = RunNode(null,RunOn.STOP, 0, null, null)

    val nodes = HashMap<String, RunNode>()

    fun linkNodes() {
        for (node in nodes.values) {
            if (node.runOn == RunOn.TEST) continue

            var parentNode = when (node.runOn) {
                RunOn.START -> startNode
                RunOn.STOP -> stopNode
            }

            if (node.after != null) {
                val afterNode = nodes[node.after]
                if (afterNode != null) parentNode = afterNode
            }

            parentNode.addSubNode(node)
        }
    }
}