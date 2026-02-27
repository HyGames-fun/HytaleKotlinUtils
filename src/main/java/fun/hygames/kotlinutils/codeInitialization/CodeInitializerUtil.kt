package `fun`.hygames.kotlinutils.codeInitialization

import `fun`.hygames.kotlinutils.HytaleKotlinUtils
import java.lang.invoke.MethodHandle
import java.lang.reflect.Method
import java.lang.reflect.Modifier
import java.util.Arrays
import java.util.logging.Level
import kotlin.reflect.full.createInstance
import kotlin.reflect.jvm.kotlinFunction

object CodeInitializerUtil {

    fun Method.ktInvoke(vararg args: Any?) : Any? {
        if (Modifier.isStatic(this.modifiers)) {
            return invoke(null, *args)
        } else {
            return invoke(declaringClass.kotlin.objectInstance, *args)
        }
    }

    fun Method.ktInvoke() : Any? {
        if (Modifier.isStatic(this.modifiers)) {
            return invoke(null)
        } else {
            return invoke(declaringClass.kotlin.objectInstance)
        }
    }
}