package com.g2pdev.smartrate.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.g2pdev.smartrate.SmartRate
import com.g2pdev.smartrate.logic.model.SmartRateConfig

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val config = SmartRateConfig()
            .apply {
                customStoreLink = "https://android-school.ru"
            }

        SmartRate.show(this, config)
    }
}
