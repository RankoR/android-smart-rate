package com.g2pdev.smartrate.demo.domain.interactor.fake_session_count

import com.g2pdev.smartrate.demo.data.repository.SettingsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface GetFakeSessionCount {
    suspend fun exec(): Int
}

internal class GetFakeSessionCountImpl(
    private val settingsRepository: SettingsRepository
) : GetFakeSessionCount {

    override suspend fun exec(): Int {
        return withContext(Dispatchers.IO) {
            settingsRepository.getFakeSessionCount()
        }
    }
}
