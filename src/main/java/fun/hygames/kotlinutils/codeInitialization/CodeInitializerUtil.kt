package `fun`.hygames.kotlinutils.codeInitialization

import java.lang.reflect.Method
import java.lang.reflect.Modifier

object CodeInitializerUtil {

    fun Method.ktInvoke(vararg args: Any?) : Any{
        if (Modifier.isStatic(this.modifiers)) {
            return invoke(null, *args)
        } else {
            return invoke(declaringClass.kotlin.objectInstance, *args)
        }
    }

    fun Method.ktInvoke() : Any{
        if (Modifier.isStatic(this.modifiers)) {
            return invoke(null)
        } else {
            return invoke(declaringClass.kotlin.objectInstance)
        }
    }
}