package com.g2pdev.smartrate.di

import android.content.Context
import android.content.pm.PackageManager
import com.g2pdev.smartrate.cache.IsRatedCache
import com.g2pdev.smartrate.cache.LastPromptSessionCache
import com.g2pdev.smartrate.cache.NeverAskCache
import com.g2pdev.smartrate.cache.SessionCountCache
import com.g2pdev.smartrate.interactor.*
import com.g2pdev.smartrate.interactor.is_rated.IsRated
import com.g2pdev.smartrate.interactor.is_rated.IsRatedImpl
import com.g2pdev.smartrate.interactor.is_rated.SetIsRated
import com.g2pdev.smartrate.interactor.is_rated.SetIsRatedImpl
import com.g2pdev.smartrate.interactor.last_prompt.GetLastPromptSession
import com.g2pdev.smartrate.interactor.last_prompt.GetLastPromptSessionImpl
import com.g2pdev.smartrate.interactor.last_prompt.SetLastPromptSessionToCurrent
import com.g2pdev.smartrate.interactor.last_prompt.SetLastPromptSessionToCurrentImpl
import com.g2pdev.smartrate.interactor.never_ask.IsNeverAsk
import com.g2pdev.smartrate.interactor.never_ask.IsNeverAskImpl
import com.g2pdev.smartrate.interactor.never_ask.SetNeverAsk
import com.g2pdev.smartrate.interactor.never_ask.SetNeverAskImpl
import com.g2pdev.smartrate.interactor.session_count.GetSessionCount
import com.g2pdev.smartrate.interactor.session_count.GetSessionCountImpl
import com.g2pdev.smartrate.interactor.session_count.IncrementSessionCount
import com.g2pdev.smartrate.interactor.session_count.IncrementSessionCountImpl
import com.g2pdev.smartrate.interactor.store.GetStoreLink
import com.g2pdev.smartrate.interactor.store.GetStoreLinkImpl
import com.g2pdev.smartrate.logic.StoreLinkResolver
import com.g2pdev.smartrate.logic.StoreLinkResolverImpl
import com.g2pdev.smartrate.repository.RateRepository
import com.g2pdev.smartrate.repository.RateRepositoryImpl
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal open class RateModule(
    private val context: Context
) {

    @Provides
    @Singleton
    fun provideContext(): Context = context

    @Provides
    @Singleton
    fun providePackageManager(
        context: Context
    ): PackageManager = context.packageManager

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideSessionCountCache(
        gson: Gson,
        context: Context
    ): SessionCountCache = SessionCountCache(gson, context)

    @Provides
    @Singleton
    fun provideIsRatedCache(
        gson: Gson,
        context: Context
    ): IsRatedCache = IsRatedCache(gson, context)

    @Provides
    @Singleton
    fun provideNeverAskCache(
        gson: Gson,
        context: Context
    ): NeverAskCache = NeverAskCache(gson, context)

    @Provides
    @Singleton
    fun provideLastPromptSessionCache(
        gson: Gson,
        context: Context
    ): LastPromptSessionCache = LastPromptSessionCache(gson, context)

    @Provides
    @Singleton
    fun provideRateRepository(
        sessionCountCache: SessionCountCache,
        isRatedCache: IsRatedCache,
        neverAskCache: NeverAskCache,
        lastPromptSessionCache: LastPromptSessionCache
    ): RateRepository =
        RateRepositoryImpl(sessionCountCache, isRatedCache, neverAskCache, lastPromptSessionCache)

    @Provides
    @Singleton
    fun provideGetSessionCount(
        rateRepository: RateRepository
    ): GetSessionCount = GetSessionCountImpl(rateRepository)

    @Provides
    @Singleton
    fun provideIncrementSessionCount(
        rateRepository: RateRepository,
        getSessionCount: GetSessionCount
    ): IncrementSessionCount =
        IncrementSessionCountImpl(rateRepository, getSessionCount)

    @Provides
    @Singleton
    fun provideIsNeverAsk(
        rateRepository: RateRepository
    ): IsNeverAsk = IsNeverAskImpl(rateRepository)

    @Provides
    @Singleton
    fun provideSetNeverAsk(
        rateRepository: RateRepository
    ): SetNeverAsk = SetNeverAskImpl(rateRepository)

    @Provides
    @Singleton
    fun provideGetLastPromptSession(
        rateRepository: RateRepository
    ): GetLastPromptSession = GetLastPromptSessionImpl(rateRepository)

    @Provides
    @Singleton
    fun provideSetLastPromptSessionToCurrent(
        rateRepository: RateRepository,
        getSessionCount: GetSessionCount
    ): SetLastPromptSessionToCurrent =
        SetLastPromptSessionToCurrentImpl(rateRepository, getSessionCount)

    @Provides
    @Singleton
    fun provideIsRated(
        rateRepository: RateRepository
    ): IsRated = IsRatedImpl(rateRepository)

    @Provides
    @Singleton
    fun provideSetIsRated(
        rateRepository: RateRepository
    ): SetIsRated = SetIsRatedImpl(rateRepository)

    @Provides
    @Singleton
    fun provideShouldShowRating(
        getSessionCount: GetSessionCount,
        isRated: IsRated,
        isNeverAsk: IsNeverAsk,
        getLastPromptSession: GetLastPromptSession
    ): ShouldShowRating =
        ShouldShowRatingImpl(getSessionCount, isRated, isNeverAsk, getLastPromptSession)

    @Provides
    @Singleton
    fun provideStoreLinkResolver(): StoreLinkResolver = StoreLinkResolverImpl()

    @Provides
    @Singleton
    fun provideGetStoreLink(
        storeLinkResolver: StoreLinkResolver
    ): GetStoreLink = GetStoreLinkImpl(storeLinkResolver)

    @Provides
    @Singleton
    fun provideGetPackageName(
        context: Context
    ): GetPackageName = GetPackageNameImpl(context)

    @Provides
    @Singleton
    fun provideGetAppIcon(
        packageManager: PackageManager,
        getPackageName: GetPackageName
    ): GetAppIcon = GetAppIconImpl(packageManager, getPackageName)

}