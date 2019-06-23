package com.kinisoftware.upcomingMovies.handler

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.dispatcher.request.handler.RequestHandler
import com.amazon.ask.model.IntentRequest
import com.amazon.ask.model.Response
import com.amazon.ask.request.Predicates
import com.kinisoftware.upcomingMovies.MoviesGetter
import com.kinisoftware.upcomingMovies.model.Movie
import java.time.LocalDate
import java.time.temporal.WeekFields
import java.util.Locale
import java.util.Optional

class NewReleasesIntentHandler(private val moviesGetter: MoviesGetter) : RequestHandler {

    override fun canHandle(input: HandlerInput): Boolean {
        return input.matches(Predicates.intentName("NewReleasesIntent"))
    }

    override fun handle(input: HandlerInput): Optional<Response> {
        val request = input.requestEnvelope.request
        val intentRequest = request as IntentRequest
        val intent = intentRequest.intent
        val slots = intent.slots

        val releasesDate = slots["releasesDate"]!!
        val dateValue = releasesDate.value

        val movies = moviesGetter.getUpcomings(dateValue)
        return if (movies.isBlank()) {
            val text = "Lo siento, aún no tengo estrenos para esa fecha."
            val reprompt = " ¿Te gustaría conocer la cartelera actual?"
            input.responseBuilder
                    .withSpeech(text + reprompt)
                    .withReprompt(reprompt)
                    .build()
        } else {
            val text = "Las películas que se estrenan son: $movies"
            input.responseBuilder
                    .withSpeech(text)
                    .withShouldEndSession(true)
                    .build()
        }
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
