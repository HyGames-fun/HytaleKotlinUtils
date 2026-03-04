package `fun`.hygames.kotlinutils.codeInitialization.typeProcessor

import `fun`.hygames.kotlinutils.HytaleKotlinUtils.Companion.infoLogger
import `fun`.hygames.kotlinutils.invoke

object TypeProcessors {
    private val map = HashMap<String, TypeProcessor>()

    fun register(name: String, typeProcessor: TypeProcessor) {
        map[name] = typeProcessor
        infoLogger("Registered \"${name}\" type processor")
    }

    operator fun get(name: String) : TypeProcessor? {
        return map[name]
    }
}