package io.sharan.nowhere.main

import io.sharan.nowhere.BasePresenter
import io.sharan.nowhere.BaseView
import io.sharan.nowhere.data.Config


interface BreatheContract {

    interface View : BaseView<Presenter> {
        fun loadConfiguration(list: ArrayList<String>);
        fun startAnimation()
        fun stopAnimation()
        fun startTimer();
        fun stopTimer();
        fun hideConfiguration()
        fun showConfiguration()
    }

    interface Presenter : BasePresenter {
        fun startBreathing(config: Config)
        fun stopBreathing()
    }

}