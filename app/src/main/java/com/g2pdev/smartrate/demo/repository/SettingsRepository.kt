package com.g2pdev.smartrate.demo.repository

import com.g2pdev.smartrate.demo.cache.FakeSessionCountPreference
import com.g2pdev.smartrate.demo.cache.SessionCountBetweenPromptsPreference
import com.g2pdev.smartrate.demo.cache.SessionCountPreference

/**
 * Stores and retrieves various parameters for app and library
 */
interface SettingsRepository {
    fun getSessionCount(): Int
    fun setSessionCount(sessionCount: Int)

    fun getSessionCountBetweenPrompts(): Int
    fun setSessionCountBetweenPrompts(sessionCount: Int)

    fun getFakeSessionCount(): Int
    fun setFakeSessionCount(sessionCount: Int)
}

internal class SettingsRepositoryImpl(
    private val sessionCountPreference: SessionCountPreference,
    private val sessionCountBetweenPromptsPreference: SessionCountBetweenPromptsPreference,
    private val fakeSessionCountPreference: FakeSessionCountPreference
) : SettingsRepository {

    override fun getSessionCount(): Int {
        return sessionCountPreference.get() ?: DEFAULT_SESSION_COUNT
    }

    override fun setSessionCount(sessionCount: Int) {
        sessionCountPreference.put(sessionCount)
    }

    override fun getSessionCountBetweenPrompts(): Int {
        return sessionCountBetweenPromptsPreference.get() ?: DEFAULT_SESSION_COUNT_BETWEEN_PROMPTS
    }

    override fun setSessionCountBetweenPrompts(sessionCount: Int) {
        sessionCountBetweenPromptsPreference.put(sessionCount)
    }

    override fun getFakeSessionCount(): Int {
        return fakeSessionCountPreference.get() ?: DEFAULT_FAKE_SESSION_COUNT
    }

    override fun setFakeSessionCount(sessionCount: Int) {
        fakeSessionCountPreference.put(sessionCount)
    }

    private companion object {
        private const val DEFAULT_SESSION_COUNT = 3
        private const val DEFAULT_SESSION_COUNT_BETWEEN_PROMPTS = 3
        private const val DEFAULT_FAKE_SESSION_COUNT = 0
    }
}
