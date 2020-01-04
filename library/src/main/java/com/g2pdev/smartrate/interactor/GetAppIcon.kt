package com.g2pdev.smartrate.interactor

import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import io.reactivex.Single

internal interface GetAppIcon {
    fun exec(): Single<Drawable>
}

internal class GetAppIconImpl(
    private val packageManager: PackageManager,
    private val getPackageName: GetPackageName
) : GetAppIcon {

    override fun exec(): Single<Drawable> {
        return getPackageName
            .exec()
            .map(packageManager::getApplicationIcon)
    }
}