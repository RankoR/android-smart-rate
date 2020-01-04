package com.g2pdev.smartrate.interactor.store

import com.g2pdev.smartrate.logic.StoreLinkResolver
import com.g2pdev.smartrate.logic.model.Store
import com.g2pdev.smartrate.logic.model.StoreLink
import io.reactivex.Single

internal interface GetStoreLink {
    fun exec(store: Store, packageName: String): Single<StoreLink>
}

internal class GetStoreLinkImpl(
    private val storeLinkResolver: StoreLinkResolver
) : GetStoreLink {

    override fun exec(store: Store, packageName: String): Single<StoreLink> {
        return storeLinkResolver.getLink(store, packageName)
    }
}