package com.g2pdev.smartrate.demo.cache

import android.content.Context
import androidx.core.content.edit
import com.google.gson.Gson
import io.reactivex.Completable
import io.reactivex.Single

abstract class BaseCache<T>(
    private val gson: Gson,
    context: Context,
    private val name: String
) {

    private val sharedPreferences by lazy { context.getSharedPreferences(name, Context.MODE_PRIVATE) }

    protected abstract fun getType(): Class<T>

    fun getSync(defaultValue: T? = null): T? {
        val json = sharedPreferences.getString(name, defaultStringValue)

        if (json == defaultStringValue) {
            defaultValue?.let {
                return defaultValue
            }

            return null
        }

        return gson.fromJson(json, getType())
    }

    fun get(defaultValue: T? = null): Single<T> {
        return Single.fromCallable {
            val value = getSync(defaultValue) ?: throw NoValueException()

            value
        }
    }

    fun putSync(obj: T?) {
        val json = gson.toJson(obj)

        sharedPreferences.edit { putString(name, json) }
    }

    fun put(obj: T?): Completable {
        return Completable.fromCallable {
            putSync(obj)
        }
    }

    class NoValueException : Exception()

    private companion object {
        private const val defaultStringValue = ""
    }

}