package com.g2pdev.smartrate.logic.model

import android.os.Parcelable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.g2pdev.smartrate.logic.model.config.SmartRateConfig
import kotlinx.android.parcel.Parcelize

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
) : Parcelable {


    fun SmartRateConfig.getRateConfig(): RateConfig {
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

}