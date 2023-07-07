package com.psijuego

import android.app.Application
import com.psijuego.core.utils.CoreModule
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PsiJuegoApp: Application() {
    override fun onCreate() {
        super.onCreate()
        CoreModule.init(this)
    }
}