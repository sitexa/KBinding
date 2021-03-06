package com.sitexa.android.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.benny.library.autoadapter.viewholder.DataGetter
import com.benny.library.kbinding.adapterview.bindings.adapter
import com.benny.library.kbinding.adapterview.bindings.itemClick
import com.benny.library.kbinding.adapterview.bindings.paging
import com.benny.library.kbinding.adapterview.converter.ListToRecyclerPagingAdapterConverter
import com.benny.library.kbinding.adapterview.viewcreator.pagingViewCreator
import com.benny.library.kbinding.adapterview.viewmodel.ItemViewModel
import com.benny.library.kbinding.annotation.Command
import com.benny.library.kbinding.annotation.DependencyProperty
import com.benny.library.kbinding.annotation.Property
import com.benny.library.kbinding.common.bindings.fadeOut
import com.benny.library.kbinding.common.bindings.until
import com.benny.library.kbinding.dsl.bind
import com.benny.library.kbinding.dsl.wait
import com.benny.library.kbinding.support.v4.bindings.refresh
import com.benny.library.kbinding.view.ViewBinderComponent
import com.sitexa.android.network.service.douban.DoubanService
import com.sitexa.android.network.service.douban.Movie
import com.sitexa.android.ui.activity.MovieDetailsActivity
import com.sitexa.android.ui.layout.item.LoadingItemView
import com.sitexa.android.ui.layout.item.MovieItemView
import com.sitexa.android.utils.generateViewId
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.swipeRefreshLayout
import org.jetbrains.anko.support.v4.toast
import kotlin.properties.Delegates

/**
 * Created by open on 04/06/2017.
 */

class MovieListFragment : BaseFragment() {
    var contentView: View? = null

    @delegate:Property
    var movies: List<Movie>? by Delegates.property()

    @Command
    fun movieDetail(params: Int) {
        startActivity<MovieDetailsActivity>("id" to movies!![params].id)
    }

    @Command
    fun reloadMovies(canExecute: (Boolean) -> Unit) {
        toast("reload movie finished")
        canExecute(true)
    }

    @Command
    fun loadMoreMovies(canExecute: (Boolean) -> Unit) {
        contentView?.postDelayed( { canExecute(true) } , 2000)
    }

    fun fetchMovies() {
        DoubanService.instance.moviesInTheaters().map { it -> it.subjects.toList()}.onErrorReturn { listOf() }.subscribe { movies = it }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if(contentView == null) {
            contentView = MovieListFragmentUI().createViewBinder(act, this).bindTo(this)
            fetchMovies()
        }
        return contentView
    }

    class MovieListFragmentUI() : ViewBinderComponent<MovieListFragment> {
        override fun builder(): AnkoContext<*>.() -> Unit = {
            relativeLayout() {
                backgroundColor = Color.WHITE
                swipeRefreshLayout {
                    bind { refresh("reloadMovies") }
                    recyclerView {
                        id = generateViewId()
                        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                        addItemDecoration(HorizontalDividerItemDecoration.Builder(ctx).color(Color.parseColor("#f2f2f2")).margin(dip(14), 0).size(1).build())
                        bind { paging("loadMoreMovies") }
                        bind { adapter("movies", converter = ListToRecyclerPagingAdapterConverter((owner as MovieListFragment).pagingViewCreator(LoadingItemView(), MovieItemView(), ::MovieViewModel))) }
                        bind { itemClick("movieDetail") }
                    }
                }
                frameLayout {
                    backgroundColor = Color.WHITE
                    themedProgressBar(android.R.attr.progressBarStyleSmall).lparams { gravity = Gravity.CENTER }
                    wait { until("movies") { fadeOut() } }
                }.lparams(matchParent, matchParent)
            }
        }
    }

    class MovieViewModel() : ItemViewModel<Movie>() {
        @delegate:Property
        var movie: Movie? by Delegates.property()

        @delegate:DependencyProperty("movie")
        val title: String? by Delegates.property { movie!!.title }

        @delegate:DependencyProperty("movie")
        val smallCover: String? by Delegates.property{ movie!!.images.small }

        @delegate:DependencyProperty("movie")
        val bigCover: String? by Delegates.property{ movie!!.images.large }

        @delegate:DependencyProperty("movie")
        val score: Float by Delegates.property { movie!!.rating.average }

        @delegate:DependencyProperty("movie")
        val casts: String? by Delegates.property { movie!!.casts.map { it -> it.name }.joinToString("/") }

        @delegate:DependencyProperty("movie")
        val genres: String? by Delegates.property { movie!!.genres.joinToString("/") }

        @delegate:DependencyProperty("movie")
        val summary: String? by Delegates.property { movie!!.summary }

        @delegate:DependencyProperty("movie")
        val ratingsCount: Int by Delegates.property { movie!!.ratingsCount }

        override fun onDataChange(getter: DataGetter<Movie?>, position: Int) {
            movie = getter.data
        }
    }
}