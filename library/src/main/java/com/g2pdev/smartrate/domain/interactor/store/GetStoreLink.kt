package com.g2pdev.smartrate.domain.interactor.store

import com.g2pdev.smartrate.data.model.Store
import com.g2pdev.smartrate.data.model.StoreLink
import com.g2pdev.smartrate.domain.StoreLinkResolver

internal interface GetStoreLink {
    fun exec(store: Store, packageName: String): StoreLink
}

internal class GetStoreLinkImpl(
    private val storeLinkResolver: StoreLinkResolver
) : GetStoreLink {

    override fun exec(store: Store, packageName: String): StoreLink {
        return storeLinkResolver.getLink(store, packageName)
    }
}
