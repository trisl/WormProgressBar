package com.tristanroussel.wormprogressbar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tristanroussel.wormprogressbar.worm.WormProgressBar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var wormProgressBar: WormProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        wormProgressBar = worm
    }

    override fun onStart() {
        super.onStart()

        val config =
                WormProgressBar
                        .AnimationConfigurationBuilder()
                        .apply {
                            duration = 500L
                        }
                        .build()

        wormProgressBar.start(config)
    }
}
