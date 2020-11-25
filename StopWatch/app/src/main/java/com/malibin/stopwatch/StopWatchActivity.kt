package com.malibin.stopwatch

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.malibin.stopwatch.databinding.ActivityStopWatchBinding
import com.malibin.stopwatch.databinding.ActivityStopWatchLandBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StopWatchActivity : AppCompatActivity() {

    private val stopWatchViewModel: StopWatchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) intLandScapeView()
        else initPortraitView()
    }

    private fun initPortraitView() {
        val binding = ActivityStopWatchBinding.inflate(layoutInflater)
        binding.viewModel = stopWatchViewModel
        binding.lifecycleOwner = this
        setContentView(binding.root)
    }

    private fun intLandScapeView() {
        val binding = ActivityStopWatchLandBinding.inflate(layoutInflater)
        binding.viewModel = stopWatchViewModel
        binding.lifecycleOwner = this
        setContentView(binding.root)
    }
}