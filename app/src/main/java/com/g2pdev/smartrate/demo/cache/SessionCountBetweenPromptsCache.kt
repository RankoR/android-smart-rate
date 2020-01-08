package com.g2pdev.smartrate.demo.cache

import android.content.Context
import com.google.gson.Gson

class SessionCountBetweenPromptsCache(
    gson: Gson,
    context: Context
) : BaseCache<Int>(gson, context, name) {

    override fun getType() = Int::class.java

    private companion object {
        private const val name = "session_count_between_prompts"
    }
}
