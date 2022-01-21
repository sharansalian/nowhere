package io.sharan.nowhere.data.analytics

import com.google.firebase.analytics.FirebaseAnalytics
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventsCreator @Inject constructor() {

    object Screen {
        const val HOME_SCREEN = "HOME_SCREEN"
    }

    companion object {
        const val SPINNER_CLICK_EVENT = "spinner_click_event"
        const val HALE_CLICK_EVENT = "hale_event"
        const val HALE_FINISH_EVENT = "hale_finish_event"
    }

    fun getCustomEvent(eventName: String, screenName: String): TrackingEvent {
        val event = TrackingEvent()
        event.setEventName(eventName)
        val map = HashMap<String, String>()
        map[FirebaseAnalytics.Param.SCREEN_NAME] = screenName
        event.setParameters(map)
        return event
    }

}
