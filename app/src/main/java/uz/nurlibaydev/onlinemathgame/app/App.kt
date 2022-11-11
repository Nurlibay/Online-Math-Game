package uz.nurlibaydev.onlinemathgame.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 *  Created by Nurlibay Koshkinbaev on 12/11/2022 00:46
 */

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: App
    }
}