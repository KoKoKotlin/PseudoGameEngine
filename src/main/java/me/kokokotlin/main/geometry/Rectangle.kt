package me.kokokotlin.main.geometry

class Rectangle {

    private val p1: Vector2f        // top left point of the rectangle
    private val p2: Vector2f        // bottom right point of the rectangle

    val left: Double                // x value of top left point
        get() = p1.x

    val top: Double                 // y value of top left point
        get() = p1.y

    val right: Double               // x value of bottom right point
        get() = p2.x

    val bottom: Double              // y value of bottom right point
        get() = p2.y

    val rI: java.awt.Rectangle      // rectangle with integer coordinates
        get() = java.awt.Rectangle(p1.xI, p1.yI, (p2 - p1).xI, (p2 - p1).yI)

    constructor(p1: Vector2f, p2: Vector2f) {
        this.p1 = p1
        this.p2 = p2
    }

    constructor(x1: Double, y1: Double, x2: Double, y2: Double)
            : this(Vector2f(x1, y1), Vector2f(x2, y2))

    /*
    * Returns true, if the vector is contained in this rectangle, otherwise false
    */
    infix fun contains(v: Vector2f) = v.x > left && v.x < right && v.y > top && v.y < bottom

    /*
    * Returns true, if the this rectangle and the given rectangle r intersect, otherwise false
    */
    infix fun intersect(r: Rectangle) = when {
            left > r.right || right > r.left -> false
            top < r.bottom || bottom < r.top -> false
            else -> true
    }


}