package com.g2pdev.smartrate.demo.domain.interactor.fake_session_count

import com.g2pdev.smartrate.SmartRate
import com.g2pdev.smartrate.demo.data.repository.SettingsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface IncrementFakeSessionCount {
    suspend fun exec()
}

internal class IncrementFakeSessionCountImpl(
    private val getFakeSessionCount: GetFakeSessionCount,
    private val settingsRepository: SettingsRepository
) : IncrementFakeSessionCount {

    override suspend fun exec() {
        withContext(Dispatchers.IO) {
            getFakeSessionCount
                .exec()
                .let { it + 1 }
                .let(settingsRepository::setFakeSessionCount)

            @Suppress("DEPRECATION")
            SmartRate.testIncrementSessionCount()
        }
    }
}
