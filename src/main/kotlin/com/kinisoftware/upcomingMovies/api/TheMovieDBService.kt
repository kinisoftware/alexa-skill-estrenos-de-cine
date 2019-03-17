package com.kinisoftware.upcomingMovies.api

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.kinisoftware.upcomingMovies.model.Upcomings
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TheMovieDBService {
    fun getUpcomings(): Upcomings? {
        val gson = GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()

        val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl("https://api.themoviedb.org/3/")
                .build()

        val theMovieDBAPI = retrofit.create(TheMovieDBAPI::class.java)

        val upcomings = theMovieDBAPI.getUpcomings(
                System.getenv("TheMovieDBApiKey"),
                "es-ES",
                1,
                "ES"
        )

        return upcomings.execute().body()
    }
}