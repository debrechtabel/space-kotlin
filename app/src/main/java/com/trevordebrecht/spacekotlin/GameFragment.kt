package com.trevordebrecht.spacekotlin

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import org.jetbrains.anko.frameLayout
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.textColor
import org.jetbrains.anko.textView

/**
 * Created by tdebrecht on 1/20/16.
 */

class GameFragment : Fragment() {

    lateinit var surface: GameSurface
    lateinit var livesText: TextView
    lateinit var gameOver: TextView

    val paint: Paint = Paint()

    val gameManager = GameManager() { objects ->
        val canvas = surface.holder.lockCanvas() ?: return@GameManager

        canvas.drawColor(Color.BLACK)

        for (obj in objects) {
            obj.draw(canvas, paint)
        }

        surface.holder.unlockCanvasAndPost(canvas)
    }

    init {
        gameManager.lifeListeners.add {
            livesText.text = "x$it"

            if (it == 0) {
                gameOver.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return with (ctx) {
            frameLayout {
                lparams(matchParent, matchParent)

                surface = GameSurface(ctx).lparams(matchParent, matchParent)

                livesText = textView {
                    setCompoundDrawablesWithIntrinsicBounds(R.drawable.ship, 0, 0, 0)
                }.lparams {
                    gravity = Gravity.BOTTOM or Gravity.LEFT
                }

                gameOver = textView("Game Over") {
                    textSize = 32f
                    textColor = Color.WHITE
                    visibility = View.GONE
                }.lparams {
                    gravity = Gravity.CENTER
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        gameManager.start()
    }

    override fun onPause() {
        super.onPause()

        gameManager.quit()
    }
}

class GameSurface : SurfaceView, SurfaceHolder.Callback {
    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        holder.addCallback(this)
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        Log.d("blah", "surface created!")
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
        Log.d("blah", "surface changed!")
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        Log.d("blah", "surface destroyed!")
    }

//    var renderObjects: Collection<GameObject>? = null


//    init {
//        setWillNotDraw(false)
//    }
//
//    override fun onDraw(canvas: Canvas) {
//        val objects = renderObjects ?: return
//
//        for (obj in objects) {
//            obj.draw(canvas, paint)
//        }
//    }
}
