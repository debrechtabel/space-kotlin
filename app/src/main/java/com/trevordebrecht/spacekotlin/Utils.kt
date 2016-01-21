package com.trevordebrecht.spacekotlin

import android.os.Handler
import android.os.Looper
import android.support.v4.app.Fragment
import android.support.v4.graphics.drawable.DrawableCompat
import android.view.View
import android.widget.ImageView
import kotlin.concurrent.thread

/**
 * Created by tdebrecht on 1/19/16.
 */

operator fun View.get(id: Int): View {
    return this.findViewById(id)
}

fun Fragment.popFragment() {
    val act = activity
    if (!isDetached && act != null) {
        act.supportFragmentManager.popBackStack()
    }
}

fun ImageView.setTint(tint: Int) {
    DrawableCompat.setTint(drawable, tint)
}

fun runInBackground(f: () -> Unit) {
    thread { f() }
}

fun postToMainThread(f: () -> Unit) {
    Handler(Looper.getMainLooper()).post { f() }
}
