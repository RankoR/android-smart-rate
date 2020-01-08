package com.g2pdev.smartrate.demo.repository

import com.g2pdev.smartrate.demo.cache.FakeSessionCountCache
import com.g2pdev.smartrate.demo.cache.SessionCountBetweenPromptsCache
import com.g2pdev.smartrate.demo.cache.SessionCountCache
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Stores and retrieves various parameters for app and library
 */
interface SettingsRepository {
    fun getSessionCount(): Single<Int>
    fun setSessionCount(sessionCount: Int): Completable

    fun getSessionCountBetweenPrompts(): Single<Int>
    fun setSessionCountBetweenPrompts(sessionCount: Int): Completable

    fun getFakeSessionCount(): Single<Int>
    fun setFakeSessionCount(sessionCount: Int): Completable
}

internal class SettingsRepositoryImpl(
    private val sessionCountCache: SessionCountCache,
    private val sessionCountBetweenPromptsCache: SessionCountBetweenPromptsCache,
    private val fakeSessionCountCache: FakeSessionCountCache
) : SettingsRepository {

    override fun getSessionCount(): Single<Int> {
        return sessionCountCache.get(defaultSessionCount)
    }

    override fun setSessionCount(sessionCount: Int): Completable {
        return sessionCountCache.put(sessionCount)
    }

    override fun getSessionCountBetweenPrompts(): Single<Int> {
        return sessionCountBetweenPromptsCache.get(defaultSessionCountBetweenPrompts)
    }

    override fun setSessionCountBetweenPrompts(sessionCount: Int): Completable {
        return sessionCountBetweenPromptsCache.put(sessionCount)
    }

    override fun getFakeSessionCount(): Single<Int> {
        return fakeSessionCountCache.get(defaultFakeSessionCount)
    }

    override fun setFakeSessionCount(sessionCount: Int): Completable {
        return fakeSessionCountCache.put(sessionCount)
    }

    private companion object {
        private const val defaultSessionCount = 3
        private const val defaultSessionCountBetweenPrompts = 3
        private const val defaultFakeSessionCount = 0
    }
}