package io.sharan.nowhere.data.analytics

import java.util.*

class TrackingEvent {
    private var eventName: String? = null
    private var map = HashMap<String, String>()

    fun getEventName(): String {
        return eventName ?: ""
    }

    fun setEventName(eventName: String) {
        this.eventName = eventName
    }

    fun getParameters(): HashMap<String, String> {
        return map
    }

    fun setParameters(map: HashMap<String, String>) {
        this.map.putAll(map)
    }
}