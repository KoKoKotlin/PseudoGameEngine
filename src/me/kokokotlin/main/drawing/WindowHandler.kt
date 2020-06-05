package me.kokokotlin.main.drawing

import java.awt.Canvas
import java.awt.Color
import java.awt.Dimension
import java.util.concurrent.CopyOnWriteArrayList
import javax.swing.JFrame
import javax.swing.JPanel
import kotlin.math.roundToLong

/*
* Class that handles drawing in the game and the window
*/
class WindowHandler(val w0: Int, val h0: Int, title: String) : Canvas(), Runnable {

    private val window = JFrame(title)      // main game window
    private val contentPane = JPanel()      // panel in which the canvas in placed
    private val drawThread = Thread(this)   // thread in which all the drawing happens

    // fps of the game (frames per second that are drawn to the screen)
    private var currentFps = 60
    private var optimalFrameTime = 1000000000.0 / fps
    public var fps: Int
        get() = currentFps
        set(v) {
            currentFps = v
            optimalFrameTime = 1000000000.0 / fps
        }

    // last measured real fps of the game
    private var _lastFps = 0
    public val lastFps: Int
        get() = _lastFps

    // list of all drawable things in the game
    val drawables = CopyOnWriteArrayList<Drawable>()

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
        for (e in drawables) {
            e.draw(g)
        }

        // show the next buffer
        bufferStrategy.show()
    }

    override fun run() {
        var lastLoopTime = System.nanoTime()
        var lastFpsTime = 0L

        var fps = 0             // real fps counter

        // drawing loop that caps the fps to the desired current_fps value
        while(drawThread.isAlive) {
            val now = System.nanoTime()
            val updateLength = now -lastLoopTime
            lastLoopTime = now;

            fps++

            lastFpsTime += updateLength
            if (lastFpsTime >= 1000000000) {
                lastFpsTime = 0;
                fps = 0
            }

            redraw()

            val sleepTime = ((lastLoopTime - System.nanoTime() + optimalFrameTime) / 1000000).roundToLong()
            Thread.sleep(when(sleepTime) {
                0L -> 1L
                else -> sleepTime
            })
        }
    }

}