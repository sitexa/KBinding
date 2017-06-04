package com.sitexa.android.ui.layout.item

import android.view.Gravity
import com.benny.library.kbinding.adapterview.viewcreator.ItemViewBinderComponent
import org.jetbrains.anko.*

/**
 * Created by open on 04/06/2017.
 */
class LoadingItemView : ItemViewBinderComponent {
    override fun builder(): AnkoContext<*>.() -> Unit = {
        relativeLayout {
            frameLayout {
                themedProgressBar (android.R.attr.progressBarStyleSmall) {
                    isIndeterminate = true
                }.lparams(width = dip(24), height = dip(24)) { gravity = Gravity.CENTER }
            }.lparams(matchParent, wrapContent)
        }
    }
}