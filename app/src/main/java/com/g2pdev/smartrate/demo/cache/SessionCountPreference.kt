package com.g2pdev.smartrate.demo.cache

import android.content.Context
import pro.labster.typedpreferences.IntPreference
import pro.labster.typedpreferences.IntPreferenceImpl

/**
 * Stores session count setting (value is used in library config)
 */
interface SessionCountPreference : IntPreference

internal class SessionCountPreferenceImpl(
    context: Context
) : IntPreferenceImpl(context, NAME), SessionCountPreference {

    private companion object {
        private const val NAME = "session_count_2"
    }
}
