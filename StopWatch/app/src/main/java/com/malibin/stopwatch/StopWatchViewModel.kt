package com.malibin.stopwatch

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

/**
 * Created By Malibin
 * on 11월 25, 2020
 */

class StopWatchViewModel @ViewModelInject constructor() : ViewModel() {

    private val _displayTime = MutableLiveData(INITIAL_TIME)
    val displayTime: LiveData<String>
        get() = _displayTime

    private val _isStartVisible = MutableLiveData(true)
    val isStartVisible: LiveData<Boolean>
        get() = _isStartVisible

    private val _isStopVisible = MutableLiveData(false)
    val isStopVisible: LiveData<Boolean>
        get() = _isStopVisible

    private val runningJobs: MutableList<Job> = mutableListOf()
    private var latestTimeRecord = 0L

    fun start() {
        turnOnStopButtonAfter(DELAY_MILLIS)
        startWatch()
    }

    private fun turnOnStopButtonAfter(millis: Long) {
        val job = viewModelScope.launch {
            _isStartVisible.value = false
            delay(millis)
            if (isActive) _isStopVisible.value = true
        }
        runningJobs.add(job)
    }

    private fun startWatch() {
        val job = viewModelScope.launch {
            val startMilliseconds = System.currentTimeMillis()
            while (isActive) {
                delay(10)
                val diff = System.currentTimeMillis() - startMilliseconds
                val minutes = TimeUnit.MILLISECONDS.toMinutes(diff)
                val seconds = TimeUnit.MILLISECONDS.toSeconds(diff) % 60
                val millis = diff / 10 % 100
                val displayTime = if (minutes == 0L) "%02d.%02d".format(seconds, millis)
                else "%02d:%02d.%02d".format(minutes, seconds, millis)
                _displayTime.value = displayTime
                latestTimeRecord = diff
            }
        }
        runningJobs.add(job)
    }

    fun stop() {
        cancelAllJobs()
        turnOnStartButtonAfter(DELAY_MILLIS)
        controlCustomerRequirement()
    }

    private fun controlCustomerRequirement() {
        if (latestTimeRecord in _29_SECONDS.._31_SECONDS) {
            _displayTime.value = CUSTOMER_REQUIREMENT
        }
    }

    private fun turnOnStartButtonAfter(millis: Long) {
        val job = viewModelScope.launch {
            _isStopVisible.value = false
            delay(millis)
            if (isActive) _isStartVisible.value = true
        }
        runningJobs.add(job)
    }

    fun reset() {
        cancelAllJobs()
        _displayTime.value = INITIAL_TIME
        _isStartVisible.value = true
        _isStopVisible.value = false
    }

    private fun cancelAllJobs() {
        runningJobs.forEach { it.cancel() }
        runningJobs.clear()
    }

    companion object {
        private const val DELAY_MILLIS = 3_000L
        private const val INITIAL_TIME = "00.00"
        private const val CUSTOMER_REQUIREMENT = "31.73"
        private val _29_SECONDS = TimeUnit.SECONDS.toMillis(29)
        private val _31_SECONDS = TimeUnit.SECONDS.toMillis(31)
    }
}