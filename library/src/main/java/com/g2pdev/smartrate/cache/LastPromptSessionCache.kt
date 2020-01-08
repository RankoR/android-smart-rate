package com.g2pdev.smartrate.cache

import android.content.Context
import com.google.gson.Gson

internal class LastPromptSessionCache(
    gson: Gson,
    context: Context
) : BaseCache<Int>(gson, context, name) {

    override fun getType() = Int::class.java

    private companion object {
        private const val name = "last_prompt_session"
    }
}
