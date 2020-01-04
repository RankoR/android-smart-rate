package com.g2pdev.smartrate.repository

import com.g2pdev.smartrate.cache.IsRatedCache
import com.g2pdev.smartrate.cache.LastPromptSessionCache
import com.g2pdev.smartrate.cache.NeverAskCache
import com.g2pdev.smartrate.cache.SessionCountCache
import io.reactivex.Completable
import io.reactivex.Single

internal interface RateRepository {
    fun getSessionCount(): Single<Int>
    fun setSessionCount(sessionCount: Int): Completable

    fun isRated(): Single<Boolean>
    fun setIsRated(isRated: Boolean): Completable

    fun isNeverAsk(): Single<Boolean>
    fun setNeverAsk(neverAsk: Boolean): Completable

    fun getLastPromptSession(): Single<Int>
    fun setLastPromptSession(session: Int): Completable
}

internal class RateRepositoryImpl(
    private val sessionCountCache: SessionCountCache,
    private val isRatedCache: IsRatedCache,
    private val neverAskCache: NeverAskCache,
    private val lastPromptSessionCache: LastPromptSessionCache
) : RateRepository {


    override fun getSessionCount(): Single<Int> {
        return sessionCountCache.get(defaultSessionCount)
    }

    override fun setSessionCount(sessionCount: Int): Completable {
        return sessionCountCache.put(sessionCount)
    }

    override fun isRated(): Single<Boolean> {
        return isRatedCache.get(defaultIsRated)
    }

    override fun setIsRated(isRated: Boolean): Completable {
        return isRatedCache.put(isRated)
    }

    override fun isNeverAsk(): Single<Boolean> {
        return neverAskCache.get(defaultNeverAsk)
    }

    override fun setNeverAsk(neverAsk: Boolean): Completable {
        return neverAskCache.put(neverAsk)
    }

    override fun getLastPromptSession(): Single<Int> {
        return lastPromptSessionCache.get(defaultLastAskSession)
    }

    override fun setLastPromptSession(session: Int): Completable {
        return lastPromptSessionCache.put(session)
    }

    private companion object {
        private const val defaultSessionCount = 0
        private const val defaultIsRated = false
        private const val defaultNeverAsk = false
        private const val defaultLastAskSession = 0
    }
}