package com.g2pdev.smartrate.data.preference

import android.content.Context
import pro.labster.typedpreferences.BooleanPreference
import pro.labster.typedpreferences.BooleanPreferenceImpl

interface IsRatedPreference : BooleanPreference

internal class IsRatedPreferenceImpl(
    context: Context
) : BooleanPreferenceImpl(context, NAME), IsRatedPreference {

    private companion object {
        private const val NAME = "sr2_is_rated"
    }
}
