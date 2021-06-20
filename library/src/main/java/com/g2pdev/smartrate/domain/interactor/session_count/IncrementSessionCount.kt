package com.g2pdev.smartrate.domain.interactor.session_count

import com.g2pdev.smartrate.data.repository.RateRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal interface IncrementSessionCount {
    suspend fun exec()
}

internal class IncrementSessionCountImpl(
    private val rateRepository: RateRepository,
    private val getSessionCount: GetSessionCount
) : IncrementSessionCount {

    override suspend fun exec() {
        withContext(Dispatchers.IO) {
            getSessionCount
                .exec()
                .let { it + 1 }
                .let(rateRepository::setSessionCount)
        }
    }
}
