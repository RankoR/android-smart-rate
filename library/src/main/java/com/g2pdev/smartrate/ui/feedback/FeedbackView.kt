package com.g2pdev.smartrate.ui.feedback

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface FeedbackView : MvpView {
    fun enableSubmitButton(enable: Boolean)

    @StateStrategyType(SkipStrategy::class)
    fun close()
}
