package com.sitexa.android.viewmodel

import com.benny.library.autoadapter.viewholder.DataGetter
import com.benny.library.kbinding.adapterview.viewmodel.ItemViewModel
import com.sitexa.android.network.service.douban.Movie

/**
 * Created by open on 04/06/2017.
 */
class MovieViewModel() : ItemViewModel<Movie>() {

    var movie: Movie? by bindProperty("movie")

    val title: String? by bindProperty("title", "movie") { movie!!.title }
    val smallCover: String? by bindProperty("smallCover", "movie") { movie!!.images.small }
    val bigCover: String? by bindProperty("bigCover", "movie") { movie!!.images.large }
    val score: Float by bindProperty("score", "movie") { movie!!.rating.average }
    val casts: String? by bindProperty("casts", "movie") { movie!!.casts.map { it -> it.name }.joinToString("/") }
    val genres: String? by bindProperty("genres", "movie") { movie!!.genres.joinToString("/") }
    val summary: String? by bindProperty("summary", "movie") { movie!!.summary }
    val ratingsCount: Int by bindProperty("ratingsCount", "movie") { movie!!.ratingsCount }

    override fun onDataChange(getter: DataGetter<Movie?>, position: Int) {
        movie = getter.data
    }
}