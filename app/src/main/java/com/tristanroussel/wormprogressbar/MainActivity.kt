package com.tristanroussel.wormprogressbar

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.tristanroussel.worm.WormProgressBar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    
    private lateinit var startButton: View
    private lateinit var pauseButton: View
    private lateinit var completeButton: View
    private lateinit var resetButton: View

    private lateinit var wormProgressBar: WormProgressBar
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        startButton = button_start
        pauseButton = button_pause
        completeButton = button_complete
        resetButton = button_reset

        wormProgressBar = worm_view
        
        wormProgressBar.initAnimation()
    }
    
    override fun onStart() {
        super.onStart()
        
        startButton.setOnClickListener { wormProgressBar.start() }
        pauseButton.setOnClickListener { wormProgressBar.pause() }
        completeButton.setOnClickListener { wormProgressBar.complete() }
        resetButton.setOnClickListener { wormProgressBar.reset() }
    }
}
