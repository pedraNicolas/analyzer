package com.analyzer

import android.app.Application
import com.analyzer.core.utils.CoreModule
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AnalyzerApp: Application() {
    override fun onCreate() {
        super.onCreate()
        CoreModule.init(this)
    }
}