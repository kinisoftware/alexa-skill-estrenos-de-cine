package com.kinisoftware.upcomingMovies.api

import com.google.gson.Gson
import com.kinisoftware.upcomingMovies.model.Movie
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TheMovieDBService(private val gson: Gson) {

    fun getNowPlayingMovies(): List<Movie> {
        val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl("https://api.themoviedb.org/3/")
                .build()

        val theMovieDBAPI = retrofit.create(TheMovieDBAPI::class.java)

        val upcomings = theMovieDBAPI.getNowPlaying(
                System.getenv("TheMovieDBApiKey"),
                "es-ES",
                1,
                "ES"
        )

        return upcomings.execute().body().results
    }

    fun getUpcomings(): List<Movie> {
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

        return upcomings.execute().body().results
    }
}