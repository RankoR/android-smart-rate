package com.g2pdev.smartrate.interactor

import com.g2pdev.smartrate.interactor.is_rated.IsRated
import com.g2pdev.smartrate.interactor.last_prompt.GetLastPromptSession
import com.g2pdev.smartrate.interactor.never_ask.IsNeverAsk
import com.g2pdev.smartrate.interactor.session_count.GetSessionCount
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal interface ShouldShowRating {
    suspend fun exec(minSessionCount: Int, minSessionCountBetweenPrompts: Int): Boolean
}

internal class ShouldShowRatingImpl(
    private val getSessionCount: GetSessionCount,
    private val isRated: IsRated,
    private val isNeverAsk: IsNeverAsk,
    private val getLastPromptSession: GetLastPromptSession
) : ShouldShowRating {

    override suspend fun exec(minSessionCount: Int, minSessionCountBetweenPrompts: Int): Boolean {
        return withContext(Dispatchers.IO) {
            if (isRated.exec() || isNeverAsk.exec()) {
                return@withContext false
            }

            if (getSessionCount.exec() >= minSessionCount) {
                return@withContext getSessionCountBetweenLastPrompt() >= minSessionCountBetweenPrompts
            }

            return@withContext false
        }
    }

    private fun getSessionCountBetweenLastPrompt(): Int {
        return getSessionCount.exec() - getLastPromptSession.exec()
    }
}
