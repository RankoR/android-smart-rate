package com.g2pdev.smartrate.domain.interactor.last_prompt

import com.g2pdev.smartrate.data.repository.RateRepository
import com.g2pdev.smartrate.domain.interactor.session_count.GetSessionCount
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
