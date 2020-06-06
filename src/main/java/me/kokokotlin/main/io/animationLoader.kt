package me.kokokotlin.main.io

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import me.kokokotlin.main.drawing.animation.Animation
import me.kokokotlin.main.drawing.animation.KeyFrame
import me.kokokotlin.main.drawing.sprite.Sprite
import me.kokokotlin.main.drawing.sprite.SpriteSheet
import me.kokokotlin.main.geometry.Rectangle
import me.kokokotlin.main.geometry.Vector2f
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*
import javax.imageio.ImageIO

// global gson object for loading json data
private val gson = Gson()


/**
* Classes that represent the structure of the json data that is loaded
*/
data class AnimationData(val spriteSheet: String, val keyFrames: Array<KeyFrameData>)
data class KeyFrameData(val spriteIndex: Int, val duration: Double)

data class SpriteSheetData(val path: String, val coordinates: Array<RectangleData>)
data class RectangleData(val p1: Vector2f, val p2: Vector2f)


/**
* Loads a animation from the json file given by path
 * intern the linked sprite sheet is loaded and parsed
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
fun loadAnimation(path: Path): Optional<Animation> {

    // sanity check for given path
    if(isPathValid(path)) {
        // load the json
        val animationData = try {
            gson.fromJson(Files.newBufferedReader(path), AnimationData::class.java)
        } catch (e: JsonSyntaxException) { println(e.message); return Optional.empty() }

        // load the sprite sheet linked in the json
        val spriteSheet = loadSpriteSheet(Paths.get(animationData.spriteSheet))

        // sprite sheet couldn't be loaded cancel loading of animation
        if(spriteSheet.isEmpty) {
            print("Sprite sheet of animation couldn't be loaded")
            return Optional.empty()
        }

        // extract the keyframes
        val keyFrames = List(animationData.keyFrames.size) {
            // extract the keyframe information
            val keyFrameData = animationData.keyFrames[it]

            KeyFrame(spriteSheet.get().getSprite(keyFrameData.spriteIndex), keyFrameData.duration)
        }

        return Optional.of(Animation(keyFrames))
    } else {
        // print error when path invalid
        println("Path $path does not exist or is directory!")
        return Optional.empty()
    }
}

/**
* Loads a animation from the json file given by path
 * the sprite sheet is given as an argument to the function so
 * no loading required
 * File format:
 * {
 *  keyframes: [
 *     {
 *      spriteIndex: index
 *      duration: duration
 *     }, ...
 *  ]
 * }
*/
fun loadAnimWithSpritesheet(path: Path, spriteSheet: SpriteSheet): Optional<Animation> {
    // sanity check for given path
    if(isPathValid(path)) {
        // load the json
        val animationData = try {
            gson.fromJson(Files.newBufferedReader(path), AnimationData::class.java)
        } catch (e: JsonSyntaxException) { println(e.message); return Optional.empty() }

        // extract the keyframes
        val keyFrames = List(animationData.keyFrames.size) {
            // extract the keyframe information
            val keyFrameData = animationData.keyFrames[it]

            KeyFrame(spriteSheet.getSprite(keyFrameData.spriteIndex), keyFrameData.duration)
        }

        return Optional.of(Animation(keyFrames))
    } else {
        // print error when path invalid
        println("Path $path does not exist or is directory!")
        return Optional.empty()
    }
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
fun loadSpriteSheet(path: Path): Optional<SpriteSheet> {
    // sanity check for given path
    if(isPathValid(path)) {
        // load the json
        val spriteSheetData = try {
            gson.fromJson<SpriteSheetData>(Files.newBufferedReader(path), SpriteSheetData::class.java)
        } catch (e: JsonSyntaxException) { println(e.message); return Optional.empty() }

        // load the buffered image of the sprite sheet
        val image = ImageIO.read(Files.newInputStream(Paths.get(spriteSheetData.path)))

        // extract rectangles
        val rectangles = List(spriteSheetData.coordinates.size) {
            val coords = spriteSheetData.coordinates[it]

            Rectangle(coords.p1, coords.p2)
        }

        return Optional.of(SpriteSheet(image, rectangles))
    } else {
        // print error when path invalid
        println("Path $path does not exist or is directory!")
        return Optional.empty()
    }
}

/**
* Returns true, if the given path is a valid existing file
*/
fun isPathValid(path: Path) = Files.exists(path) && !Files.isDirectory(path)