package com.g2pdev.smartrate.demo.cache

import android.content.Context
import pro.labster.typedpreferences.IntPreference
import pro.labster.typedpreferences.IntPreferenceImpl

interface SessionCountBetweenPromptsPreference : IntPreference

internal class SessionCountBetweenPromptsPreferenceImpl(
    context: Context
) : IntPreferenceImpl(context, NAME), SessionCountBetweenPromptsPreference {

    private companion object {
        private const val NAME = "session_count_between_prompts_2"
    }
}
