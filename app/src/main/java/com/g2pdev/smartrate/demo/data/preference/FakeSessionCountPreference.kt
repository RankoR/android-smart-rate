package com.g2pdev.smartrate.demo.data.preference

import android.content.Context
import pro.labster.typedpreferences.IntPreference
import pro.labster.typedpreferences.IntPreferenceImpl

/**
 * Imitates internal session counter from library
 */
interface FakeSessionCountPreference : IntPreference

internal class FakeSessionCountPreferenceImpl(
    context: Context
) : IntPreferenceImpl(context, NAME), FakeSessionCountPreference {

    private companion object {
        private const val NAME = "fake_session_count_2"
    }
}
