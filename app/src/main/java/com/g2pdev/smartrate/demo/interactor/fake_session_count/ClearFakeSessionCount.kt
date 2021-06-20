package com.g2pdev.smartrate.demo.interactor.fake_session_count

import com.g2pdev.smartrate.demo.repository.SettingsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface ClearFakeSessionCount {
    suspend fun exec()
}

internal class ClearFakeSessionCountImpl(
    private val settingsRepository: SettingsRepository
) : ClearFakeSessionCount {

    override suspend fun exec() {
        withContext(Dispatchers.IO) {
            settingsRepository.setFakeSessionCount(0)
        }
    }
}
