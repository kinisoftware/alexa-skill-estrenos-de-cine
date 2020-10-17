package com.kinisoftware.upcomingMovies.handler

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.dispatcher.request.handler.RequestHandler
import com.amazon.ask.exception.AskSdkException
import com.amazon.ask.model.IntentRequest
import com.amazon.ask.model.Response
import com.amazon.ask.model.interfaces.alexa.presentation.apl.RenderDocumentDirective
import com.amazon.ask.request.Predicates
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.kinisoftware.upcomingMovies.MoviesGetter
import com.kinisoftware.upcomingMovies.Translations
import com.kinisoftware.upcomingMovies.getLanguage
import com.kinisoftware.upcomingMovies.getResponse
import com.kinisoftware.upcomingMovies.getTitle
import com.kinisoftware.upcomingMovies.supportAPL
import java.io.File
import java.io.IOException
import java.util.HashMap
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
        val locale = intentRequest.locale
        val language = input.getLanguage()

        val movies = moviesGetter.getUpcomings(locale, dateValue)

        return when {
            input.supportAPL() && movies.isNotEmpty() -> {
                try {
                    val mapper = ObjectMapper()
                    val documentMapType = object : TypeReference<HashMap<String, Any>>() {}
                    val document = mapper.readValue<Map<String, Any>>(File("upcomingMoviesScreen.json"), documentMapType)
                    val dataSourceMapType = object : TypeReference<HashMap<String, Any>>() {}
                    val dataSource = mapper.readValue<Map<String, Any>>(File("upcomingMoviesScreenData.json"), dataSourceMapType)

                    val newReleases = mapOf("newReleases" to movies)
                    val documentDirective = RenderDocumentDirective.builder()
                            .withToken("newReleasesSkillAPLToken")
                            .withDocument(document)
                            .withDatasources(dataSource)
                            .putDatasourcesItem("movies", newReleases)
                            .build()

                    return input.responseBuilder
                            .withSpeech("Te muestro los próximos estrenos de cine")
                            .addDirective(documentDirective)
                            .build()
                } catch (e: IOException) {
                    throw AskSdkException("Unable to read or deserialize upcoming movies data", e)
                }
            }
            else -> {
                var shouldEndSession: Boolean
                var text: String
                if (movies.isEmpty()) {
                    text = Translations.getMessage(language, Translations.TranslationKey.UPCOMINGS_NOT_FOUND) +
                            Translations.getMessage(language, Translations.TranslationKey.ASKING_FOR_NOW_PLAYING)
                    shouldEndSession = false
                } else {
                    text = "${Translations.getMessage(language, Translations.TranslationKey.UPCOMINGS_RESPONSE)}: " +
                            movies.map { it.getTitle() }.getResponse(language)
                    shouldEndSession = true
                }
                input.responseBuilder
                        .withSpeech(text)
                        .withShouldEndSession(shouldEndSession)
                        .build()
            }
        }
    }
}
