package me.kokokotlin.main.geometry

import kotlin.math.acos
import kotlin.math.hypot
import kotlin.math.roundToInt

class Vector2f(val x: Double, val y: Double) {

    val xI: Int
        get() = x.roundToInt()

    val yI: Int
        get() = y.roundToInt()

    val mag: Double
        get() = hypot(x, y)

    val angleToXAxes: Double
        get() = acos((Vector2f(1.0, 0.0) dot this) / (this.mag))

    infix fun dot(other: Vector2f) = x * other.x + y * other.y
    infix fun dist(other: Vector2f) = hypot(x - other.x, y - other.y)

    operator fun plus(other: Vector2f) = Vector2f(x + other.x, y + other.y)
    operator fun times(scalar: Double) = Vector2f(x * scalar, y * scalar)
    operator fun times(scalar: Int) = Vector2f(x * scalar, y * scalar)
    operator fun times(other: Vector2f) = this dot other
    operator fun minus(other: Vector2f) = this + other * -1
    operator fun div(scalar: Double) = this * (1.0 / scalar)
    operator fun div(scalar: Int) = this * (1.0 / scalar)
    operator fun unaryMinus() = this * -1

    override fun toString() = "Vector2f<x: $x, y: $y>"
    override fun hashCode() = (y.hashCode() + x.hashCode()).hashCode()
    override fun equals(other: Any?): Boolean {
        if(other !is Vector2f) return false
        return x == other.x && y == other.y
    }
}