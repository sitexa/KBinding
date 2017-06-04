package com.sitexa.android.ui.activity

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import com.ToxicBakery.viewpager.transforms.ZoomOutSlideTransformer
import com.benny.library.autoadapter.IPagerAdapterItemAccessor
import com.benny.library.autoadapter.SimpleAdapterItemAccessor
import com.benny.library.kbinding.adapterview.bindings.fragmentAdapter
import com.benny.library.kbinding.adapterview.converter.ListToFragmentPagerAdapterConverter
import com.benny.library.kbinding.annotation.Property
import com.benny.library.kbinding.dsl.bind
import com.benny.library.kbinding.dsl.inflate
import com.benny.library.kbinding.view.ViewBinderComponent
import com.benny.library.kbinding.view.setContentView
import com.sitexa.android.R
import com.sitexa.android.ui.extension.viewPagerIndicator
import com.sitexa.android.ui.fragment.LoginFragment
import com.sitexa.android.ui.fragment.MovieListFragment
import com.sitexa.android.ui.fragment.SampleFragment
import com.sitexa.android.ui.fragment.StockFragment
import com.sitexa.android.ui.layout.TitleToolBarView
import com.sitexa.android.ui.widget.ViewPagerIndicator
import com.sitexa.android.utils.generateViewId
import org.jetbrains.anko.*
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.support.v4.viewPager
import kotlin.properties.Delegates

/**
 * Created by open on 04/06/2017.
 */

class MainActivity : BaseActivity() {
    lateinit var toolBar: Toolbar

    @delegate:Property
    val fragments: List<Fragment> by Delegates.property(listOf(LoginFragment(), MovieListFragment(), StockFragment(), SampleFragment()))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("MainActivity", "onCreate this:" + this)

        setTheme(R.style.AppTheme)
        MainActivityUI().setContentView(this).bindTo(this)

        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);
        supportActionBar?.title = "";
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    inner class PagerAdapterAccessor(list: List<Fragment>) : SimpleAdapterItemAccessor<Fragment>(list), IPagerAdapterItemAccessor<Fragment> {
        override fun getTitle(position: Int): String = when(position) {
            0 -> ctx.resources.getString(R.string.log_in)
            1 -> ctx.resources.getString(R.string.douban)
            2 -> ctx.resources.getString(R.string.stock)
            else -> ctx.resources.getString(R.string.reserved)
        }
    }

    inner class MainActivityUI : ViewBinderComponent<MainActivity> {
        override fun builder(): AnkoContext< MainActivity>.() -> Unit = {
            verticalLayout {
                appBarLayout {
                    toolBar = inflate(TitleToolBarView(ctx.resources.getString(R.string.home)), this@appBarLayout) as Toolbar
                }
                val pager = viewPager {
                    id = generateViewId()
                    clipToPadding = false
                    setPadding(40, 0 , 40, 0)
                    pageMargin = 20
                    setPageTransformer(false, ZoomOutSlideTransformer())
                    bind { fragmentAdapter("fragments", converter = ListToFragmentPagerAdapterConverter((owner).supportFragmentManager, { PagerAdapterAccessor(it) })) }
                }.lparams(matchParent, 0, 1f)

                viewPagerIndicator {
                    backgroundColor = Color.parseColor("#393a4c")
                    indicatorLinePosition = ViewPagerIndicator.TOP
                    indicatorLineMargin = dip(5)
                    indicatorLineHeight = dip(4)
                    viewPager = pager
                }.lparams(matchParent, wrapContent)
            }
        }
    }
}
