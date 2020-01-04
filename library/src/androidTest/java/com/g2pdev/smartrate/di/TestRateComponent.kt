package com.g2pdev.smartrate.di

import com.g2pdev.smartrate.CachesTest
import com.g2pdev.smartrate.RateRepositoryTest
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        TestRateModule::class
    ]
)
@Singleton
internal interface TestRateComponent {

    fun inject(cachesTest: CachesTest)
    fun inject(rateRepositoryTest: RateRepositoryTest)

}