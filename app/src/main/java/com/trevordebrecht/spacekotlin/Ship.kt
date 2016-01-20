package com.trevordebrecht.spacekotlin

import android.graphics.Color

/**
 * Created by tdebrecht on 1/19/16.
 */

object Ship {
    var hue = 289f
    var saturation = .58f
    var brightness = .48f
    var alpha = 255
}

fun shipColor(): Int = Color.HSVToColor(floatArrayOf(Ship.hue, Ship.saturation, Ship.brightness))
