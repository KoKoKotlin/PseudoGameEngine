package me.kokokotlin.main.sound

import java.nio.file.Path
import javax.sound.sampled.AudioSystem

class Sound(val p: Path) {
    fun play() {
        val t = Thread {
            val clip = AudioSystem.getClip()
            val inputStream = AudioSystem.getAudioInputStream(p.toFile())

            clip.open(inputStream)
            clip.start()
        }

        t.start()
    }
}