package com.g2pdev.smartrate.demo.interactor.session_count

import com.g2pdev.smartrate.demo.repository.SettingsRepository
import io.reactivex.Single

interface GetSessionCountBetweenPrompts {
    fun exec(): Single<Int>
}

internal class GetSessionCountBetweenPromptsImpl(
    private val settingsRepository: SettingsRepository
) : GetSessionCountBetweenPrompts {

    override fun exec(): Single<Int> {
        return settingsRepository.getSessionCountBetweenPrompts()
    }
}