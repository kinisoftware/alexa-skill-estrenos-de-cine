package com.kinisoftware.upcomingMovies.handler

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.dispatcher.request.handler.RequestHandler
import com.amazon.ask.model.IntentRequest
import com.amazon.ask.model.Response
import com.amazon.ask.request.Predicates
import com.kinisoftware.upcomingMovies.ReleasesGetter
import com.kinisoftware.upcomingMovies.UpcomingMoviesStreamHandler.Companion.CARD_TITLE
import com.kinisoftware.upcomingMovies.model.NewRelease
import java.time.LocalDate
import java.time.temporal.WeekFields
import java.util.*

class NewReleasesIntentHandler(private val releasesGetter: ReleasesGetter) : RequestHandler {

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
        println("Slot value:$dateValue")

        val movies = releasesGetter.get().filter { it.isReleasedOnDate(dateValue) }.map { it.title }
        val text = if (movies.isEmpty()) {
            "Lo siento, aún no tengo estrenos para esa fecha"
        } else {
            "Las películas que se estrenan son: " + movies.joinToString(", ")
        }
        println("Output:$text")

        return input.responseBuilder
                .withSpeech(text)
                .withSimpleCard(CARD_TITLE, text)
                .withShouldEndSession(true)
                .build()
    }

    private fun NewRelease.isReleasedOnDate(dateValue: String) =
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
