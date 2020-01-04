package com.g2pdev.smartrate.demo.interactor.fake_session_count

import com.g2pdev.smartrate.demo.repository.SettingsRepository
import io.reactivex.Single

interface GetFakeSessionCount {
    fun exec(): Single<Int>
}

internal class GetFakeSessionCountImpl(
    private val settingsRepository: SettingsRepository
) : GetFakeSessionCount {

    override fun exec(): Single<Int> {
        return settingsRepository.getFakeSessionCount()
    }
}