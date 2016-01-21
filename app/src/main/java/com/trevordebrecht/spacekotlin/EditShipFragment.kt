package com.trevordebrecht.spacekotlin

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.graphics.drawable.DrawableCompat
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SeekBar
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.button
import org.jetbrains.anko.imageView
import org.jetbrains.anko.margin
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.onClick
import org.jetbrains.anko.onSeekBarChangeListener
import org.jetbrains.anko.seekBar
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.dip
import org.jetbrains.anko.textView
import org.jetbrains.anko.verticalLayout


/**
 * Created by tdebrecht on 1/19/16.
 */

class EditShipFragment : Fragment() {

    lateinit var preview: ImageView

    var hue = Player.hue
    var saturation = Player.saturation
    var brightness = Player.brightness
    var alpha = Player.alpha

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return with(ctx) {
            verticalLayout {
                backgroundColor = Color.WHITE
                lparams(matchParent, matchParent)

                preview = imageView(R.drawable.ship).lparams(dip(200), dip(200)) {
                    gravity = Gravity.CENTER
                }
                ShipUtil.processShipDrawable(preview.drawable)

                textView(R.string.color) {
                    textSize = 18f
                }.lparams {
                    margin = dip(10)
                }

                // color
                seekBar {
                    init()
                    max = 360
                    progress = hue.toInt()

                    onSeekBarChangeListener {
                        onProgressChanged { seekBar, progress, fromUser ->
                            hue = progress.toFloat()
                            updateTint()
                        }
                    }
                }

                textView(R.string.saturation) {
                    textSize = 18f
                }.lparams {
                    margin = dip(10)
                }

                // saturation
                seekBar {
                    init()
                    max = 100
                    progress = (saturation * 100f).toInt()

                    onSeekBarChangeListener {
                        onProgressChanged { seekBar, progress, fromUser ->
                            saturation = progress.toFloat() / 100f
                            updateTint()
                        }
                    }
                }

                textView(R.string.brightness) {
                    textSize = 18f
                }.lparams {
                    margin = dip(10)
                }

                // brightness
                seekBar {
                    init()
                    max = 100
                    progress = (brightness * 100f).toInt()

                    onSeekBarChangeListener {
                        onProgressChanged { seekBar, progress, fromUser ->
                            brightness = progress.toFloat() / 100f
                            updateTint()
                        }
                    }
                }

                textView(R.string.opacity) {
                    textSize = 18f
                }.lparams {
                    margin = dip(10)
                }

                // opacity
                seekBar {
                    init()
                    max = 254
                    progress = alpha - 1

                    onSeekBarChangeListener {
                        onProgressChanged { seekBar, progress, fromUser ->
                            alpha = progress + 1
                            preview.imageAlpha = alpha
                        }
                    }
                }

                button("Finish") {
                    onClick {
                        Player.hue = hue
                        Player.saturation = saturation
                        Player.brightness = brightness
                        Player.alpha = alpha

                        runInBackground { Player.genBitmap() }

                        popFragment()
                    }
                }.lparams(width = matchParent) {
                    margin = dip(20)
                }
            }
        }
    }

    fun updateTint() {
        preview.setTint(Color.HSVToColor(floatArrayOf(hue, saturation, brightness)))
    }

    fun SeekBar.init() {
        DrawableCompat.setTint(progressDrawable, Color.TRANSPARENT)
    }

}

