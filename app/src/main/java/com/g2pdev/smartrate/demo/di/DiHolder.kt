package com.g2pdev.smartrate.demo.di

import android.app.Application
import android.content.Context

object DiHolder {

    private lateinit var appComponent: AppComponent

    fun init(context: Context) {
        if (context !is Application) {
            throw IllegalArgumentException("Context must be Application")
        }

        if (::appComponent.isInitialized) {
            throw IllegalStateException("Already initialized")
        }

        appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(context))
            .build()
    }

    fun plusAppComponent(): AppComponent {
        return appComponent
    }

}