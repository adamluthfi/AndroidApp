package com.example.gredu.androidapp

import android.app.Application
import com.facebook.stetho.Stetho
import timber.log.Timber

/**
 * Created by gredu on 11/01/18.
 */
class WeatherApplication: Application(){

    override fun onCreate() {
        super.onCreate()
        initLeakCanary()
        initTimber()
        initStetho()
    }

    private fun initLeakCanary() {
       /* if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        LeakCanary.install(this);*/
    }

    private fun initStetho() {
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}