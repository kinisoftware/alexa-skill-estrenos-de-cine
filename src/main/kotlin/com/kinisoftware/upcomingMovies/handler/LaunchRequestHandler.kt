package com.kinisoftware.upcomingMovies.handler

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.dispatcher.request.handler.RequestHandler
import com.amazon.ask.exception.AskSdkException
import com.amazon.ask.model.LaunchRequest
import com.amazon.ask.model.Response
import com.amazon.ask.model.interfaces.alexa.presentation.apl.RenderDocumentDirective
import com.amazon.ask.request.Predicates
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.kinisoftware.upcomingMovies.Translations
import com.kinisoftware.upcomingMovies.getLanguage
import com.kinisoftware.upcomingMovies.supportAPL
import java.io.File
import java.io.IOException
import java.util.HashMap
import java.util.Optional

class LaunchRequestHandler : RequestHandler {

    override fun canHandle(input: HandlerInput): Boolean {
        return input.matches(Predicates.requestType(LaunchRequest::class.java))
    }

    override fun handle(input: HandlerInput): Optional<Response> {
        val text = Translations.getMessage(input.getLanguage(), Translations.TranslationKey.WELCOME)
        return when {
            input.supportAPL() -> {
                try {
                    val mapper = ObjectMapper()
                    val documentMapType = object : TypeReference<HashMap<String, Any>>() {}
                    val document = mapper.readValue<Map<String, Any>>(File("upcomingMoviesScreen.json"), documentMapType)
                    val dataSourceMapType = object : TypeReference<HashMap<String, Any>>() {}
                    val dataSource = mapper.readValue<Map<String, Any>>(File("upcomingMoviesScreenData.json"), dataSourceMapType)
                    val documentDirective = RenderDocumentDirective.builder()
                            .withDocument(document)
                            .withDatasources(dataSource)
                            .build()

                    return input.responseBuilder
                            .withSpeech(text)
                            .addDirective(documentDirective)
                            .withShouldEndSession(false)
                            .build()
                } catch (e: IOException) {
                    throw AskSdkException("Unable to read or deserialize upcoming movies data", e)
                }
            }
            else -> {
                val repromptText = Translations.getMessage(input.getLanguage(), Translations.TranslationKey.HELP)
                input.responseBuilder
                        .withSpeech(text)
                        .withReprompt(repromptText)
                        .build()
            }
        }
    }
}