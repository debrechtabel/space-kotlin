package com.trevordebrecht.spacekotlin

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point

/**
 * Created by tdebrecht on 1/20/16.
 */

operator fun Pair<Float, Float>.plus(other: Pair<Float, Float>) = Pair(first + other.first, second + other.second)
operator fun Pair<Float, Float>.minus(other: Pair<Float, Float>) = Pair(first - other.first, second - other.second)
operator fun Pair<Float, Float>.times(other: Float) = Pair(first * other, second * other)

const val FRAMERATE = 1000L / 60

class GameManager(val renderer: (Collection<GameObject>) -> Unit) {

    var numLives = 3

    val lifeListeners: MutableSet<(Int) -> Unit> = hashSetOf()

    private val ships: MutableSet<Ship> = hashSetOf()

    private val frameListeners: MutableSet<FrameListener> = hashSetOf()

    private val gameObjects: MutableSet<GameObject> = hashSetOf()

    private var lastFrame = 0L

    private var quitRequested = false

    init {
        ships.add(Player)
        gameObjects.add(Player)
    }

    fun checkCollision(point: Pair<Float, Float>, ship: Ship): Boolean {
        val target: Bitmap = ship.bitmap ?: return false

        val p = point - ship.position

        if (p.first in 0..target.width && p.second in 0..target.height) {
            return Color.alpha(target.getPixel(p.first.toInt(), p.second.toInt())) > 0
        }

        return false
    }

    fun start() {
        quitRequested = false
        postToMainThread { gameLoop() }
    }

    fun gameLoop() {
        if (quitRequested) {
            lastFrame
            return
        }

        val thisFrame = System.currentTimeMillis()
        var timeSinceLastFrame = if (lastFrame == 0L) 0f else ((thisFrame - lastFrame) / 1000f)

        frameListeners.forEach { it.onFrameStarted(timeSinceLastFrame) }

        renderer(gameObjects)

        postToMainThread { gameLoop() }
    }

    fun quit() {
        quitRequested = true
    }

}

class Bullet(var velocity: Pair<Float, Float>, var position: Pair<Float, Float>) : FrameListener, GameObject {

    override fun onFrameStarted(timeSinceLastFrame: Float) {
        position += velocity * timeSinceLastFrame
    }

    override fun draw(canvas: Canvas, paint: Paint) {
        canvas.save()

        canvas.translate(position.first, position.second)
        canvas.drawCircle(0f, 0f, 2f, paint)

        canvas.restore()
    }
}

interface GameObject {
    fun draw(canvas: Canvas, paint: Paint)
}

interface FrameListener {
    fun onFrameStarted(timeSinceLastFrame: Float)
    fun onFrameEnded() = {}
}
