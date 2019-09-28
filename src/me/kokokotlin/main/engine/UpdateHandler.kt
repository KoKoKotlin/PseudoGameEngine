package me.kokokotlin.main.engine

import java.util.concurrent.CopyOnWriteArrayList

class UpdateHandler : Runnable {

    private val updateThread = Thread(this)
    val entities = CopyOnWriteArrayList<Entity>()

    init {
        updateThread.isDaemon = true
        updateThread.start()
    }

    private fun mainloop() {
        // init
        var lastLoopTime = System.nanoTime()
        val TARGET_FPS = 60
        val OPTIMAL_TIME = (1000000000 / TARGET_FPS).toLong()
        var lastFpsTime: Long = 0

        // loop
        while (updateThread.isAlive) {
            val now = System.nanoTime()
            val updateLength = now - lastLoopTime
            lastLoopTime = now
            val delta = updateLength / OPTIMAL_TIME.toDouble()

            lastFpsTime += updateLength
            if (lastFpsTime >= 1000000000) {
                lastFpsTime = 0
            }

            // update game
            update(delta)

            val sleeptime = (lastLoopTime - System.nanoTime() + OPTIMAL_TIME) / 1000000
            Thread.sleep(when {
                sleeptime > 0 -> sleeptime
                else -> 0
            })
        }

    }

    private fun update(delta: Double) {
        for(e in entities) {
            e.update(delta)
        }
    }

    override fun run() {
        mainloop()
    }


}