package uz.nurlibaydev.onlinemathgame.app

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber
import uz.nurlibaydev.onlinemathgame.BuildConfig
import uz.nurlibaydev.onlinemathgame.di.dataModule
import uz.nurlibaydev.onlinemathgame.di.repositoryModule
import uz.nurlibaydev.onlinemathgame.di.sharedPrefModule
import uz.nurlibaydev.onlinemathgame.di.viewModelModule

/**
 *  Created by Nurlibay Koshkinbaev on 12/11/2022 00:46
 */

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        //Koin
        val modules = listOf(
            dataModule, sharedPrefModule, repositoryModule, viewModelModule
        )
        startKoin {
            androidLogger()
            androidContext(this@App)
            androidFileProperties()
            koin.loadModules(modules)
        }
    }

    companion object {
        lateinit var instance: App
    }
}