package com.kinisoftware.upcomingMovies.api

import com.kinisoftware.upcomingMovies.model.Configuration
import com.kinisoftware.upcomingMovies.model.Movie
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDBAPI {

    @GET("configuration")
    fun getConfiguration(
            @Query("api_key") apiKey: String
    ): Call<Configuration>

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