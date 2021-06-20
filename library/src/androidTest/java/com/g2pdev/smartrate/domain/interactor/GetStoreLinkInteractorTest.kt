package com.g2pdev.smartrate.domain.interactor

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.g2pdev.smartrate.BaseTest
import com.g2pdev.smartrate.data.model.Store
import com.g2pdev.smartrate.data.model.StoreLink
import com.g2pdev.smartrate.domain.interactor.store.GetStoreLink
import javax.inject.Inject
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class GetStoreLinkInteractorTest : BaseTest() {

    @Inject
    lateinit var getStoreLink: GetStoreLink

    @Before
    fun setUp() {
        createDaggerComponent().inject(this)
    }

    @Test
    fun testGooglePlay() {
        val link = getStoreLink.exec(Store.GOOGLE_PLAY, PACKAGE_NAME)
        val expectedLink = StoreLink(
            link = "market://details?id=$PACKAGE_NAME",
            alternateLink = "https://play.google.com/store/apps/details?id=$PACKAGE_NAME"
        )

        Assert.assertEquals(expectedLink, link)
    }

    @Test
    fun testAmazon() {
        val link = getStoreLink.exec(Store.AMAZON, PACKAGE_NAME)
        val expectedLink = StoreLink(
            link = "amzn://apps/android?p=$PACKAGE_NAME",
            alternateLink = "https://www.amazon.com/gp/mas/dl/android?p=$PACKAGE_NAME"
        )

        Assert.assertEquals(expectedLink, link)
    }

    @Test
    fun testXiaomi() {
        val link = getStoreLink.exec(Store.XIAOMI, PACKAGE_NAME)
        val expectedLink = StoreLink(
            link = "mimarket://details?id=$PACKAGE_NAME",
            alternateLink = "https://play.google.com/store/apps/details?id=$PACKAGE_NAME"
        )

        Assert.assertEquals(expectedLink, link)
    }

    @Test
    fun testSamsung() {
        val link = getStoreLink.exec(Store.SAMSUNG, PACKAGE_NAME)
        val expectedLink = StoreLink(
            link = "https://galaxystore.samsung.com/detail/$PACKAGE_NAME",
            alternateLink = "https://galaxystore.samsung.com/detail/$PACKAGE_NAME"
        )

        Assert.assertEquals(expectedLink, link)
    }

    private companion object {
        private const val PACKAGE_NAME = "com.g2pdev.smartrate.demo"
    }
}
