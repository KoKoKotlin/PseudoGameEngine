package me.kokokotlin.main.drawing.animation

import me.kokokotlin.main.drawing.sprite.Sprite
import java.lang.IllegalArgumentException

/**
* Class that represents an animation of a drawable object
*/
class Animation {

    // keyframes that are shown consecutively on screen during the animation
    val keyFrames: List<KeyFrame>

    constructor(keyFrames: List<KeyFrame>) {
        this.keyFrames = keyFrames

        transformToTimeInterval()
    }

    constructor(sprites: List<Sprite>, durations: List<Double>) {
        if(sprites.size != durations.size) {
            throw IllegalArgumentException("Sprites list and duration list must have same lenght")
        }

        keyFrames = List (sprites.size) {
            KeyFrame(sprites[it], durations[it])
        }

        transformToTimeInterval()
    }

    /**
    * transforms the duration values of the keyframes into time points
    * by accumulating them
    */
    private fun transformToTimeInterval() {
        var totalTime = 0.0

        keyFrames.forEach {
            it.timeInterval = totalTime..(totalTime + it.duration)
            totalTime += it.duration
        }
    }

    /**
    * returns the sprite that should be drawn in the current time interval
    */
    fun getSprite(currentTime: Double) = keyFrames.first { currentTime in it.timeInterval }.sprite
}

data class KeyFrame(val sprite: Sprite, val duration: Double,
                    var timeInterval: ClosedFloatingPointRange<Double> = 0.0..0.0)