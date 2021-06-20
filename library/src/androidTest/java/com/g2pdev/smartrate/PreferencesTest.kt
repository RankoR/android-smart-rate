package com.g2pdev.smartrate

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.g2pdev.smartrate.data.preference.IsRatedPreference
import com.g2pdev.smartrate.data.preference.LastPromptSessionPreference
import javax.inject.Inject
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Test just these two caches, because other are implemented the same way
 */
@RunWith(AndroidJUnit4::class)
internal class PreferencesTest : BaseTest() {

    @Inject
    lateinit var isRatedPreference: IsRatedPreference

    @Inject
    lateinit var lastPromptSessionPreference: LastPromptSessionPreference

    @Before
    fun setUp() {
        createDaggerComponent().inject(this)
    }

    @Test
    fun testIsRatedCache() {
        Assert.assertNull(isRatedPreference.get())

        isRatedPreference.put(true)

        Assert.assertTrue(isRatedPreference.get() ?: false)
    }

    @Test
    fun testLastPromptSessionCache() {
        Assert.assertNull(lastPromptSessionPreference.get())

        lastPromptSessionPreference.put(100500)

        Assert.assertEquals(100500, lastPromptSessionPreference.get())
    }
}
