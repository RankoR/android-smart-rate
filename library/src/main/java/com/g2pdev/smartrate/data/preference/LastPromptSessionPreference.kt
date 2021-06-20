package com.g2pdev.smartrate.data.preference

import android.content.Context
import pro.labster.typedpreferences.IntPreference
import pro.labster.typedpreferences.IntPreferenceImpl

interface LastPromptSessionPreference : IntPreference

internal class LastPromptSessionPreferenceImpl(
    context: Context
) : IntPreferenceImpl(context, NAME), LastPromptSessionPreference {

    private companion object {
        private const val NAME = "sr2_last_prompt_session"
    }
}
