package com.trevordebrecht.spacekotlin

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Point
import android.graphics.drawable.Drawable
import android.os.Looper

/**
 * Created by tdebrecht on 1/19/16.
 */

val shipSize by lazy { App.context.resources.getDimensionPixelSize(R.dimen.ship_size) }
val halfShip by lazy { shipSize / 2f }
val baseShip by lazy {
    val base = App.context.getDrawable(R.drawable.ship)
    base.setBounds(0, 0, shipSize, shipSize)
    val bm = Bitmap.createBitmap(shipSize, shipSize, Bitmap.Config.ARGB_8888)
    base.draw(Canvas(bm))
    bm
}

open class Ship(var hue: Float, var saturation: Float, var brightness: Float, var alpha: Int,
                val angle: Float, onReady: () -> Unit = {}) : GameObject {

    var position = Pair(50f, 50f)

    var bitmap: Bitmap? = null
//    var drawable: Drawable? = null

    val color: Int
        get() {
            return Color.HSVToColor(floatArrayOf(hue, saturation, brightness))
        }

    init {
        val mainThread = Looper.myLooper() == Looper.getMainLooper()

        runInBackground {
            genBitmap()

//            drawable = App.context.getDrawable(R.drawable.ship)
//            drawable?.setBounds(0, 0, shipSize, shipSize)

            if (mainThread) {
                postToMainThread { onReady() }
            }
            else {
                onReady()
            }
        }
    }

    fun genBitmap() {
        val temp = baseShip.copy(baseShip.config, true)

        val matrix = Matrix()
        matrix.postRotate(angle)

        bitmap = Bitmap.createBitmap(temp, 0, 0, temp.width, temp.height, matrix, true)
    }

    override fun draw(canvas: Canvas, paint: Paint) {
        val bm = bitmap ?: return

        canvas.save()

        canvas.translate(position.first, position.second)
        canvas.rotate(angle, halfShip.toFloat(), halfShip.toFloat())

        paint.color = color
        canvas.drawBitmap(bm, -halfShip, -halfShip, paint)
//        d.draw(canvas)

        canvas.restore()
    }

    companion object {
        fun createEnemy() = Ship(0f, 0f, 0f, 255, -90f)
    }
}

object Player : Ship(289f, .58f, .48f, 255, 90f) {
}
