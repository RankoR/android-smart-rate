package com.g2pdev.smartrate.data.model.config

import android.os.Parcelable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import kotlinx.parcelize.Parcelize

@Parcelize
internal data class RateConfig(
    @DrawableRes
    val iconDrawableResId: Int?,

    @StringRes
    val titleResId: Int,

    @StringRes
    val neverAskResId: Int,

    @StringRes
    val laterResId: Int,

    @ColorRes
    var titleTextColorResId: Int,

    @ColorRes
    var neverAskButtonTextColorResId: Int,

    @ColorRes
    var laterButtonTextColorResId: Int?,

    val isDismissible: Boolean
) : Parcelable
