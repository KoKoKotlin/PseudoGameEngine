package me.kokokotlin.main.sound

import java.nio.file.Path
import javax.sound.sampled.AudioSystem

class BackgorundMusic(val p: Path) {

    private var t: Thread? = null
    private var clip = AudioSystem.getClip()

    fun start() {
        if(t?.isAlive != null) {
            t?.join()
        }

        t = Thread {
            val inputStream = AudioSystem.getAudioInputStream(p.toFile())

            clip.loop(-1)
            clip.open(inputStream)
            clip.start()
        }

        t!!.start()
    }

    fun stop() {
        clip.stop()
        clip.drain()
        clip.close()

        clip = AudioSystem.getClip()
    }

}