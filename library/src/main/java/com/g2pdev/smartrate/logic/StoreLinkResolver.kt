package com.g2pdev.smartrate.logic

import com.g2pdev.smartrate.logic.model.Store
import com.g2pdev.smartrate.logic.model.StoreLink

internal interface StoreLinkResolver {
    fun getLink(store: Store, packageName: String): StoreLink
}

internal class StoreLinkResolverImpl : StoreLinkResolver {

    override fun getLink(store: Store, packageName: String): StoreLink {
        return when (store) {
            Store.GOOGLE_PLAY -> getGooglePlayLink(packageName)
            Store.AMAZON -> getAmazonLink(packageName)
            Store.XIAOMI -> getXiaomiLink(packageName)
            Store.SAMSUNG -> getSamsungLink(packageName)
            Store.APP_GALLERY -> getAppGalleryLink(packageName)
            //            Store.APTOIDE -> getAptoideLink(packageName) // TODO
        }
    }

    private fun getGooglePlayLink(packageName: String): StoreLink {
        return StoreLink(
            link = GOOGLE_PLAY_LINK_FORMAT.format(packageName),
            alternateLink = GOOGLE_PLAY_ALTERNATIVE_LINK_FORMAT.format(packageName)
        )
    }

    private fun getAmazonLink(packageName: String): StoreLink {
        return StoreLink(
            link = AMAZON_LINK_FORMAT.format(packageName),
            alternateLink = AMAZON_ALTERNATE_LINK_FORMAT.format(packageName)
        )
    }

    private fun getXiaomiLink(packageName: String): StoreLink {
        return StoreLink(
            link = XIAOMI_LINK_FORMAT.format(packageName),
            alternateLink = XIAOMI_ALTERNATE_LINK_FORMAT.format(packageName)
        )
    }

    private fun getSamsungLink(packageName: String): StoreLink {
        return StoreLink(
            link = SAMSUNG_LINK_FORMAT.format(packageName),
            alternateLink = SAMSUNG_ALTERNATE_LINK_FORMAT.format(packageName)
        )
    }

    private fun getAptoideLink(packageName: String): StoreLink {
        return StoreLink(
            link = SAMSUNG_LINK_FORMAT.format(packageName),
            alternateLink = SAMSUNG_ALTERNATE_LINK_FORMAT.format(packageName)
        )
    }

    private fun getAppGalleryLink(packageName: String): StoreLink {
        return StoreLink(
            link = APP_GALLERY_LINK_FORMAT.format(packageName),
            alternateLink = APP_GALLERY_ALTERNATE_LINK_FORMAT
        )
    }

    private companion object {
        private const val GOOGLE_PLAY_LINK_FORMAT = "market://details?id=%s"
        private const val GOOGLE_PLAY_ALTERNATIVE_LINK_FORMAT = "https://play.google.com/store/apps/details?id=%s"

        private const val AMAZON_LINK_FORMAT = "amzn://apps/android?p=%s"
        private const val AMAZON_ALTERNATE_LINK_FORMAT = "https://www.amazon.com/gp/mas/dl/android?p=%s"

        private const val XIAOMI_LINK_FORMAT = "mimarket://details?id=%s"
        private const val XIAOMI_ALTERNATE_LINK_FORMAT = "https://play.google.com/store/apps/details?id=%s"

        private const val SAMSUNG_LINK_FORMAT = "https://galaxystore.samsung.com/detail/%s"
        private const val SAMSUNG_ALTERNATE_LINK_FORMAT = "https://galaxystore.samsung.com/detail/%s"

        private const val APP_GALLERY_LINK_FORMAT = "appmarket://details?id=%s"
        private const val APP_GALLERY_ALTERNATE_LINK_FORMAT = "https://appgallery8.huawei.com/"
    }
}
