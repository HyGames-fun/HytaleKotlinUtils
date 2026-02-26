package `fun`.hygames.kotlinutils.codeInitialization

import `fun`.hygames.kotlinutils.HytaleKotlinUtils
import java.lang.reflect.Method
import java.lang.reflect.Modifier
import java.util.Arrays
import java.util.logging.Level
import kotlin.reflect.full.createInstance

object CodeInitializerUtil {

    fun Method.ktInvoke(vararg args: Any?) : Any {
        return try {
            if (Modifier.isStatic(this.modifiers)) {
                invoke(null, *args)
            } else {
                if (declaringClass.kotlin.objectInstance == null) {
                    throw NullPointerException("Object instance of ${declaringClass.kotlin.simpleName} is null!")
                }
                invoke(declaringClass.kotlin.objectInstance, *args)
            }
        } catch (exception: Exception){
            HytaleKotlinUtils.logger.at(Level.SEVERE).log(exception.message + ". Arguments: ${args.contentToString()}")
            exception.printStackTrace()
            throw exception
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