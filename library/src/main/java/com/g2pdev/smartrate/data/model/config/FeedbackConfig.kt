package com.g2pdev.smartrate.data.model.config

import android.os.Parcelable
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import kotlinx.parcelize.Parcelize

@Parcelize
internal data class FeedbackConfig(
    @StringRes
    val titleResId: Int,

    @StringRes
    val hintResId: Int,

    @StringRes
    val cancelResId: Int,

    @StringRes
    val submitResId: Int,

    @ColorRes
    var titleTextColorResId: Int,

    @ColorRes
    var cancelButtonTextColorResId: Int,

    @ColorRes
    var submitButtonTextColorResId: Int?,

    val isDismissible: Boolean,

    val minFeedbackLength: Int
) : Parcelable
