package com.g2pdev.smartrate.logic.model.config

internal fun SmartRateConfig.getRateConfig(): RateConfig {
    return RateConfig(
        iconDrawableResId = iconDrawableResId,
        titleResId = titleResId,
        neverAskResId = rateNeverAskResId,
        laterResId = rateLaterResId,
        titleTextColorResId = rateTitleTextColorResId,
        neverAskButtonTextColorResId = rateNeverAskButtonTextColorResId,
        laterButtonTextColorResId = rateLaterButtonTextColorResId,
        isDismissible = isRateDismissible
    )
}

internal fun SmartRateConfig.getFeedbackConfig(): FeedbackConfig {
    return FeedbackConfig(
        titleResId = feedbackTitleResId,
        hintResId = feedbackHintResId,
        cancelResId = feedbackCancelResId,
        submitResId = feedbackSubmitResId,
        titleTextColorResId = feedbackTitleTextColorResId,
        cancelButtonTextColorResId = feedbackCancelButtonTextColorResId,
        submitButtonTextColorResId = feedbackSubmitButtonTextColorResId,
        isDismissible = isFeedbackDismissible,
        minFeedbackLength = minFeedbackLength
    )
}
