package com.sitexa.android.utils

import android.os.Build
import android.view.View
import com.google.gson.GsonBuilder
import com.sitexa.android.Constants
import java.util.concurrent.atomic.AtomicInteger

/**
 * Created by open on 04/06/2017.
 */
fun toJson(`object`: Any): String {
    return GsonBuilder().setDateFormat(Constants.DATE_FORMAT).create().toJson(`object`)
}

fun <T> fromJson(`object`: String, clazz: Class<T>): T? {
    return if(`object`.isNullOrEmpty()) null else GsonBuilder().setDateFormat(Constants.DATE_FORMAT).create().fromJson(`object`, clazz)
}

val sNextGeneratedId: AtomicInteger = AtomicInteger(1);
fun generateViewId(): Int {
    if (Build.VERSION.SDK_INT < 17) {
        while (true) {
            val result: Int = sNextGeneratedId.get();
            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
            var newValue: Int = result + 1;
            if (newValue > 0x00FFFFFF)
                newValue = 1; // Roll over to 1, not 0.
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result;
            }
        }
    } else {
        return View.generateViewId();
    }
}
