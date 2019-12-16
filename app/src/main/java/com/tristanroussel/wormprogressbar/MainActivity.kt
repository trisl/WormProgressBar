package com.tristanroussel.wormprogressbar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        wormProgressBar = worm

        wormProgressBar.initAnimation()
    }

    override fun onResume() {
        super.onResume()

        wormProgressBar.start()
    }

    override fun onPause() {
        super.onPause()

        wormProgressBar.pause()
    }
}
