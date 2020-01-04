package com.g2pdev.smartrate.di

import android.content.Context
import com.g2pdev.smartrate.di.RateModule
import dagger.Module

@Module
internal class TestRateModule(
    context: Context
) : RateModule(context) {
}