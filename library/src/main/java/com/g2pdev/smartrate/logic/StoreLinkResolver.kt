package com.g2pdev.smartrate.logic

import com.g2pdev.smartrate.logic.model.Store
import com.g2pdev.smartrate.logic.model.StoreLink
import io.reactivex.Single

internal interface StoreLinkResolver {
    fun getLink(store: Store, packageName: String): Single<StoreLink>
}

internal class StoreLinkResolverImpl: StoreLinkResolver {

    override fun getLink(store: Store, packageName: String): Single<StoreLink> {
        return when (store) {
            Store.GOOGLE_PLAY -> getGooglePlayLink(packageName)
            Store.AMAZON -> getAmazonLink(packageName)
            Store.XIAOMI -> getXiaomiLink(packageName)
            Store.SAMSUNG -> getSamsungLink(packageName)
//            Store.APTOIDE -> getAptoideLink(packageName)
        }
    }

    private fun getGooglePlayLink(packageName: String): Single<StoreLink> {
        return Single.just(
            StoreLink(
                link = googlePlayLinkFormat.format(packageName),
                alternateLink = googlePlayAlternateLinkFormat.format(packageName)
            )
        )
    }

    private fun getAmazonLink(packageName: String): Single<StoreLink> {
        return Single.just(
            StoreLink(
                link = amazonLinkFormat.format(packageName),
                alternateLink = amazonAlternateLinkFormat.format(packageName)
            )
        )
    }

    private fun getXiaomiLink(packageName: String): Single<StoreLink> {
        return Single.just(
            StoreLink(
                link = xiaomiLinkFormat.format(packageName),
                alternateLink = xiaomiAlternateLinkFormat.format(packageName)
            )
        )
    }

    private fun getSamsungLink(packageName: String): Single<StoreLink> {
        return Single.just(
            StoreLink(
                link = samsungLinkFormat.format(packageName),
                alternateLink = samsungAlternateLinkFormat.format(packageName)
            )
        )
    }

    private fun getAptoideLink(packageName: String): Single<StoreLink> {
        return Single.just(
            StoreLink(
                link = samsungLinkFormat.format(packageName),
                alternateLink = xiaomiAlternateLinkFormat.format(packageName)
            )
        )
    }


    private companion object {
        private const val googlePlayLinkFormat = "market://details?id=%s"
        private const val googlePlayAlternateLinkFormat = "https://play.google.com/store/apps/details?id=%s"

        private const val amazonLinkFormat = "amzn://apps/android?p=%s"
        private const val amazonAlternateLinkFormat = "https://www.amazon.com/gp/mas/dl/android?p=%s"

        // TODO: Validate these links
        private const val xiaomiLinkFormat = "market://details?id=%s"
        private const val xiaomiAlternateLinkFormat = "https://play.google.com/store/apps/details?id=%s"

        private const val samsungLinkFormat = "https://galaxystore.samsung.com/detail/%s"
        private const val samsungAlternateLinkFormat = "https://galaxystore.samsung.com/detail/%s"
    }

}