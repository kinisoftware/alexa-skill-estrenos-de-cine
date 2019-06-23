package com.kinisoftware.upcomingMovies

import com.kinisoftware.upcomingMovies.api.TheMovieDBService
import com.kinisoftware.upcomingMovies.model.Movie
import java.time.LocalDate
import java.time.temporal.WeekFields
import java.util.Locale

class MoviesGetter {

    fun getUpcomings(releaseDate: String) = TheMovieDBService().getUpcomings()
            .filter { it.isReleasedOnDate(releaseDate) }.map { it.title }.getResponse()

    fun getNowPlayingMovies() = TheMovieDBService().getNowPlayingMovies().map { it.title }.getResponse()

    private fun List<String>.getResponse() =
            when {
                isNotEmpty() -> take(size - 1).joinToString(", ").plus(" y ${last()}.")
                else -> ""
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
