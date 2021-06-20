package com.g2pdev.smartrate.demo.di

import com.g2pdev.smartrate.demo.App
import com.g2pdev.smartrate.demo.presentation.MainViewModel
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

    fun inject(viewModel: MainViewModel)
}
