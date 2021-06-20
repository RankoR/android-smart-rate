package com.g2pdev.smartrate.interactor

import android.content.Context

interface GetPackageName {
    fun exec(): String
}

internal class GetPackageNameImpl(
    private val context: Context
) : GetPackageName {

    override fun exec(): String {
        return context.packageName
    }
}
