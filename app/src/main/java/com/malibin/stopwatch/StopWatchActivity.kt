package com.malibin.stopwatch

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.malibin.stopwatch.databinding.ActivityStopWatchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StopWatchActivity : AppCompatActivity() {

    private val stopWatchViewModel: StopWatchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {
        val binding = ActivityStopWatchBinding.inflate(layoutInflater)
        binding.viewModel = stopWatchViewModel
        binding.lifecycleOwner = this
        setContentView(binding.root)
    }
}