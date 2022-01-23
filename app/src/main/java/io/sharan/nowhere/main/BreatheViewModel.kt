package io.sharan.nowhere.main

import androidx.annotation.Keep
import androidx.lifecycle.ViewModel
import io.sharan.nowhere.data.Config

@Keep
class BreatheViewModel(private val view: BreatheContract.View) : ViewModel(), BreatheContract.Presenter {

    init {
        view.setPresenter(this)
    }

    override fun start() {
        // Create list for times.
        val list = ArrayList<String>()
        list.add("30 times")
        list.add("60 times")
        list.add("120 times")

        // Load configuration values.
        view.loadConfiguration(list)
    }

    override fun startBreathing(config: Config) {
        // Hide configuration.
        view.hideConfiguration()
        // Start animation.
        view.startAnimation()
        // Start timer.
        view.startTimer()
    }

    override fun stopBreathing() {
        // Stop timer.
        view.stopTimer()
        // Stop animation.
        view.stopAnimation()
    }

}