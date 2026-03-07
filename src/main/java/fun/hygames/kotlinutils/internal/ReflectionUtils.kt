package `fun`.hygames.kotlinutils.internal

import java.lang.invoke.LambdaMetafactory
import java.lang.invoke.MethodHandles
import java.lang.invoke.MethodType
import java.lang.reflect.Method
import java.util.function.Consumer

object ReflectionUtils {
    fun <T> createConsumer(method: Method, clazz: Class<T>) : Consumer<T>{
        val lookup = MethodHandles.lookup()

        val handle = lookup.unreflect(method)

        return createObjectConsumer(method, handle, method.parameterTypes[0]) as Consumer<T>
    }

    private fun <T> createObjectConsumer(
        method: Method,
        handle: java.lang.invoke.MethodHandle,
        paramType: Class<T>
    ): Consumer<T> {
        return try {
            val factory = LambdaMetafactory.metafactory(
                MethodHandles.lookup(),
                "accept",
                MethodType.methodType(Consumer::class.java, method.declaringClass),
                MethodType.methodType(Void.TYPE, Any::class.java),
                handle,
                MethodType.methodType(Void.TYPE, paramType)
            )

            factory.target.invoke(method.declaringClass.kotlin.objectInstance) as Consumer<T>
        } catch (e: Throwable) {
            throw RuntimeException("Failed to create consumer", e)
        }
    }
}