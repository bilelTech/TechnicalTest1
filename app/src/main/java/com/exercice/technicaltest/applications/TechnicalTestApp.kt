package com.exercice.technicaltest.applications

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TechnicalTestApp : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}