package me.kokokotlin.main.drawing

import java.awt.Graphics

/*
* Interface that should be inherited by everything, that can be drawn in the game
*/
interface Drawable {
    /*
    * Draws the object
    */
    fun draw(g: Graphics)
}