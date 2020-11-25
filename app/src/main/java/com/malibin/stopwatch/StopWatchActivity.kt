package com.malibin.stopwatch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.malibin.stopwatch.databinding.ActivityStopWatchBinding

class StopWatchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityStopWatchBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}