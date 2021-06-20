package com.g2pdev.smartrate

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.g2pdev.smartrate.cache.IsRatedPreference
import com.g2pdev.smartrate.cache.LastPromptSessionCache
import javax.inject.Inject
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Test just these two caches, because other are implemented the same way
 */
@RunWith(AndroidJUnit4::class)
internal class CachesTest : BaseTest() {

    @Inject
    lateinit var isRatedPreference: IsRatedPreference

    @Inject
    lateinit var lastPromptSessionCache: LastPromptSessionCache

    @Before
    fun setUp() {
        createDaggerComponent()
            .inject(this)
    }

    @Test
    fun testIsRatedCache() {
        isRatedPreference
            .get()
            .test()
            .assertError(BaseCache.NoValueException::class.java)

        isRatedPreference
            .get(defaultValue = false)
            .test()
            .assertValue(false)

        isRatedPreference
            .put(true)
            .test()
            .assertComplete()

        isRatedPreference
            .get()
            .test()
            .assertValue(true)
    }

    @Test
    fun testLastPromptSessionCache() {
        lastPromptSessionCache
            .get()
            .test()
            .assertError(BaseCache.NoValueException::class.java)

        lastPromptSessionCache
            .get(defaultValue = 0)
            .test()
            .assertValue(0)

        lastPromptSessionCache
            .put(100500)
            .test()
            .assertComplete()

        lastPromptSessionCache
            .get()
            .test()
            .assertValue(100500)
    }
}
