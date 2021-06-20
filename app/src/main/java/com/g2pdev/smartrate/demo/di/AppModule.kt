package com.g2pdev.smartrate.demo.di

import android.content.Context
import com.g2pdev.smartrate.demo.data.preference.FakeSessionCountPreference
import com.g2pdev.smartrate.demo.data.preference.FakeSessionCountPreferenceImpl
import com.g2pdev.smartrate.demo.data.preference.SessionCountBetweenPromptsPreference
import com.g2pdev.smartrate.demo.data.preference.SessionCountBetweenPromptsPreferenceImpl
import com.g2pdev.smartrate.demo.data.preference.SessionCountPreference
import com.g2pdev.smartrate.demo.data.preference.SessionCountPreferenceImpl
import com.g2pdev.smartrate.demo.data.repository.SettingsRepository
import com.g2pdev.smartrate.demo.data.repository.SettingsRepositoryImpl
import com.g2pdev.smartrate.demo.domain.interactor.GetSessionCount
import com.g2pdev.smartrate.demo.domain.interactor.GetSessionCountImpl
import com.g2pdev.smartrate.demo.domain.interactor.SetSessionCount
import com.g2pdev.smartrate.demo.domain.interactor.SetSessionCountImpl
import com.g2pdev.smartrate.demo.domain.interactor.fake_session_count.ClearFakeSessionCount
import com.g2pdev.smartrate.demo.domain.interactor.fake_session_count.ClearFakeSessionCountImpl
import com.g2pdev.smartrate.demo.domain.interactor.fake_session_count.GetFakeSessionCount
import com.g2pdev.smartrate.demo.domain.interactor.fake_session_count.GetFakeSessionCountImpl
import com.g2pdev.smartrate.demo.domain.interactor.fake_session_count.IncrementFakeSessionCount
import com.g2pdev.smartrate.demo.domain.interactor.fake_session_count.IncrementFakeSessionCountImpl
import com.g2pdev.smartrate.demo.domain.interactor.session_count.GetSessionCountBetweenPrompts
import com.g2pdev.smartrate.demo.domain.interactor.session_count.GetSessionCountBetweenPromptsImpl
import com.g2pdev.smartrate.demo.domain.interactor.session_count.SetSessionCountBetweenPrompts
import com.g2pdev.smartrate.demo.domain.interactor.session_count.SetSessionCountBetweenPromptsImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Main dagger module
 */
@Module
class AppModule(
    private val context: Context
) {

    @Provides
    @Singleton
    fun provideContext(): Context = context

    @Provides
    @Singleton
    fun provideSessionCountPreference(
        context: Context
    ): SessionCountPreference = SessionCountPreferenceImpl(context)

    @Provides
    @Singleton
    fun provideFakeSessionCountCache(
        context: Context
    ): FakeSessionCountPreference = FakeSessionCountPreferenceImpl(context)

    @Provides
    @Singleton
    fun provideSessionCountBetweenPromptsCache(
        context: Context
    ): SessionCountBetweenPromptsPreference = SessionCountBetweenPromptsPreferenceImpl(context)

    @Provides
    @Singleton
    fun provideSettingsRepository(
        sessionCountPreference: SessionCountPreference,
        sessionCountBetweenPromptsPreference: SessionCountBetweenPromptsPreference,
        fakeSessionCountPreference: FakeSessionCountPreference
    ): SettingsRepository = SettingsRepositoryImpl(
        sessionCountPreference,
        sessionCountBetweenPromptsPreference,
        fakeSessionCountPreference
    )

    @Provides
    @Singleton
    fun provideGetSessionCount(
        settingsRepository: SettingsRepository
    ): GetSessionCount = GetSessionCountImpl(settingsRepository)

    @Provides
    @Singleton
    fun provideSetSessionCount(
        settingsRepository: SettingsRepository
    ): SetSessionCount = SetSessionCountImpl(settingsRepository)

    @Provides
    @Singleton
    fun provideGetSessionCountBetweenPrompts(
        settingsRepository: SettingsRepository
    ): GetSessionCountBetweenPrompts = GetSessionCountBetweenPromptsImpl(settingsRepository)

    @Provides
    @Singleton
    fun provideSetSessionCountBetweenPrompts(
        settingsRepository: SettingsRepository
    ): SetSessionCountBetweenPrompts = SetSessionCountBetweenPromptsImpl(settingsRepository)

    @Provides
    @Singleton
    fun provideGetFakeSessionCount(
        settingsRepository: SettingsRepository
    ): GetFakeSessionCount = GetFakeSessionCountImpl(settingsRepository)

    @Provides
    @Singleton
    fun provideIncrementFakeSessionCount(
        getFakeSessionCount: GetFakeSessionCount,
        settingsRepository: SettingsRepository
    ): IncrementFakeSessionCount =
        IncrementFakeSessionCountImpl(getFakeSessionCount, settingsRepository)

    @Provides
    @Singleton
    fun provideClearFakeSessionCount(
        settingsRepository: SettingsRepository
    ): ClearFakeSessionCount = ClearFakeSessionCountImpl(settingsRepository)
}
