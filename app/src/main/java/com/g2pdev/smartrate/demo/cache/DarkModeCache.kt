package com.g2pdev.smartrate.demo.cache

import android.content.Context
import com.google.gson.Gson

/**
 * Stores dark mode setting
 */
class DarkModeCache(
    gson: Gson,
    context: Context
) : BaseCache<Boolean>(gson, context, name) {

    override fun getType() = Boolean::class.java

    private companion object {
        private const val name = "dark_mode"
    }
}
