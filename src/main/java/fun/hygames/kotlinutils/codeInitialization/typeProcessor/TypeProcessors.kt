package `fun`.hygames.kotlinutils.codeInitialization.typeProcessor

object TypeProcessors {
    private val map = HashMap<String, TypeProcessor>()

    fun register(name: String, typeProcessor: TypeProcessor){
        map[name] = typeProcessor
    }

    operator fun get(name: String) : TypeProcessor? {
        return map[name]
    }
}