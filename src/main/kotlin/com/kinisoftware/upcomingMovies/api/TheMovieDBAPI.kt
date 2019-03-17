package com.kinisoftware.upcomingMovies.api

import com.kinisoftware.upcomingMovies.model.Upcomings
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TheMovieDBAPI {

    @GET("movie/upcoming")
    fun getUpcomings(
            @Query("api_key") apiKey: String,
            @Query("language") language: String,
            @Query("page") page: Int,
            @Query("region") region: String
    ): Call<Upcomings>
}