package com.g2pdev.smartrate.di

import com.g2pdev.smartrate.CachesTest
import com.g2pdev.smartrate.RateRepositoryTest
import com.g2pdev.smartrate.interactor.IsRatedInteractorTest
import com.g2pdev.smartrate.interactor.LastPromptInteractorTest
import com.g2pdev.smartrate.interactor.NeverAskInteractorTest
import com.g2pdev.smartrate.interactor.SessionCountInteractorTest
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
    fun inject(isRatedInteractorTest: IsRatedInteractorTest)
    fun inject(lastPromptInteractorTest: LastPromptInteractorTest)
    fun inject(neverAskInteractorTest: NeverAskInteractorTest)
    fun inject(sessionCountInteractorTest: SessionCountInteractorTest)

}