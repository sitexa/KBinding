package com.sitexa.android.ui.layout.item

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.view.Gravity
import com.benny.library.kbinding.adapterview.viewcreator.ItemViewBinderComponent
import com.benny.library.kbinding.common.bindings.background
import com.benny.library.kbinding.common.bindings.text
import com.benny.library.kbinding.common.bindings.textColorResource
import com.benny.library.kbinding.common.bindings.textWeight
import com.benny.library.kbinding.common.roundRect
import com.benny.library.kbinding.converter.OneWayConverter
import com.benny.library.kbinding.dsl.OneWay
import com.benny.library.kbinding.dsl.bind
import com.benny.library.kbinding.dsl.resolveAttribute
import com.sitexa.android.R
import com.sitexa.android.converter.StockColorConverter
import com.sitexa.android.converter.StockPriceChangePercentageConverter
import com.sitexa.android.converter.StockPriceConverter
import com.sitexa.android.network.service.caishuo.Stock
import com.sitexa.android.utils.generateViewId
import org.jetbrains.anko.*

/**
 * Created by open on 04/06/2017.
 */

class StockItemView : ItemViewBinderComponent {
    class ChangePercentBackgroundConverter(val context: Context) : OneWayConverter<Array<Any?>, Drawable> {
        override fun convert(params: Array<Any?>): Drawable {
            if(params.size < 2) return roundRect { radius = context.dip(2).toFloat(); color = context.resources.getColor(R.color.color_9) }
            val listedState = params[0] as? Int ?: Stock.LISTED_STATE_ABNORMAL
            val changePercent = params[1] as? Float ?: 0f

            if(listedState == Stock.LISTED_STATE_NORMAL) {
                return roundRect { radius = context.dip(2).toFloat(); color = StockColorConverter(context.resources.getColor(R.color.color_9)).convert(changePercent) }
            }

            return roundRect { radius = context.dip(2).toFloat(); color = context.resources.getColor(R.color.color_9) }
        }
    }

    class ChangePercentConverter : OneWayConverter<Array<Any?>, CharSequence> {
        override fun convert(params: Array<Any?>): CharSequence {
            if(params.size < 2) return "+0.00%"
            val listedState = params[0] as? Int ?: Stock.LISTED_STATE_ABNORMAL
            val changePercent = params[1] as? Float ?: 0f

            if(listedState == Stock.LISTED_STATE_NORMAL) return StockPriceChangePercentageConverter().convert(changePercent)

            return Stock.listedStateDescription(listedState)
        }
    }

    override fun builder(): AnkoContext<*>.() -> Unit = {
        relativeLayout {
            backgroundResource = resolveAttribute(android.R.attr.selectableItemBackground)
            verticalLayout {
                verticalPadding = dip(7)
                textView {
                    textSize = 16f
                    textColorResource = R.color.color_3
                    singleLine = true
                    maxEms = 8
                    ellipsize = TextUtils.TruncateAt.END
                    bind { text("name", mode = OneWay) }
                }.lparams(width = wrapContent)
                textView {
                    textSize = 9f
                    textColorResource = R.color.color_9
                    bind { text("symbol", mode = OneWay) }
                }.lparams(width = wrapContent)
            }.lparams { leftMargin = dip(14) }
            val v = textView {
                id = generateViewId()
                textSize = 16f
                textColor = Color.WHITE
                textWeight = Typeface.BOLD
                this@textView.gravity = Gravity.CENTER
                bind { background("listedState", "changePercent", converter = ChangePercentBackgroundConverter(ctx)) }
                bind { text("listedState", "changePercent", converter = ChangePercentConverter()) }
            }.lparams(width = dip(82), height = dip(33)) {
                alignParentRight()
                centerVertically()
                rightMargin = dip(14)
            }
            textView {
                textSize = 16f
                textColorResource = R.color.color_3
                this@textView.gravity = Gravity.CENTER_VERTICAL or Gravity.RIGHT
                bind { text("realtimePrice", mode = OneWay, converter = StockPriceConverter()) }
            }.lparams {
                leftOf(v)
                centerVertically()
                rightMargin = dip(14)
            }
        }
    }
}
