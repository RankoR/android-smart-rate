package com.g2pdev.smartrate.interactor.last_prompt

import com.g2pdev.smartrate.repository.RateRepository

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
