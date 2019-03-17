package com.kinisoftware.upcomingMovies

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.model.IntentRequest
import com.amazon.ask.model.Response
import com.kinisoftware.upcomingMovies.model.NewRelease
import io.micronaut.function.aws.alexa.AlexaIntents
import io.micronaut.function.aws.alexa.annotation.IntentHandler
import java.time.LocalDate
import java.time.temporal.WeekFields
import java.util.*
import javax.inject.Singleton

@Singleton
class UpcomingMoviesSkill(val releasesGetter: ReleasesGetter) {

    companion object {
        const val CARD_TITLE = "Estrenos de cine"
    }

    @IntentHandler("NewReleasesIntent")
    fun handleNewReleasesIntent(input: HandlerInput): Optional<Response> {
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

    @IntentHandler(AlexaIntents.HELP)
    fun handleHelpIntent(input: HandlerInput): Optional<Response> {
        val text = "Pregúntame por los estrenos de cine de esta semana, de la próxima semana o de este mes"
        return input.responseBuilder
                .withSpeech(text)
                .withSimpleCard(CARD_TITLE, text)
                .withReprompt(text)
                .build()
    }

    @IntentHandler(AlexaIntents.CANCEL, AlexaIntents.STOP)
    fun handleCancelAndStopIntents(input: HandlerInput): Optional<Response> {
        val text = "Gracias por usar Estrenos de cine"
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
