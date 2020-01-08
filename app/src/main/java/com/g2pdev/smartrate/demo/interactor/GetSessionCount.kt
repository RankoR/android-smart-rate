package com.g2pdev.smartrate.demo.interactor

import com.g2pdev.smartrate.demo.repository.SettingsRepository
import io.reactivex.Single

interface GetSessionCount {
    fun exec(): Single<Int>
}

internal class GetSessionCountImpl(
    private val settingsRepository: SettingsRepository
) : GetSessionCount {

    override fun exec(): Single<Int> {
        return settingsRepository.getSessionCount()
    }
}
