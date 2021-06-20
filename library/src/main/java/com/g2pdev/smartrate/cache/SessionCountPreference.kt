package com.g2pdev.smartrate.cache

import android.content.Context
import pro.labster.typedpreferences.IntPreference
import pro.labster.typedpreferences.IntPreferenceImpl

interface SessionCountPreference : IntPreference

internal class SessionCountPreferenceImpl(
    context: Context
) : IntPreferenceImpl(context, NAME), SessionCountPreference {

    private companion object {
        private const val NAME = "sr2_session_count"
    }
}
