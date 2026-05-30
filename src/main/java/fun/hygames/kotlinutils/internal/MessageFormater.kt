package `fun`.hygames.kotlinutils.internal

internal object MessageFormater {
}

internal data class MessageFoldTree(val message: String) {
    private val children = HashMap<String, MessageFoldTree>()

    fun branch(vararg messages: String) {
        branch(messages, 0)
    }

    private fun branch(messages: Array<out String>, index: Int) {
        if (index >= messages.size) return

        val child = children.computeIfAbsent(messages[index]) { MessageFoldTree(messages[index]) }

        child.branch(messages, index + 1)
    }

    fun buildString(level: Int = 0, last: Boolean = false) : String {
        var result = ""

        for (i in 1..level){
            if (i == level) {
                result += if (last) "\\--  " else "|--  "
                continue
            }

            result += "|    "

        }
        result += message

        var counter = 0
        for (tree in children.values){
            counter++
            result += "\n" + tree.buildString(level + 1, counter == children.size)
        }

        return result
    }


}
