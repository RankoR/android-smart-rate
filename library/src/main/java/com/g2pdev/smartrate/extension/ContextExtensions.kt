package com.g2pdev.smartrate.extension

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.res.ResourcesCompat

@ColorInt
fun Context.getColorCompat(@ColorRes colorResId: Int): Int {
    return ResourcesCompat.getColor(resources, colorResId, theme)
}

fun Context.getDrawableCompat(@DrawableRes drawableResId: Int): Drawable? {
    return ResourcesCompat.getDrawable(resources, drawableResId, theme)
}
