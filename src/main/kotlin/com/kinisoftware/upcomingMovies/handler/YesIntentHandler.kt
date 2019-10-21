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
import com.kinisoftware.upcomingMovies.Utils
import com.kinisoftware.upcomingMovies.getLanguage
import com.kinisoftware.upcomingMovies.getResponse
import java.io.File
import java.io.IOException
import java.util.HashMap
import java.util.Optional

class YesIntentHandler(private val moviesGetter: MoviesGetter) : RequestHandler {

    override fun canHandle(input: HandlerInput): Boolean {
        return input.matches(Predicates.intentName("AMAZON.YesIntent"))
    }

    override fun handle(input: HandlerInput): Optional<Response> {
        val request = input.requestEnvelope.request
        val intentRequest = request as IntentRequest
        val language = input.getLanguage()

        val movies = moviesGetter.getNowPlayingMovies(intentRequest.locale)

        return when {
            movies.isEmpty() -> {
                val text = Translations.getMessage(language, Translations.TranslationKey.ERROR_ASKING_NOW_PLAYING)
                input.responseBuilder
                        .withSpeech(text)
                        .withShouldEndSession(true)
                        .build()
            }
            Utils.supportAPL(input) -> {
                val text = "Te muestro la cartelera actual"
                try {
                    val mapper = ObjectMapper()
                    val documentMapType = object : TypeReference<HashMap<String, Any>>() {}
                    val document = mapper.readValue<Map<String, Any>>(File("upcomingMoviesScreen.json"), documentMapType)
                    val dataSourceMapType = object : TypeReference<HashMap<String, Any>>() {}
                    val dataSource = mapper.readValue<Map<String, Any>>(File("upcomingMoviesScreenData.json"), dataSourceMapType)

                    val newReleases = mapOf("newReleases" to movies)
                    val documentDirective = RenderDocumentDirective.builder()
                            .withDocument(document)
                            .withDatasources(dataSource)
                            .putDatasourcesItem("movies", newReleases)
                            .build()

                    return input.responseBuilder
                            .withSpeech(text)
                            .addDirective(documentDirective)
                            .withShouldEndSession(true)
                            .build()
                } catch (e: IOException) {
                    throw AskSdkException("Unable to read or deserialize upcoming movies data", e)
                }
            }
            else -> {
                val text = "${Translations.getMessage(language, Translations.TranslationKey.NOW_PLAYING_RESPONSE)}: ${movies.map { it.title }.getResponse(language)}"
                input.responseBuilder
                        .withSpeech(text)
                        .withShouldEndSession(true)
                        .build()
            }
        }
    }
}
