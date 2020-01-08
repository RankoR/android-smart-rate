package com.g2pdev.smartrate.di

import com.g2pdev.smartrate.logic.RateDisplayer
import com.g2pdev.smartrate.ui.rate.RatePresenter
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        RateModule::class
    ]
)
@Singleton
internal interface RateComponent {

    fun inject(rateDisplayer: RateDisplayer)

    fun inject(presenter: RatePresenter)
}
