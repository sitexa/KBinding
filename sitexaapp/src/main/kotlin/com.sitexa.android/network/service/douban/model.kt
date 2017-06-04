package com.sitexa.android.network.service.douban

import com.google.gson.annotations.SerializedName

/**
 * Created by open on 04/06/2017.
 */

data class Avatars(var small: String,
                   var large: String,
                   var medium: String)

data class Cast(var id: String,
                var name: String,
                var avatars: Avatars,
                var alt: String)

data class Category(
        var count: Int = 0,
        var start: Int = 0,
        var total: Int = 0,
        var title: String,
        var subjects: Array<Movie>
)

data class Movie(
        var id: String,
        var title: String,

        var rating: Rating,

        @SerializedName("original_title")
        var originalTitle: String,

        var subtype: String,
        var summary: String,

        @SerializedName("reviews_count")
        var reviewsCount: Int = 0,

        @SerializedName("wish_count")
        var wishCount: Int = 0,

        @SerializedName("comments_count")
        var commentsCount: Int = 0,

        @SerializedName("ratings_count")
        var ratingsCount: Int = 0,

        @SerializedName("douban_site")
        var doubanSite: String,

        var year: String,
        var images: Avatars,
        var alt: String,

        @SerializedName("mobile_url")
        var mobileUrl: String,

        @SerializedName("share_url")
        var shareUrl: String,

        @SerializedName("schedule_url")
        var scheduleUrl: String,

        var genres: Array<String>,

        var countries: Array<String>,

        var casts: Array<Cast>,

        var directors: Array<Cast>,

        var aka: Array<String>
)

class Rating(
        var max: Int = 0,
        var min: Int = 0,
        var average: Float = 0.toFloat(),
        var starts: String
)