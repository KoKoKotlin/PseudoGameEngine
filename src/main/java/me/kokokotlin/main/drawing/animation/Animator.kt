package me.kokokotlin.main.drawing.animation

import me.kokokotlin.main.drawing.sprite.Sprite


/**
* Class that handles list of animations and applies them with the onUpdate callback
*/
class Animator(val animations: List<Animation>, val onUpdate: (Sprite) -> Unit) {

    // time the current animation is running
    private var currentTime = 0.0

    // index to the current animation in the list
    private var animationPointer = 0
    private var currentAnimation: Animation? = null

    // cached previous sprite
    private var prevSprite: Sprite? = null

    // property that sets the animation pointer and resets the current object accordingly
    var animation: Int
        get() = animationPointer
        set(v) {
            animationPointer = v
            currentAnimation = animations[animationPointer]
            currentTime = 0.0
        }

    /**
    * Update the the animation by increasing the animation timer by delta time and
    * calling the update callback with the next sprite of the animation
    */
    fun update(delta: Double) {
        // if the current animation is null the animator is stopped
        currentAnimation?.also {
            if(it.checkReset(currentTime))
                currentTime = 0.0

            currentTime += delta

            val nextSprite = it.getSprite(currentTime)
            if (prevSprite != nextSprite)
                onUpdate(nextSprite)
        }
    }
}