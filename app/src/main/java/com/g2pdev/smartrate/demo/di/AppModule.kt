package com.g2pdev.smartrate.demo.di

import android.content.Context
import com.g2pdev.smartrate.demo.cache.FakeSessionCountCache
import com.g2pdev.smartrate.demo.cache.SessionCountBetweenPromptsCache
import com.g2pdev.smartrate.demo.cache.SessionCountCache
import com.g2pdev.smartrate.demo.interactor.GetSessionCount
import com.g2pdev.smartrate.demo.interactor.GetSessionCountImpl
import com.g2pdev.smartrate.demo.interactor.SetSessionCount
import com.g2pdev.smartrate.demo.interactor.SetSessionCountImpl
import com.g2pdev.smartrate.demo.interactor.fake_session_count.ClearFakeSessionCount
import com.g2pdev.smartrate.demo.interactor.fake_session_count.ClearFakeSessionCountImpl
import com.g2pdev.smartrate.demo.interactor.fake_session_count.GetFakeSessionCount
import com.g2pdev.smartrate.demo.interactor.fake_session_count.GetFakeSessionCountImpl
import com.g2pdev.smartrate.demo.interactor.fake_session_count.IncrementFakeSessionCount
import com.g2pdev.smartrate.demo.interactor.fake_session_count.IncrementFakeSessionCountImpl
import com.g2pdev.smartrate.demo.interactor.session_count.GetSessionCountBetweenPrompts
import com.g2pdev.smartrate.demo.interactor.session_count.GetSessionCountBetweenPromptsImpl
import com.g2pdev.smartrate.demo.interactor.session_count.SetSessionCountBetweenPrompts
import com.g2pdev.smartrate.demo.interactor.session_count.SetSessionCountBetweenPromptsImpl
import com.g2pdev.smartrate.demo.repository.SettingsRepository
import com.g2pdev.smartrate.demo.repository.SettingsRepositoryImpl
import com.google.gson.Gson
import com.google.gson.GsonBuilder
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
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    fun provideSessionCountCache(
        gson: Gson,
        context: Context
    ): SessionCountCache = SessionCountCache(gson, context)

    @Provides
    @Singleton
    fun provideFakeSessionCountCache(
        gson: Gson,
        context: Context
    ): FakeSessionCountCache = FakeSessionCountCache(gson, context)

    @Provides
    @Singleton
    fun provideSessionCountBetweenPromptsCache(
        gson: Gson,
        context: Context
    ): SessionCountBetweenPromptsCache = SessionCountBetweenPromptsCache(gson, context)

    @Provides
    @Singleton
    fun provideSettingsRepository(
        sessionCountCache: SessionCountCache,
        sessionCountBetweenPromptsCache: SessionCountBetweenPromptsCache,
        fakeSessionCountCache: FakeSessionCountCache
    ): SettingsRepository =
        SettingsRepositoryImpl(
            sessionCountCache,
            sessionCountBetweenPromptsCache,
            fakeSessionCountCache
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
