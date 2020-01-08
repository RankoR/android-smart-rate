package com.g2pdev.smartrate.demo.di

import com.g2pdev.smartrate.demo.App
import com.g2pdev.smartrate.demo.ui.MainPresenter
import dagger.Component
import javax.inject.Singleton

/**
 * Main Dagger component
 */
@Component(
    modules = [
        AppModule::class
    ]
)
@Singleton
interface AppComponent {

    fun inject(app: App)

    fun inject(presenter: MainPresenter)

}