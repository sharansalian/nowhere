package io.sharan.nowhere.data.analytics

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.firebase.analytics.FirebaseAnalytics
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class EventLogger @Inject constructor(private val fireBaseAnalytics: FirebaseAnalytics) {

    fun trackEvents(
        fragment: Fragment,
        screenName: String
    ) {
        val params = Bundle()
        params.putString(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
        params.putString(FirebaseAnalytics.Param.SCREEN_CLASS, fragment::class.simpleName)
        fireBaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, params)
    }

    fun logCustomEvent(event: TrackingEvent) {
        val bundle = Bundle()
        event.getParameters().iterator().forEach {
            bundle.putString(it.key, it.value)
        }
        fireBaseAnalytics.logEvent(event.getEventName(), bundle)
    }

}