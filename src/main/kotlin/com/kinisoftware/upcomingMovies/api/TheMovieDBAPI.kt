package com.kinisoftware.upcomingMovies.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TheMovieDBAPI {

    @GET("movie/now_playing")
    fun getNowPlaying(
            @Query("api_key") apiKey: String,
            @Query("language") language: String,
            @Query("page") page: Int,
            @Query("region") region: String
    ): Call<ApiResults>

    @GET("movie/upcoming")
    fun getUpcomings(
            @Query("api_key") apiKey: String,
            @Query("language") language: String,
            @Query("page") page: Int,
            @Query("region") region: String
    ): Call<ApiResults>
}