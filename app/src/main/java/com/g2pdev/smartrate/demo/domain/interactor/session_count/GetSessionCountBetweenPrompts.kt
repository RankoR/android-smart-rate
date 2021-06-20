package com.g2pdev.smartrate.demo.domain.interactor.session_count

import com.g2pdev.smartrate.demo.data.repository.SettingsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface GetSessionCountBetweenPrompts {
    suspend fun exec(): Int
}

internal class GetSessionCountBetweenPromptsImpl(
    private val settingsRepository: SettingsRepository
) : GetSessionCountBetweenPrompts {

    override suspend fun exec(): Int {
        return withContext(Dispatchers.IO) {
            settingsRepository.getSessionCountBetweenPrompts()
        }
    }
}
