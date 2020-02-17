package me.kokokotlin.main.geometry

import java.lang.IllegalStateException
import java.util.*
import kotlin.math.atan
import kotlin.math.tan

class Line2D(p1: Vector2f, p2: Vector2f, val segment: Boolean = false) {

    val supportVector: Vector2f
    val directionVector: Vector2f

    val slope: Double
        get() = tan(directionVector.angleToXAxes)

    init {
        supportVector = p1
        directionVector = p2 - p1
    }

    infix fun intersect(other: Line2D): Optional<Vector2f> {
        val x1 = supportVector.x
        val x2 = directionVector.x
        val x3 = other.supportVector.x
        val x4 = other.directionVector.x

        val y1 = supportVector.y
        val y2 = directionVector.y
        val y3 = other.supportVector.y
        val y4 = other.directionVector.y

        val t = ((x1 - x3)*(y3 - y4) - (y1 - y3)*(x3 - x4)) / ((x1 - x2)*(y3 - y4) - (y1 - y2)*(x3 - x4))
        val u = ((x1 - x2)*(y1 - y3) - (y1 - y2)*(x1 - x3)) / ((x1 - x2)*(y3 - y4) - (y1 - y2)*(x3 - x4))

        val maybePoint = Optional.of(supportVector + directionVector * t)

        return when {
            this.segment && other.segment -> if(t !in 0.0..1.0 && u !in 0.0..1.0)
                Optional.empty() else maybePoint
            this.segment && !other.segment -> if(t !in 0.0..1.0)
                Optional.empty() else maybePoint
            !this.segment && other.segment -> if(u !in 0.0..1.0)
                Optional.empty() else maybePoint
            !this.segment && !other.segment -> maybePoint
            else -> throw IllegalStateException("This state never should be reached! Report bug to Github!")
        }
    }

}