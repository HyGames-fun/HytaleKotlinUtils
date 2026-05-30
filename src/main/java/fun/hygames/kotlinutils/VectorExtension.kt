@file:Suppress("unused")

package `fun`.hygames.kotlinutils

import org.joml.Vector3d
import org.joml.Vector3f


@Suppress("NOTHING_TO_INLINE")
inline fun vec3(x: Float, y: Float, z: Float): Vector3f {
    return Vector3f(x, y, z)
}

@Suppress("NOTHING_TO_INLINE")
inline fun vec3(x: Int, y: Int, z: Int): Vector3f {
    return Vector3f(x.toFloat(), y.toFloat(), z.toFloat())
}

@Suppress("NOTHING_TO_INLINE")
inline fun vec3(x: Float, y: Int, z: Float): Vector3f {
    return Vector3f(x, y.toFloat(), z)
}

@Suppress("NOTHING_TO_INLINE")
inline fun vec3(x: Int, y: Float, z: Int): Vector3f {
    return Vector3f(x.toFloat(), y, z.toFloat())
}

@Suppress("NOTHING_TO_INLINE")
inline fun vec3(x: Double, y: Double, z: Double): Vector3d {
    return Vector3d(x, y, z)
}

@Suppress("NOTHING_TO_INLINE")
inline fun vec3d(x: Double, y: Double, z: Double): Vector3d {
    return Vector3d(x, y, z)
}

@Suppress("NOTHING_TO_INLINE")
inline fun vec3d(x: Int, y: Int, z: Int): Vector3d {
    return Vector3d(x.toDouble(), y.toDouble(), z.toDouble())
}

@Suppress("NOTHING_TO_INLINE")
inline fun vec3d(x: Float, y: Float, z: Float): Vector3d {
    return Vector3d(x.toDouble(), y.toDouble(), z.toDouble())
}

// Vector3f
@Suppress("NOTHING_TO_INLINE")
inline operator fun Vector3f.plus(other: Vector3f) : Vector3f {
    return this.add(other, Vector3f())
}

@Suppress("NOTHING_TO_INLINE")
inline operator fun Vector3f.plusAssign(other: Vector3f) {
    this.add(other)
}

@Suppress("NOTHING_TO_INLINE")
inline operator fun Vector3f.minus(other: Vector3f) : Vector3f {
    return this.sub(other, Vector3f())
}

@Suppress("NOTHING_TO_INLINE")
inline operator fun Vector3f.minusAssign(other: Vector3f) {
    this.sub(other)
}

@Suppress("NOTHING_TO_INLINE")
inline operator fun Vector3f.times(other: Vector3f) : Vector3f {
    return this.mul(other, Vector3f())
}

@Suppress("NOTHING_TO_INLINE")
inline operator fun Vector3f.times(value: Float) : Vector3f {
    return this.mul(value, Vector3f())
}

@Suppress("NOTHING_TO_INLINE")
inline operator fun Vector3f.timesAssign(other: Vector3f) {
    this.mul(other)
}

@Suppress("NOTHING_TO_INLINE")
inline operator fun Vector3f.timesAssign(value: Float) {
    this.mul(value)
}

operator fun Vector3f.div(other: Vector3f) : Vector3f {
    return set(x / other.x, y / other.y, z / other.z)
}

operator fun Vector3f.div(value: Float) : Vector3f {
    return set(x / value, y / value, z / value)
}

operator fun Vector3f.divAssign(other: Vector3f){
    Vector3f().set(x / other.x, y / other.y, z / other.z)
}

operator fun Vector3f.divAssign(value: Float) {
    Vector3f().set(x / value, y / value, z / value)
}

@Suppress("NOTHING_TO_INLINE")
inline operator fun Vector3f.unaryMinus(): Vector3f = vec3(-x, -y, -z)

@Suppress("NOTHING_TO_INLINE")
inline operator fun Vector3f.get(index: Int): Float = when(index) {
    0 -> x; 1 -> y; 2 -> z; else -> throw IndexOutOfBoundsException()
}

@Suppress("NOTHING_TO_INLINE")
inline operator fun Vector3f.set(index: Int, value: Float) {
    when(index) { 0 -> x = value; 1 -> y = value; 2 -> z = value }
}

// Vector3d

@Suppress("NOTHING_TO_INLINE")
inline operator fun Vector3d.plus(other: Vector3d) : Vector3d {
    return this.add(other, Vector3d())
}

@Suppress("NOTHING_TO_INLINE")
inline operator fun Vector3d.plusAssign(other: Vector3d) {
    this.add(other)
}

@Suppress("NOTHING_TO_INLINE")
inline operator fun Vector3d.minus(other: Vector3d) : Vector3d {
    return this.sub(other, Vector3d())
}

@Suppress("NOTHING_TO_INLINE")
inline operator fun Vector3d.minusAssign(other: Vector3d) {
    this.sub(other)
}

@Suppress("NOTHING_TO_INLINE")
inline operator fun Vector3d.times(other: Vector3d) : Vector3d {
    return this.mul(other, Vector3d())
}

@Suppress("NOTHING_TO_INLINE")
inline operator fun Vector3d.times(value: Double) : Vector3d {
    return this.mul(value, Vector3d())
}

@Suppress("NOTHING_TO_INLINE")
inline operator fun Vector3d.timesAssign(other: Vector3d) {
    this.mul(other)
}

@Suppress("NOTHING_TO_INLINE")
inline operator fun Vector3d.timesAssign(value: Double) {
    this.mul(value)
}

operator fun Vector3d.div(other: Vector3d) : Vector3d {
    return Vector3d().set(x / other.x, y / other.y, z / other.z)
}

operator fun Vector3d.div(value: Float) : Vector3d {
    return Vector3d().set(x / value, y / value, z / value)
}

operator fun Vector3d.divAssign(other: Vector3d){
    set(x / other.x, y / other.y, z / other.z)
}

operator fun Vector3d.divAssign(value: Double) {
    set(x / value, y / value, z / value)
}

@Suppress("NOTHING_TO_INLINE")
inline operator fun Vector3d.unaryMinus(): Vector3d = vec3d(-x, -y, -z)

@Suppress("NOTHING_TO_INLINE")
inline operator fun Vector3d.get(index: Int): Double = when(index) {
    0 -> x; 1 -> y; 2 -> z; else -> throw IndexOutOfBoundsException()
}

@Suppress("NOTHING_TO_INLINE")
inline operator fun Vector3d.set(index: Int, value: Double) {
    when(index) { 0 -> x = value; 1 -> y = value; 2 -> z = value }
}