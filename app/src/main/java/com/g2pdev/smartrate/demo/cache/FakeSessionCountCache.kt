package com.g2pdev.smartrate.demo.cache

import android.content.Context
import com.google.gson.Gson

/**
 * Imitates internal session counter from library
 */
class FakeSessionCountCache(
    gson: Gson,
    context: Context
) : BaseCache<Int>(gson, context, name) {

    override fun getType() = Int::class.java

    private companion object {
        private const val name = "fake_session_count"
    }
}