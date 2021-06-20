package com.g2pdev.smartrate.di

import com.g2pdev.smartrate.PreferencesTest
import com.g2pdev.smartrate.RateRepositoryTest
import com.g2pdev.smartrate.interactor.ClearAllInteractorTest
import com.g2pdev.smartrate.interactor.GetPackageNameInteractorTest
import com.g2pdev.smartrate.interactor.GetStoreLinkInteractorTest
import com.g2pdev.smartrate.interactor.IsRatedInteractorTest
import com.g2pdev.smartrate.interactor.LastPromptInteractorTest
import com.g2pdev.smartrate.interactor.NeverAskInteractorTest
import com.g2pdev.smartrate.interactor.SessionCountInteractorTest
import com.g2pdev.smartrate.interactor.ShouldShowRatingInteractorTest
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        TestRateModule::class
    ]
)
@Singleton
internal interface TestRateComponent {

    fun inject(preferencesTest: PreferencesTest)
    fun inject(rateRepositoryTest: RateRepositoryTest)

    fun inject(isRatedInteractorTest: IsRatedInteractorTest)
    fun inject(lastPromptInteractorTest: LastPromptInteractorTest)
    fun inject(neverAskInteractorTest: NeverAskInteractorTest)
    fun inject(sessionCountInteractorTest: SessionCountInteractorTest)
    fun inject(getStoreLinkInteractorTest: GetStoreLinkInteractorTest)
    fun inject(getPackageNameInteractorTest: GetPackageNameInteractorTest)
    fun inject(shouldShowRatingInteractorTest: ShouldShowRatingInteractorTest)
    fun inject(clearAllInteractorTest: ClearAllInteractorTest)
}
