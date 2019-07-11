package com.kinisoftware.upcomingMovies

import com.google.gson.Gson
import com.kinisoftware.upcomingMovies.api.TheMovieDBService
import com.kinisoftware.upcomingMovies.model.Movie
import java.time.LocalDate
import java.time.temporal.WeekFields
import java.util.Locale

class MoviesGetter(gson: Gson) {

    private val theMovieDBService = TheMovieDBService(gson)

    fun getUpcomings(locale: String, releaseDate: String) = theMovieDBService.getUpcomings(locale)
            .filter { it.isReleasedOnDate(releaseDate) }
            .map { it.getTitle() }
            .getResponse()

    fun getNowPlayingMovies(locale: String) = theMovieDBService.getNowPlayingMovies(locale)
            .map { it.getTitle() }
            .getResponse()

    private fun Movie.getTitle() =
            when {
                originalTitle == title && originalLanguage != "es" -> {
                    val lang = ssmlLangByMovieOriginalLanguage[originalLanguage]
                    when {
                        lang != null && lang.isNotBlank() -> "<lang xml:lang=$lang>${originalTitle.sanitaze()}</lang>"
                        else -> {
                            println("Unhandled original language $originalLanguage")
                            title.sanitaze()
                        }
                    }

                }
                else -> title.sanitaze()
            }

    private fun String.sanitaze() = replace("&", "&amp;")

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

    companion object {
        private val ssmlLangByMovieOriginalLanguage = mapOf(
                "en" to "\"en-US\"",
                "fr" to "\"fr-FR\""
        )
    }
}
