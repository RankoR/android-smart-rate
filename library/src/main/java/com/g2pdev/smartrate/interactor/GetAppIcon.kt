package com.g2pdev.smartrate.interactor

import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal interface GetAppIcon {
    suspend fun exec(): Drawable
}

internal class GetAppIconImpl(
    private val packageManager: PackageManager,
    private val getPackageName: GetPackageName
) : GetAppIcon {

    override suspend fun exec(): Drawable {
        return withContext(Dispatchers.IO) {
            getPackageName
                .exec()
                .let(packageManager::getApplicationIcon)
        }
    }
}
