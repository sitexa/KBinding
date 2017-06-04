package com.sitexa.android.viewmodel

import com.benny.library.autoadapter.viewholder.DataGetter
import com.benny.library.kbinding.adapterview.viewmodel.ItemViewModel
import com.benny.library.kbinding.annotation.ExtractProperty
import com.sitexa.android.network.service.caishuo.Stock
import kotlin.properties.Delegates

/**
 * Created by open on 04/06/2017.
 */
class StockViewModel() : ItemViewModel<Stock>() {

    @delegate:ExtractProperty(
            "name", "symbol", "realtimePrice", "changePercent", "changePrice", "listedState", "market",
            hasPrefix = false
    )

    var stock: Stock? by Delegates.property()

    override fun onDataChange(getter: DataGetter<Stock?>, position: Int) {
        stock = getter.data
    }
}