package `fun`.hygames.kotlinutils.codeInitialization.dependecyInjection

import `fun`.hygames.kotlinutils.codeInitialization.RunNode
import java.lang.reflect.Parameter

fun interface ParameterInjection {
    fun inject(runNode: RunNode, parameter: Parameter, arguments: Map<String, Any>) : Any?
}