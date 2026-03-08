package `fun`.hygames.kotlinutils.internal

import java.lang.reflect.Method
import java.lang.reflect.Modifier

object MethodUtil {

    internal fun Method.ktInvoke(vararg args: Any?) : Any? {
        return if (Modifier.isStatic(this.modifiers)) {
            invoke(null, *args)
        } else {
            invoke(declaringClass.kotlin.objectInstance, *args)
        }
    }

    internal fun Method.ktInvoke() : Any? {
        return if (Modifier.isStatic(this.modifiers)) {
            invoke(null)
        } else {
            invoke(declaringClass.kotlin.objectInstance)
        }
    }
}