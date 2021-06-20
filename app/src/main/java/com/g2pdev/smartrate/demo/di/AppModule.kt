package com.g2pdev.smartrate.demo.di

import android.content.Context
import com.g2pdev.smartrate.demo.cache.*
import com.g2pdev.smartrate.demo.interactor.GetSessionCount
import com.g2pdev.smartrate.demo.interactor.GetSessionCountImpl
import com.g2pdev.smartrate.demo.interactor.SetSessionCount
import com.g2pdev.smartrate.demo.interactor.SetSessionCountImpl
import com.g2pdev.smartrate.demo.interactor.fake_session_count.*
import com.g2pdev.smartrate.demo.interactor.session_count.GetSessionCountBetweenPrompts
import com.g2pdev.smartrate.demo.interactor.session_count.GetSessionCountBetweenPromptsImpl
import com.g2pdev.smartrate.demo.interactor.session_count.SetSessionCountBetweenPrompts
import com.g2pdev.smartrate.demo.interactor.session_count.SetSessionCountBetweenPromptsImpl
import com.g2pdev.smartrate.demo.repository.SettingsRepository
import com.g2pdev.smartrate.demo.repository.SettingsRepositoryImpl
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
