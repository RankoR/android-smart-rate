package com.g2pdev.smartrate.ui.rate

import android.graphics.drawable.Drawable
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface RateView : MvpView {

    fun showAppIcon(drawable: Drawable)

    @StateStrategyType(SkipStrategy::class)
    fun close()
}
