package com.sitexa.android.network.service.douban

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import rx.Observable

/**
 * Created by open on 04/06/2017.
 */
interface IDoubanService {

    @GET(API_VERSION + "/movie/{type}")
    fun movies(@Path("type") type: String, @Query("apikey") key: String): Observable<Category>

    @GET(API_VERSION + "/movie/subject/{id}")
    fun movie(@Path("id") id: String, @Query("apikey") key: String): Observable<Movie>

    companion object {
        const val API_VERSION = "/v2"
    }
}
