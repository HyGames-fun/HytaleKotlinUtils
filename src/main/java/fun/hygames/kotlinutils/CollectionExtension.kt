package `fun`.hygames.kotlinutils

inline fun <T> Collection<T>.stringIfNotEmpty(empty: String = "", crossinline block: Collection<T>.() -> String) : String {
    if (isEmpty()) return empty

    return block.invoke(this)
}