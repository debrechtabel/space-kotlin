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

    lateinit var ship: ImageView
    lateinit var colorSlider: SeekBar
    lateinit var alphaSlider: SeekBar
    lateinit var brightnessSlider: SeekBar

    var hue = Ship.hue
    var saturation = Ship.saturation
    var brightness = Ship.brightness
    var alpha = Ship.alpha

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return with(ctx) {
            verticalLayout {
                backgroundColor = Color.WHITE
                lparams(matchParent, matchParent)

                ship = imageView(R.drawable.ship).lparams(dip(200), dip(200)) {
                    gravity = Gravity.CENTER
                }
                ShipUtil.processShipDrawable(ship.drawable)

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
                    max = 255
                    progress = alpha

                    onSeekBarChangeListener {
                        onProgressChanged { seekBar, progress, fromUser ->
                            alpha = progress
                            ship.imageAlpha = alpha
                        }
                    }
                }

                button("Finish") {
                    onClick {
                        Ship.hue = hue
                        Ship.saturation = saturation
                        Ship.brightness = brightness
                        Ship.alpha = alpha

                        popFragment()
                    }
                }.lparams(width = matchParent) {
                    margin = dip(20)
                }
            }
        }
    }

    fun updateTint() {
        ship.setTint(Color.HSVToColor(floatArrayOf(hue, saturation, brightness)))
    }

    fun SeekBar.init() {
        DrawableCompat.setTint(progressDrawable, Color.TRANSPARENT)
    }

}

