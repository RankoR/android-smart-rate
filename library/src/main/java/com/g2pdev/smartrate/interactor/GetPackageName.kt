package com.g2pdev.smartrate.interactor

import android.content.Context
import io.reactivex.Single

interface GetPackageName {
    fun exec(): Single<String>
}

internal class GetPackageNameImpl(
    private val context: Context
) : GetPackageName {

    override fun exec(): Single<String> {
        return Single.just(context.packageName)
    }
}