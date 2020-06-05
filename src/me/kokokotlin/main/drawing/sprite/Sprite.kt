package me.kokokotlin.main.drawing.sprite

import me.kokokotlin.main.geometry.Rectangle
import java.awt.geom.AffineTransform
import java.awt.image.AffineTransformOp
import java.awt.image.BufferedImage
import java.awt.image.IndexColorModel


/*
* Returns the transformed version of the image with the given transformation
*/
fun BufferedImage.applyTransformToImage(transformOp: AffineTransformOp): BufferedImage {
    val transformedImage = BufferedImage(this.width, this.height, this.type, this.colorModel as IndexColorModel)

    transformOp.filter(this, transformedImage)
    return transformedImage
}

/*
* Class that represents a drawable image in the game
*/
data class Sprite(val image: BufferedImage) {

    fun getRotated(angle: Double): Sprite {
        val affineTransform = AffineTransform()
        affineTransform.rotate(angle)

        return customTransform(affineTransform)
    }

    fun getScaled(scaleX: Double, scaleY: Double): Sprite {
        val affineTransform = AffineTransform()
        affineTransform.scale(scaleX, scaleY)

        return customTransform(affineTransform)
    }

    fun getScaled(scale: Double) = getScaled(scale, scale)

    fun customTransform(transform: AffineTransform): Sprite {
        val transformOp = AffineTransformOp(transform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR)
        return Sprite(image.applyTransformToImage(transformOp))
    }
}

class SpriteSheet(private val srcImage: BufferedImage, private val spriteRects: List<Rectangle>) {

    fun getSprite(index: Int) {
        val rect = spriteRects[index].rI

        srcImage.getSubimage(rect.x, rect.y, rect.width, rect.height)
    }

}