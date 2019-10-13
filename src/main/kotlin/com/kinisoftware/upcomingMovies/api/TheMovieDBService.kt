package com.kinisoftware.upcomingMovies.api

import com.google.gson.Gson
import com.kinisoftware.upcomingMovies.Utils
import com.kinisoftware.upcomingMovies.model.Movie
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TheMovieDBService(private val gson: Gson) {

    fun getNowPlayingMovies(locale: String): List<Movie> {
        val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl("https://api.themoviedb.org/3/")
                .build()

        val theMovieDBAPI = retrofit.create(TheMovieDBAPI::class.java)

        val upcomings = theMovieDBAPI.getNowPlaying(
                System.getenv("TheMovieDBApiKey"),
                locale,
                1,
                Utils.getRegion(locale)
        )

        return upcomings.execute().body().results
    }

    fun getUpcomings(locale: String): List<Movie> {
        val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl("https://api.themoviedb.org/3/")
                .build()

        val theMovieDBAPI = retrofit.create(TheMovieDBAPI::class.java)

        val upcomings = theMovieDBAPI.getUpcomings(
                System.getenv("TheMovieDBApiKey"),
                locale,
                1,
                Utils.getRegion(locale)
        )

        return upcomings.execute().body().results
    }
}