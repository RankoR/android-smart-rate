package com.g2pdev.smartrate.interactor.last_prompt

import com.g2pdev.smartrate.interactor.session_count.GetSessionCount
import com.g2pdev.smartrate.repository.RateRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal interface SetLastPromptSessionToCurrent {
    suspend fun exec()
}

internal class SetLastPromptSessionToCurrentImpl(
    private val rateRepository: RateRepository,
    private val getSessionCount: GetSessionCount
) : SetLastPromptSessionToCurrent {

    override suspend fun exec() {
        withContext(Dispatchers.IO) {
            getSessionCount
                .exec()
                .let(rateRepository::setLastPromptSession)
        }
    }
}
