package com.g2pdev.smartrate.di

import com.g2pdev.smartrate.domain.RateDisplayer
import com.g2pdev.smartrate.presentation.rate.RateDialogFragment
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

    fun inject(fragment: RateDialogFragment)
}
