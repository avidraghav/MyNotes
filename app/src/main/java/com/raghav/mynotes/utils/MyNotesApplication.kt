package com.raghav.mynotes.utils

import ConfigureLeakCanary.enableLeakCanary
import android.app.Application
import com.raghav.mynotes.BuildConfig
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyNotesApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            enableLeakCanary(false)
        }
    }
}