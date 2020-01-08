package com.g2pdev.smartrate.cache

import android.content.Context
import com.google.gson.Gson

internal class IsRatedCache(
    gson: Gson,
    context: Context
) : BaseCache<Boolean>(gson, context, name) {

    override fun getType() = Boolean::class.java

    private companion object {
        private const val name = "sr_is_rated"
    }
}
