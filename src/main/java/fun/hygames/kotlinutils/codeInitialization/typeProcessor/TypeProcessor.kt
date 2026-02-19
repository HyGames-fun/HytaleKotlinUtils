package `fun`.hygames.kotlinutils.codeInitialization.typeProcessor

import `fun`.hygames.kotlinutils.codeInitialization.Register

interface TypeProcessor {
    fun run(register: Register, clazz: Class<*>)
}