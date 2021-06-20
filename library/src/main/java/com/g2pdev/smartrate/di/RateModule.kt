package com.g2pdev.smartrate.di

import android.content.Context
import android.content.pm.PackageManager
import com.g2pdev.smartrate.data.preference.IsRatedPreference
import com.g2pdev.smartrate.data.preference.IsRatedPreferenceImpl
import com.g2pdev.smartrate.data.preference.LastPromptSessionPreference
import com.g2pdev.smartrate.data.preference.LastPromptSessionPreferenceImpl
import com.g2pdev.smartrate.data.preference.NeverAskPreference
import com.g2pdev.smartrate.data.preference.NeverAskPreferenceImpl
import com.g2pdev.smartrate.data.preference.SessionCountPreference
import com.g2pdev.smartrate.data.preference.SessionCountPreferenceImpl
import com.g2pdev.smartrate.data.repository.RateRepository
import com.g2pdev.smartrate.data.repository.RateRepositoryImpl
import com.g2pdev.smartrate.domain.StoreLinkResolver
import com.g2pdev.smartrate.domain.StoreLinkResolverImpl
import com.g2pdev.smartrate.domain.interactor.ClearAll
import com.g2pdev.smartrate.domain.interactor.ClearAllImpl
import com.g2pdev.smartrate.domain.interactor.GetAppIcon
import com.g2pdev.smartrate.domain.interactor.GetAppIconImpl
import com.g2pdev.smartrate.domain.interactor.GetPackageName
import com.g2pdev.smartrate.domain.interactor.GetPackageNameImpl
import com.g2pdev.smartrate.domain.interactor.ShouldShowRating
import com.g2pdev.smartrate.domain.interactor.ShouldShowRatingImpl
import com.g2pdev.smartrate.domain.interactor.is_rated.IsRated
import com.g2pdev.smartrate.domain.interactor.is_rated.IsRatedImpl
import com.g2pdev.smartrate.domain.interactor.is_rated.SetIsRated
import com.g2pdev.smartrate.domain.interactor.is_rated.SetIsRatedImpl
import com.g2pdev.smartrate.domain.interactor.last_prompt.GetLastPromptSession
import com.g2pdev.smartrate.domain.interactor.last_prompt.GetLastPromptSessionImpl
import com.g2pdev.smartrate.domain.interactor.last_prompt.SetLastPromptSessionToCurrent
import com.g2pdev.smartrate.domain.interactor.last_prompt.SetLastPromptSessionToCurrentImpl
import com.g2pdev.smartrate.domain.interactor.never_ask.IsNeverAsk
import com.g2pdev.smartrate.domain.interactor.never_ask.IsNeverAskImpl
import com.g2pdev.smartrate.domain.interactor.never_ask.SetNeverAsk
import com.g2pdev.smartrate.domain.interactor.never_ask.SetNeverAskImpl
import com.g2pdev.smartrate.domain.interactor.session_count.GetSessionCount
import com.g2pdev.smartrate.domain.interactor.session_count.GetSessionCountImpl
import com.g2pdev.smartrate.domain.interactor.session_count.IncrementSessionCount
import com.g2pdev.smartrate.domain.interactor.session_count.IncrementSessionCountImpl
import com.g2pdev.smartrate.domain.interactor.store.GetStoreLink
import com.g2pdev.smartrate.domain.interactor.store.GetStoreLinkImpl
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
    fun provideSessionCountPreference(
        context: Context
    ): SessionCountPreference = SessionCountPreferenceImpl(context)

    @Provides
    @Singleton
    fun provideIsRatedPreference(
        context: Context
    ): IsRatedPreference = IsRatedPreferenceImpl(context)

    @Provides
    @Singleton
    fun provideNeverAskPreference(
        context: Context
    ): NeverAskPreference = NeverAskPreferenceImpl(context)

    @Provides
    @Singleton
    fun provideLastPromptSessionPreference(
        context: Context
    ): LastPromptSessionPreference = LastPromptSessionPreferenceImpl(context)

    @Provides
    @Singleton
    fun provideRateRepository(
        sessionCountPreference: SessionCountPreference,
        isRatedPreference: IsRatedPreference,
        neverAskPreference: NeverAskPreference,
        lastPromptSessionPreference: LastPromptSessionPreference
    ): RateRepository = RateRepositoryImpl(sessionCountPreference, isRatedPreference, neverAskPreference, lastPromptSessionPreference)

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
    ): IncrementSessionCount = IncrementSessionCountImpl(rateRepository, getSessionCount)

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
    ): SetLastPromptSessionToCurrent = SetLastPromptSessionToCurrentImpl(rateRepository, getSessionCount)

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
    ): ShouldShowRating = ShouldShowRatingImpl(getSessionCount, isRated, isNeverAsk, getLastPromptSession)

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

    @Provides
    @Singleton
    fun provideClearAll(
        rateRepository: RateRepository
    ): ClearAll = ClearAllImpl(rateRepository)
}
