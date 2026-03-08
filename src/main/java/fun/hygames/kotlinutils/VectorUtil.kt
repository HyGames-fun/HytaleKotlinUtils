@file:Suppress("unused")

package `fun`.hygames.kotlinutils

import com.hypixel.hytale.math.vector.Vector3d
import com.hypixel.hytale.math.vector.Vector3f

fun vec3(x: Float, y: Float, z: Float) : Vector3f {
    return Vector3f(x, y, z)
}

fun vec3(x: Int, y: Int, z: Int) : Vector3f {
    return Vector3f(x.toFloat(), y.toFloat(), z.toFloat())
}

fun vec3(x: Float, y: Int, z: Float) : Vector3f {
    return Vector3f(x, y.toFloat(), z)
}

fun vec3(x: Int, y: Float, z: Int) : Vector3f {
    return Vector3f(x.toFloat(), y, z.toFloat())
}

fun vec3(x: Double, y: Double, z: Double) : Vector3d {
    return Vector3d(x, y, z)
}

fun vec3d(x: Double, y: Double, z: Double) : Vector3d {
    return Vector3d(x, y, z)
}

fun vec3d(x: Int, y: Int, z: Int) : Vector3d {
    return Vector3d(x.toDouble(), y.toDouble(), z.toDouble())
}

fun vec3d(x: Float, y: Float, z: Float) : Vector3d {
    return Vector3d(x.toDouble(), y.toDouble(), z.toDouble())
}
