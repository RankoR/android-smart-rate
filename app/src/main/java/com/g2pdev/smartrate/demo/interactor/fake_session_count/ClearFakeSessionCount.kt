package com.g2pdev.smartrate.demo.interactor.fake_session_count

import com.g2pdev.smartrate.demo.repository.SettingsRepository
import io.reactivex.Completable

interface ClearFakeSessionCount {
    fun exec(): Completable
}

internal class ClearFakeSessionCountImpl(
    private val settingsRepository: SettingsRepository
) : ClearFakeSessionCount {

    override fun exec(): Completable {
        return settingsRepository.setFakeSessionCount(0)
    }
}
