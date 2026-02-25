package `fun`.hygames.kotlinutils

fun Class<*>.inheritsFrom(target: Class<*>): Boolean {
    return this == target ||
            this.superclass?.inheritsFrom(target) == true ||
            this.interfaces.any { it.inheritsFrom(target) }
}