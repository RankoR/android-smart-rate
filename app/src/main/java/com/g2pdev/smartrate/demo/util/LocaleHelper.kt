package com.g2pdev.smartrate.demo.util

import android.annotation.TargetApi
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import androidx.preference.PreferenceManager
import java.util.*


object LocaleHelper {
    private const val SELECTED_LANGUAGE = "Locale.Helper.Selected.Language"
    fun onAttach(context: Context): Context {
        val lang = getPersistedData(context, Locale.getDefault().language)
        return setLocale(context, lang)
    }

    fun onAttachConfiguration(overrideConfiguration: Configuration, context: Context): Context {
        val lang = getPersistedData(context, Locale.getDefault().language)
        return setLocale(context, lang, overrideConfiguration)
    }

    fun onAttach(context: Context, defaultLanguage: String): Context {
        val lang = getPersistedData(context, defaultLanguage)
        return setLocale(context, lang)
    }

    fun getLanguage(context: Context): String? {
        return getPersistedData(context, Locale.getDefault().language)
    }

    fun setLocale(context: Context, language: String?, configuration: Configuration? = null): Context {
        persist(context, language)
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            updateResources(context, language, configuration ?: context.resources.configuration)
        } else updateResourcesLegacy(context, language, configuration ?: context.resources.configuration)
    }

    fun setLocaleResult(context: Context, language: String?, configuration: Configuration? = null): Boolean {
        val res = persist(context, language)
        if (!res) {
            return false
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            updateResources(context, language, configuration ?: context.resources.configuration)
        } else updateResourcesLegacy(context, language, configuration ?: context.resources.configuration)
        return true
    }

    private fun getPersistedData(context: Context, defaultLanguage: String): String? {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        return preferences.getString(SELECTED_LANGUAGE, defaultLanguage)
    }

    // returns true if changed
    private fun persist(context: Context, language: String?): Boolean {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val lastLocale = preferences.getString(SELECTED_LANGUAGE,  Locale.getDefault().language)
        if (lastLocale == language) {
            return false
        }
        val editor = preferences.edit()
        editor.putString(SELECTED_LANGUAGE, language)
        editor.apply()
        return true
    }

    @TargetApi(Build.VERSION_CODES.N)
    private fun updateResources(context: Context, language: String?, configuration: Configuration): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)
        return context.createConfigurationContext(configuration)
    }

    private fun updateResourcesLegacy(
        context: Context,
        language: String?,
        configuration: Configuration
    ): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val resources: Resources = context.resources
        configuration.locale = locale
        configuration.setLayoutDirection(locale)
        resources.updateConfiguration(configuration, resources.getDisplayMetrics())
        return context
    }
}