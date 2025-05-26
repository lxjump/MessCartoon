package com.mess.messcartoon

import android.app.Application
import com.blankj.utilcode.util.LogUtils
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application() {

    override fun onCreate() {
        super.onCreate()
        LogUtils.getConfig().setGlobalTag("Cartoon")
    }
}