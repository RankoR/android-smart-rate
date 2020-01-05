package com.g2pdev.smartrate.logic.model.config

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.g2pdev.smartrate.R
import com.g2pdev.smartrate.logic.model.Store

data class SmartRateConfig(

    var minSessionCount: Int = defaultMinSessionCount,

    var minSessionCountBetweenPrompts: Int = defaultMinSessionCountBetweenPrompts,

    var minRatingForStore: Float = defaultMinRatingForStore,

    @DrawableRes
    var iconDrawableResId: Int? = null,

    @StringRes
    var titleResId: Int = R.string.sr_title,

    @StringRes
    var rateNeverAskResId: Int = R.string.sr_never_ask,

    @StringRes
    var rateLaterResId: Int = R.string.sr_maybe_later,

    @ColorRes
    var rateTitleTextColorResId: Int = R.color.rateTitleText,

    @ColorRes
    var rateNeverAskButtonTextColorResId: Int = R.color.rateNeverButtonText,

    @ColorRes
    var rateLaterButtonTextColorResId: Int? = null,

    @StringRes
    var feedbackTitleResId: Int = R.string.sr_title_feedback,

    @StringRes
    var feedbackHintResId: Int = R.string.sr_hint_feedback,

    @StringRes
    var feedbackCancelResId: Int = R.string.sr_feedback_cancel,

    @StringRes
    var feedbackSubmitResId: Int = R.string.sr_feedback_submit,

    @ColorRes
    var feedbackTitleTextColorResId: Int = R.color.feedbackTitleText,

    @ColorRes
    var feedbackCancelButtonTextColorResId: Int = R.color.feedbackCancelButtonText,

    @ColorRes
    var feedbackSubmitButtonTextColorResId: Int? = null,

    var isRateDismissible: Boolean = false,

    var store: Store = Store.GOOGLE_PLAY,

    var customStoreLink: String? = null,

    var onRateDialogShowListener: (() -> Unit)? = null,

    var onRateDismissListener: (() -> Unit)? = null,

    var onRateListener: ((rating: Float) -> Unit)? = null,

    var onNeverAskClickListener: (() -> Unit)? = null,

    var onLaterClickListener: (() -> Unit)? = null,

    var showFeedbackDialog: Boolean = true,

    var isFeedbackDismissible: Boolean = false,

    var onFeedbackDismissListener: (() -> Unit)? = null,

    var onFeedbackCancelClickListener: (() -> Unit)? = null,

    var onFeedbackSubmitClickListener: ((text: String) -> Unit)? = null
) {

    companion object {
        const val defaultMinSessionCount = 3
        const val defaultMinSessionCountBetweenPrompts = 3
        const val defaultMinRatingForStore = 4f
    }

}