package io.sharan.nowhere.main

import androidx.annotation.Keep
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.sharan.nowhere.data.Config
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@Keep
@HiltViewModel
class BreatheViewModel @Inject constructor() : ViewModel() {

    fun start() = viewModelScope.launch {
        // Create list for times.
        val list = ArrayList<String>()
        list.add("30 times")
        list.add("60 times")
        list.add("120 times")

        // Load configuration values.
        eventChannel.send(BreatheEvent.LoadConfiguration(list))
    }

    fun startBreathing() = viewModelScope.launch {
        eventChannel.send(BreatheEvent.HideConfiguration)
        eventChannel.send(BreatheEvent.StartAnimation)
        eventChannel.send(BreatheEvent.StartTimer)
    }

    fun stopBreathing() = viewModelScope.launch {
        eventChannel.send(BreatheEvent.StopTimer)
        eventChannel.send(BreatheEvent.StopAnimation)
        eventChannel.send(BreatheEvent.ShowConfiguration)
    }

    private val eventChannel = Channel<BreatheEvent>()
    val eventFlow = eventChannel.receiveAsFlow()

}