package com.g2pdev.smartrate.demo

import android.app.Application
import com.g2pdev.smartrate.SmartRate

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        SmartRate.init(this)
    }
}