package com.g2pdev.smartrate.interactor

import com.g2pdev.smartrate.interactor.is_rated.IsRated
import com.g2pdev.smartrate.interactor.last_prompt.GetLastPromptSession
import com.g2pdev.smartrate.interactor.never_ask.IsNeverAsk
import com.g2pdev.smartrate.interactor.session_count.GetSessionCount
import io.reactivex.Single
import io.reactivex.functions.BiFunction

internal interface ShouldShowRating {
    fun exec(
        minSessionCount: Int,
        minSessionCountBetweenPrompts: Int
    ): Single<Boolean>
}

internal class ShouldShowRatingImpl(
    private val getSessionCount: GetSessionCount,
    private val isRated: IsRated,
    private val isNeverAsk: IsNeverAsk,
    private val getLastPromptSession: GetLastPromptSession
) : ShouldShowRating {

    override fun exec(minSessionCount: Int, minSessionCountBetweenPrompts: Int): Single<Boolean> {
        return checkIfIsRatedOrNeverAsk()
            .flatMap { isRatedOrNeverAsk ->
                if (isRatedOrNeverAsk) {
                    Single.just(false)
                } else {
                    getSessionCount
                        .exec()
                        .map { it >= minSessionCount }
                        .flatMap { shouldShow ->
                            if (shouldShow) {
                                getSessionCountBetweenLastPrompt()
                                    .map { it >= minSessionCountBetweenPrompts }
                            } else {
                                Single.just(false)
                            }
                        }
                }
            }
    }

    private fun checkIfIsRatedOrNeverAsk(): Single<Boolean> {
        return Single.zip(
            isRated.exec(),
            isNeverAsk.exec(),

            BiFunction { isRated, isNeverAsk -> isRated || isNeverAsk }
        )
    }

    private fun getSessionCountBetweenLastPrompt(): Single<Int> {
        return Single.zip(
            getSessionCount.exec(),
            getLastPromptSession.exec(),

            BiFunction { sessionCount, lastPromptSession ->
                sessionCount - lastPromptSession
            }
        )
    }
}