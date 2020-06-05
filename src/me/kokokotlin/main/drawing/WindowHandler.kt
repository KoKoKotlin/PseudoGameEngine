package me.kokokotlin.main.drawing

import java.awt.Canvas
import java.awt.Color
import java.awt.Dimension
import java.awt.image.BufferStrategy
import java.util.concurrent.CopyOnWriteArrayList
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.WindowConstants

/*
* Class that handles drawing in the game and the window
*/
class WindowHandler(val w0: Int, val h0: Int, title: String) : Canvas(), Runnable {

    private val window = JFrame(title)      // main game window
    private val contentPane = JPanel()      // panel in which the canvas in placed
    private val drawThread = Thread(this)   // thread in which all the drawing happens

    // list of all drawable things in the game
    val entities = CopyOnWriteArrayList<Drawable>()

    init {
        // setup the main window
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

        // set up the buffer strategy of the canvas object
        createBufferStrategy(2)

        // setup and start the main drawing thread
        drawThread.isDaemon = true
        drawThread.start()
    }

    /*
    * Redraws the canvas to the buffer
    */
    private fun redraw() {

        val g = bufferStrategy.drawGraphics    // graphics context

        // clear the whole screen
        g.clearRect(0, 0, w0, h0)

        // draw all drawables
        for (e in entities) {
            e.draw(g)
        }

        // show the next buffer
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