package com.trevordebrecht.spacekotlin

import android.app.Application
import android.content.Context

/**
 * Created by tdebrecht on 1/20/16.
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        context = this
    }

    companion object {
        lateinit var context: Context
    }
}