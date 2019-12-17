package com.tristanroussel.wormprogressbar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tristanroussel.worm.worm.WormProgressBar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var wormProgressBar: WormProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        wormProgressBar = worm_view

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
