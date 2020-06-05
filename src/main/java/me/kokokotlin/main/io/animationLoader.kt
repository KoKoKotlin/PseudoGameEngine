package me.kokokotlin.main.io

import java.nio.file.Path


/**
* loads a list of animations from the json file given by path
* File format:
 * {
 *  spriteSheet: "path_to_sprite_sheet",
 *  keyframes: [
 *     {
 *      spriteIndex: index
 *      duration: duration
 *     }, ...
 *  ]
 * }
*/
fun loadAnimation(path: Path) {

}

/**
* loads a sprite sheet from the json file that is given by path
* File format:
 * {
 *  path: "path_to_image",
 *  coordinates: [
 *      {
 *          p1: {x: 10, y: 10},
 *          p2: {x: 100, y: 100}
 *      }, ...
 *  ]
 * }
 */
fun loadSpriteSheet(path: Path) {

}