package com.sitexa.android.ui.activity

import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Gravity
import com.benny.library.autoadapter.viewholder.DataGetter
import com.benny.library.kbinding.common.bindings.fadeOut
import com.benny.library.kbinding.common.bindings.until
import com.benny.library.kbinding.dsl.inflate
import com.benny.library.kbinding.dsl.wait
import com.benny.library.kbinding.view.ViewBinderComponent
import com.benny.library.kbinding.view.setContentView
import com.sitexa.android.network.service.caishuo.CaishuoService
import com.sitexa.android.ui.layout.stock.StockInfoUI
import com.sitexa.android.viewmodel.StockViewModel
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar

/**
 * Created by open on 04/06/2017.
 */

class StockDetailsActivity : BaseActivity() {
    lateinit var toolBar: Toolbar
    val stockViewModel: StockViewModel = StockViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StockDetailsActivityUI().setContentView(this).bindTo(stockViewModel)

        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.title = ""

        CaishuoService.instance.stock(intent.getStringExtra("id"))
                .subscribe({
                    stockViewModel.onDataChange(DataGetter(null, it, null), 0)
                }, {
                    Log.e("StockDetailsActivity", "error : $it")
                })
    }


    class StockDetailsActivityUI : ViewBinderComponent<StockDetailsActivity> {
        override fun builder(): AnkoContext< StockDetailsActivity>.() -> Unit = {
            verticalLayout {
                (owner).toolBar = toolbar {
                    backgroundColor = Color.parseColor("#393a4c")
                }
                frameLayout {
                    relativeLayout {
                        wait { until("stock") { inflate(StockInfoUI(), this@relativeLayout).lparams(matchParent) } }
                    }
                    frameLayout {
                        backgroundColor = Color.WHITE
                        themedProgressBar(android.R.attr.progressBarStyleSmall).lparams { gravity = Gravity.CENTER }
                        wait { until("stock") { fadeOut() } }
                    }
                }.lparams(matchParent, matchParent)
            }
        }
    }
}