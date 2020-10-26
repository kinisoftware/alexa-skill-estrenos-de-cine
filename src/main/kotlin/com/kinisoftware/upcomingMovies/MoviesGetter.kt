package com.kinisoftware.upcomingMovies

import com.google.gson.Gson
import com.kinisoftware.upcomingMovies.api.TheMovieDBService
import com.kinisoftware.upcomingMovies.model.Movie
import java.time.LocalDate
import java.time.temporal.WeekFields
import java.util.Locale

class MoviesGetter(gson: Gson) {

    private val theMovieDBService = TheMovieDBService(gson)

    fun getUpcomings(locale: String, releaseDate: String): List<Movie> {
        val configuration = theMovieDBService.getAPIConfiguration()

        val movies: MutableList<Movie> = mutableListOf()
        theMovieDBService.getUpcomings(locale)
                .filter { it.isReleasedOnDate(releaseDate) }
                .forEach {
                    if (!it.posterPath.isNullOrEmpty()) {
                        it.addImage(configuration.images.secureBaseUrl)
                        movies.add(it)
                    }
                }

        return movies.sortedBy { it.releaseDate }
    }

    fun getNowPlayingMovies(locale: String): List<Movie> {
        val configuration = theMovieDBService.getAPIConfiguration()

        val movies: MutableList<Movie> = mutableListOf()
        theMovieDBService.getNowPlayingMovies(locale).forEach {
            it.addImage(configuration.images.secureBaseUrl)
            movies.add(it)
        }

        return movies
    }

    private fun Movie.addImage(baseUrl: String) {
        image = baseUrl + "w500" + posterPath
    }

    private fun Movie.isReleasedOnDate(dateValue: String) =
            getWeekFormatStyle(releaseDate) == dateValue || getMonthFormatStyle(releaseDate) == dateValue

    private fun getWeekFormatStyle(releaseDate: String): String {
        val localDate = LocalDate.parse(releaseDate)
        val weekFields = WeekFields.of(Locale.getDefault())
        return localDate.year.toString() + "-W" + localDate.get(weekFields.weekOfWeekBasedYear())
    }

    private fun getMonthFormatStyle(releaseDate: String): String {
        val localDate = LocalDate.parse(releaseDate)
        return if (localDate.monthValue < 10)
            localDate.year.toString() + "-0" + localDate.monthValue
        else
            localDate.year.toString() + "-" + localDate.monthValue
    }
}
