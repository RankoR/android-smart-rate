package com.g2pdev.smartrate.domain.interactor.last_prompt

import com.g2pdev.smartrate.data.repository.RateRepository

internal interface GetLastPromptSession {
    fun exec(): Int
}

internal class GetLastPromptSessionImpl(
    private val rateRepository: RateRepository
) : GetLastPromptSession {

    override fun exec(): Int {
        return rateRepository.getLastPromptSession()
    }
}
