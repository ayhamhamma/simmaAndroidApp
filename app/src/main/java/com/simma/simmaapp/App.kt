package com.simma.simmaapp

import android.app.Application
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import com.google.firebase.analytics.logEvent
import com.kochava.tracker.Tracker
import com.uxcam.UXCam
import com.uxcam.datamodel.UXConfig
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class App:Application() {
    override fun onCreate() {
        super.onCreate()
        // firebase analytics
        val analytics = Firebase.analytics
        analytics.logEvent(/* p0 = */ FirebaseAnalytics.Event.SELECT_CONTENT) {
            param(FirebaseAnalytics.Param.ITEM_ID, "hsa");
            param(FirebaseAnalytics.Param.ITEM_NAME, "dsajh");
            param(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
        }

        // todo get the App GUID for kochava
        Tracker.getInstance().startWithAppGuid(applicationContext, "YOUR_ANDROID_APP_GUID")
        // todo get the App Key for UXCam
        val config = UXConfig.Builder("yourAppKey")
            .build()
        UXCam.startWithConfiguration(config);

    }
}