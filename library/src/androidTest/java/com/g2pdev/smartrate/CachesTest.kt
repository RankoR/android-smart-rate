package com.g2pdev.smartrate

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.g2pdev.smartrate.cache.BaseCache
import com.g2pdev.smartrate.cache.IsRatedCache
import com.g2pdev.smartrate.cache.LastPromptSessionCache
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

/**
 * Test just these two caches, because other are implemented the same way
 */
@RunWith(AndroidJUnit4::class)
internal class CachesTest : BaseTest() {

    @Inject
    lateinit var isRatedCache: IsRatedCache

    @Inject
    lateinit var lastPromptSessionCache: LastPromptSessionCache

    @Before
    fun setUp() {
        createDaggerComponent().inject(this)
    }

    @Test
    fun testIsRatedCache() {
        isRatedCache
            .get()
            .test()
            .assertError(BaseCache.NoValueException::class.java)

        isRatedCache
            .get(defaultValue = false)
            .test()
            .assertValue(false)

        isRatedCache
            .put(true)
            .test()
            .assertComplete()

        isRatedCache
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