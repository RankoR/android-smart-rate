package com.g2pdev.smartrate.interactor.last_prompt

import com.g2pdev.smartrate.repository.RateRepository
import io.reactivex.Single

internal interface GetLastPromptSession {
    fun exec(): Single<Int>
}

internal class GetLastPromptSessionImpl(
    private val rateRepository: RateRepository
) : GetLastPromptSession {

    override fun exec(): Single<Int> {
        return rateRepository.getLastPromptSession()
    }
}
