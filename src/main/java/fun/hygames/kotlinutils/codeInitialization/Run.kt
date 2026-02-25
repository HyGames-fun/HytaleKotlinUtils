package `fun`.hygames.kotlinutils.codeInitialization

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Retention(AnnotationRetention.RUNTIME)

/**
 * Auto-launches annotated methods at specific application lifecycle points.
 *
 * Methods annotated with @Run will be automatically executed during:
 * - [RunOn.START] - application startup
 * - [RunOn.STOP] - application shutdown
 * - [RunOn.TEST] - registered for manual execution via command "/test"
 *
 * Execution order can be controlled via [priority] and [after] parameters.
 *
 * @param on Defines when the method should be executed (default: [RunOn.START])
 * @param priority Execution priority (higher values run first, default: -1)
 * @param after Ensures execution after specified method name(s) - comma-separated list supported
 * @param name Custom identifier for the method (useful for [after] references)
 */

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