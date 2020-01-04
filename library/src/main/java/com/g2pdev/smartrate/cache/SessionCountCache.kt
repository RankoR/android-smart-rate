package com.g2pdev.smartrate.cache

import android.content.Context
import com.google.gson.Gson

internal class SessionCountCache(
    gson: Gson,
    context: Context
) : BaseCache<Int>(gson, context, name) {

    override fun getType() = Int::class.java

    private companion object {
        private const val name = "sr_session_count"
    }
}