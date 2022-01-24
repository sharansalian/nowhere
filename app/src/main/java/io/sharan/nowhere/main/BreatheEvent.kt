package io.sharan.nowhere.main


sealed class BreatheEvent {
    data class LoadConfiguration(val list: ArrayList<String>) : BreatheEvent()
    object StartAnimation : BreatheEvent()
    object StopAnimation : BreatheEvent()
    object StartTimer : BreatheEvent()
    object StopTimer : BreatheEvent()
    object HideConfiguration : BreatheEvent()
    object ShowConfiguration : BreatheEvent()
}