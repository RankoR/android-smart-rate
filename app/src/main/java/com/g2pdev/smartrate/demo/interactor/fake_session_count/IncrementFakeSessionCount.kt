package com.g2pdev.smartrate.demo.interactor.fake_session_count

import com.g2pdev.smartrate.SmartRate
import com.g2pdev.smartrate.demo.repository.SettingsRepository
import io.reactivex.Completable

interface IncrementFakeSessionCount {
    fun exec(): Completable
}

internal class IncrementFakeSessionCountImpl(
    private val getFakeSessionCount: GetFakeSessionCount,
    private val settingsRepository: SettingsRepository
) : IncrementFakeSessionCount {

    override fun exec(): Completable {
        return getFakeSessionCount
            .exec()
            .map { it + 1 }
            .flatMapCompletable(settingsRepository::setFakeSessionCount)
            .doOnComplete { SmartRate.testIncrementSessionCount() }
    }
}