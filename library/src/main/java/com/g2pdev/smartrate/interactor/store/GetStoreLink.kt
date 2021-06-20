package com.g2pdev.smartrate.interactor.store

import com.g2pdev.smartrate.logic.StoreLinkResolver
import com.g2pdev.smartrate.logic.model.Store
import com.g2pdev.smartrate.logic.model.StoreLink

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
