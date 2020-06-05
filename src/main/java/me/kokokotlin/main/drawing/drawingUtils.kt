package me.kokokotlin.main.drawing

import me.kokokotlin.main.geometry.Line2D
import me.kokokotlin.main.geometry.Vector2f
import java.awt.BasicStroke
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D

/*
* Some utility function for drawing simple geometric shapes
*/

// lines
fun drawLine(g: Graphics, line2D: Line2D, strokeSize: Float = 1f) {
    drawLine(g, line2D.supportVector, line2D.supportVector + line2D.directionVector, strokeSize)
}

fun drawLine(g: Graphics, pos1: Vector2f, pos2: Vector2f, strokeSize: Float = 1f) {
    (g as Graphics2D).stroke = BasicStroke(strokeSize)

    g.drawLine(pos1.xI, pos1.yI, pos2.xI, pos2.yI)
}

// circles

fun drawCircle(g: Graphics, pos: Vector2f, size: Int) {
    drawCircle(g, pos, size, 0f)
}

fun drawCircle(g: Graphics, pos: Vector2f, size: Int, strokeSize: Float, color: Color = Color.BLACK) {
    (g as Graphics2D).stroke = BasicStroke(strokeSize)

    g.fillOval(pos.xI - size / 2, pos.yI - size / 2, size, size)

    g.color = color
    g.drawOval(pos.xI - size / 2, pos.yI - size / 2, size, size)
}