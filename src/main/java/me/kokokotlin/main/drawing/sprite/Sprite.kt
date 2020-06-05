package me.kokokotlin.main.drawing.sprite

import me.kokokotlin.main.geometry.Rectangle
import java.awt.geom.AffineTransform
import java.awt.image.AffineTransformOp
import java.awt.image.BufferedImage
import java.awt.image.IndexColorModel


/**
* Returns the transformed version of the image with the given transformation
*/
fun BufferedImage.applyTransformToImage(transformOp: AffineTransformOp): BufferedImage {
    val transformedImage = BufferedImage(this.width, this.height, this.type, this.colorModel as IndexColorModel)

    transformOp.filter(this, transformedImage)
    return transformedImage
}

/**
* Class that represents a drawable image in the game
*/
data class Sprite(val image: BufferedImage) {

    /**
    * Returns the rotated version of the sprite
    */
    fun getRotated(angle: Double): Sprite {
        val affineTransform = AffineTransform()
        affineTransform.rotate(angle)

        return customTransform(affineTransform)
    }

    /**
    * Returns the scaled version of the sprite with independend x and y scale values
    */
    fun getScaled(scaleX: Double, scaleY: Double): Sprite {
        val affineTransform = AffineTransform()
        affineTransform.scale(scaleX, scaleY)

        return customTransform(affineTransform)
    }

    /**
    * Returns the scales version of the sprite with same value for x and y scale
    */
    fun getScaled(scale: Double) = getScaled(scale, scale)

    /**
    * Returns the transformed version of the sprite. The transformation is given by the user
    */
    fun customTransform(transform: AffineTransform): Sprite {
        val transformOp = AffineTransformOp(transform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR)
        return Sprite(image.applyTransformToImage(transformOp))
    }
}


/**
* Class that handles a sprite sheet file
*/
class SpriteSheet(private val srcImage: BufferedImage, private val spriteRects: List<Rectangle>) {

    /**
     * Returns a the sprite of the sprite sheet that is defined by the rectangle in the
     * list of rectangles at the index given by the user
     *
     * Throws IndexOutOfBoundException
     */
    fun getSprite(index: Int): Sprite {
        val rect = spriteRects[index].rI

        return Sprite(srcImage.getSubimage(rect.x, rect.y, rect.width, rect.height))
    }

}