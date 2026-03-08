package `fun`.hygames.kotlinutils

fun <T> Collection<T>.stringIfNotEmpty(empty: String = "", block: Collection<T>.() -> String) : String {
    if (isEmpty()) return empty

    return block.invoke(this)
}