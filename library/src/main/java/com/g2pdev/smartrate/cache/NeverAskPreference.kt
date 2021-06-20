package com.g2pdev.smartrate.cache

import android.content.Context
import pro.labster.typedpreferences.BooleanPreference
import pro.labster.typedpreferences.BooleanPreferenceImpl

interface NeverAskPreference : BooleanPreference

internal class NeverAskPreferenceImpl(
    context: Context
) : BooleanPreferenceImpl(context, NAME), NeverAskPreference {

    private companion object {
        private const val NAME = "sr2_never_ask"
    }
}
