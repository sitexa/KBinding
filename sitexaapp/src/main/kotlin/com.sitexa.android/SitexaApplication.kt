package com.sitexa.android

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco

/**
 * Created by open on 04/06/2017.
 *
 */
class SitexaApplication:Application (){

    override fun onCreate() {
        super.onCreate()

        ApplicationContext.init(this)
        Fresco.initialize(this)
    }
}