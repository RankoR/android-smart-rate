package com.g2pdev.smartrate.repository

import com.g2pdev.smartrate.cache.IsRatedPreference
import com.g2pdev.smartrate.cache.LastPromptSessionPreference
import com.g2pdev.smartrate.cache.NeverAskPreference
import com.g2pdev.smartrate.cache.SessionCountPreference

internal interface RateRepository {
    fun getSessionCount(): Int
    fun setSessionCount(sessionCount: Int)

    fun isRated(): Boolean
    fun setIsRated(isRated: Boolean)

    fun isNeverAsk(): Boolean
    fun setNeverAsk(neverAsk: Boolean)

    fun getLastPromptSession(): Int
    fun setLastPromptSession(session: Int)

    fun clearAll()
}

internal class RateRepositoryImpl(
    private val sessionCountPreference: SessionCountPreference,
    private val isRatedPreference: IsRatedPreference,
    private val neverAskPreference: NeverAskPreference,
    private val lastPromptSessionPreference: LastPromptSessionPreference
) : RateRepository {

    override fun getSessionCount(): Int {
        return sessionCountPreference.get() ?: DEFAULT_SESSION_COUNT
    }

    override fun setSessionCount(sessionCount: Int) {
        sessionCountPreference.put(sessionCount)
    }

    override fun isRated(): Boolean {
        return isRatedPreference.get() ?: DEFAULT_IS_RATED
    }

    override fun setIsRated(isRated: Boolean) {
        isRatedPreference.put(isRated)
    }

    override fun isNeverAsk(): Boolean {
        return neverAskPreference.get() ?: DEFAULT_NEVER_ASK
    }

    override fun setNeverAsk(neverAsk: Boolean) {
        neverAskPreference.put(neverAsk)
    }

    override fun getLastPromptSession(): Int {
        return lastPromptSessionPreference.get() ?: DEFAULT_LAST_ASK_SESSION
    }

    override fun setLastPromptSession(session: Int) {
        lastPromptSessionPreference.put(session)
    }

    override fun clearAll() {
        sessionCountPreference.delete()
        isRatedPreference.delete()
        neverAskPreference.delete()
        lastPromptSessionPreference.delete()
    }

    private companion object {
        private const val DEFAULT_SESSION_COUNT = 0
        private const val DEFAULT_IS_RATED = false
        private const val DEFAULT_NEVER_ASK = false
        private const val DEFAULT_LAST_ASK_SESSION = 0
    }
}
