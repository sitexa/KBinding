package com.sitexa.android.ui.extension

import android.content.Context
import android.view.View
import android.view.ViewManager
import com.facebook.drawee.view.SimpleDraweeView
import com.sitexa.android.ui.widget.ViewPagerIndicator
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.custom.ankoView

/**
 * Created by open on 04/06/2017.
 */

var View.backgroundColorResource: Int
    get() = throw UnsupportedOperationException()
    set(value) { this.backgroundColor = context.resources.getColor(value) }

fun ViewManager.simpleDraweeView(theme: Int = 0, init: SimpleDraweeView.() -> Unit): SimpleDraweeView {
    return ankoView({ctx: Context -> SimpleDraweeView(ctx) }, theme) { init() }
}

fun ViewManager.viewPagerIndicator(theme: Int = 0, init: ViewPagerIndicator.() -> Unit): ViewPagerIndicator {
    return ankoView({ctx: Context -> ViewPagerIndicator(ctx) }, theme) { init() }
}
