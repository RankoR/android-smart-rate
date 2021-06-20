package com.g2pdev.smartrate.interactor.never_ask

import com.g2pdev.smartrate.repository.RateRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal interface SetNeverAsk {
    suspend fun exec(neverAsk: Boolean)
}

internal class SetNeverAskImpl(
    private val rateRepository: RateRepository
) : SetNeverAsk {

    override suspend fun exec(neverAsk: Boolean) {
        withContext(Dispatchers.IO) {
            rateRepository.setNeverAsk(neverAsk)
        }
    }
}
