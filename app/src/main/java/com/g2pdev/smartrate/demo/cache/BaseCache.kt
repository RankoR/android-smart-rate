package com.g2pdev.smartrate.demo.cache

import android.content.Context
import androidx.core.content.edit
import com.google.gson.Gson
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Base cache class, uses SharedPreferences to store values.
 * Values are stored as JSON strings (serialized by GSON)
 */
abstract class BaseCache<T>(
    private val gson: Gson,
    context: Context,
    private val name: String
) {

    private val sharedPreferences by lazy {
        context.getSharedPreferences(
            name,
            Context.MODE_PRIVATE
        )
    }

    protected abstract fun getType(): Class<T>

    /**
     * Get value from cache synchronously
     *
     * @param defaultValue Value to use if nothing is stored yet
     * @return Stored value, default value if nothing stored, null if stored null or default value was not specified
     */
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

    /**
     * Get value from cache asynchronously
     *
     * @param defaultValue Value to use if nothing is stored yet
     *
     * @return [Single<T>] with stored or default value, or [Single[NoValueException]] if got null
     */
    fun get(defaultValue: T? = null): Single<T> {
        return Single.fromCallable {
            val value = getSync(defaultValue) ?: throw NoValueException()

            value
        }
    }

    /**
     * Put given value to cache synchronously
     * @param obj Value to store, may be null
     */
    fun putSync(obj: T?) {
        val json = gson.toJson(obj)

        sharedPreferences.edit { putString(name, json) }
    }

    /**
     * Put given value to cache asynchronously
     * @param obj Value to store, may be null
     * @return [Completable]
     */
    fun put(obj: T?): Completable {
        return Completable.fromCallable {
            putSync(obj)
        }
    }

    /**
     * Thrown if null stored while getting value asynchronously
     */
    class NoValueException : Exception()

    private companion object {
        private const val defaultStringValue = ""
    }
}
