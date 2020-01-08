package com.g2pdev.smartrate.interactor

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.g2pdev.smartrate.BaseTest
import com.g2pdev.smartrate.interactor.store.GetStoreLink
import com.g2pdev.smartrate.logic.model.Store
import com.g2pdev.smartrate.logic.model.StoreLink
import javax.inject.Inject
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class GetStoreLinkInteractorTest : BaseTest() {

    @Inject
    lateinit var getStoreLink: GetStoreLink

    @Before
    fun setUp() {
        createDaggerComponent()
            .inject(this)
    }

    @Test
    fun testGooglePlay() {
        getStoreLink
            .exec(Store.GOOGLE_PLAY, packageName)
            .test()
            .assertValue(
                StoreLink(
                    link = "market://details?id=$packageName",
                    alternateLink = "https://play.google.com/store/apps/details?id=$packageName"
                )
            )
    }

    @Test
    fun testAmazon() {
        getStoreLink
            .exec(Store.AMAZON, packageName)
            .test()
            .assertValue(
                StoreLink(
                    link = "amzn://apps/android?p=$packageName",
                    alternateLink = "https://www.amazon.com/gp/mas/dl/android?p=$packageName"
                )
            )
    }

    @Test
    fun testXiaomi() {
        getStoreLink
            .exec(Store.XIAOMI, packageName)
            .test()
            .assertValue(
                StoreLink(
                    link = "market://details?id=$packageName",
                    alternateLink = "https://play.google.com/store/apps/details?id=$packageName"
                )
            )
    }

    @Test
    fun testSamsung() {
        getStoreLink
            .exec(Store.SAMSUNG, packageName)
            .test()
            .assertValue(
                StoreLink(
                    link = "https://galaxystore.samsung.com/detail/$packageName",
                    alternateLink = "https://galaxystore.samsung.com/detail/$packageName"
                )
            )
    }

    private companion object {
        private const val packageName = "com.g2pdev.smartrate.demo"
    }
}
