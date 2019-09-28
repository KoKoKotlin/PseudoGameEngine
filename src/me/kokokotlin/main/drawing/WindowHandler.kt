package me.kokokotlin.main.drawing

import java.awt.Canvas
import java.awt.Color
import java.awt.Dimension
import java.awt.image.BufferStrategy
import java.util.concurrent.CopyOnWriteArrayList
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.WindowConstants

class WindowHandler(val w0: Int, val h0: Int, title: String) : Canvas(), Runnable {

    private val window = JFrame(title)
    private val contentPane = JPanel()
    private val drawThread = Thread(this)

    val entities = CopyOnWriteArrayList<Drawable>()

    init {
        window.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        val insets = window.insets
        contentPane.size = Dimension(w0 + insets.left + insets.right, h0 + insets.bottom + insets.top)

        window.contentPane = contentPane
        background = Color.WHITE
        size = Dimension(w0, h0)
        contentPane.add(this)
        window.pack()
        window.setLocationRelativeTo(null)

        window.isResizable = false
        window.isVisible = true

        createBufferStrategy(3)

        drawThread.isDaemon = true
        drawThread.start()
    }

    private fun redraw() {

        val g = bufferStrategy.drawGraphics
        g.clearRect(0, 0, w0, h0)

        for (e in entities) {
            e.draw(g)
        }

        bufferStrategy.show()
    }

    override fun run() {
        while (drawThread.isAlive) {
            redraw()

            // slow down drawing to gain better performance
            Thread.sleep(10)
        }
    }

}