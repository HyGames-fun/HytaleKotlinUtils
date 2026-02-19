package `fun`.hygames.kotlinutils.codeInitialization

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Retention(AnnotationRetention.RUNTIME)
annotation class Run (
    val on: RunOn = RunOn.START,
    val priority: Int = -1,
    val after: String = "",
    val name: String = ""
)

enum class RunOn {
    START,
    STOP,
    TEST
}