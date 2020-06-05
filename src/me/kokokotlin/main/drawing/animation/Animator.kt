package me.kokokotlin.main.drawing.animation


/*
* Class that handles list of animations and applies them with the onUpdate callback
*/
class Animator(val animations: List<Animation>, val onUpdate: (Sprite) -> Unit) {

    // time the current animation is running
    var currentTime = 0.0

    // index to the current animation in the list
    var animationPointer = 0
    var currentAnimation: Animation? = null

    // property that sets the animation pointer and resets the current object accordingly
    var animation: Int
        get() = animationPointer
        set(v) {
            animationPointer = v
            currentAnimation = animations[animationPointer]
            currentTime = 0.0
        }

    /*
    * Update the the animation by increasing the animation timer by delta time and
    * calling the update callback with the next sprite of the animation
    */
    fun update(delta: Double) {
        currentTime += delta

        val nextSprite = currentAnimation?.getSprite(currentTime)
        if(nextSprite != null)
            onUpdate(nextSprite)
    }
}