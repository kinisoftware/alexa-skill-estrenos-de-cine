package com.kinisoftware.upcomingMovies.handler

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.dispatcher.request.handler.RequestHandler
import com.amazon.ask.model.LaunchRequest
import com.amazon.ask.model.Response
import com.amazon.ask.request.Predicates
import com.kinisoftware.upcomingMovies.Translations
import com.kinisoftware.upcomingMovies.getLanguage
import java.util.Optional

class LaunchRequestHandler : RequestHandler {

    override fun canHandle(input: HandlerInput): Boolean {
        return input.matches(Predicates.requestType(LaunchRequest::class.java))
    }

    override fun handle(input: HandlerInput): Optional<Response> {
        val repromptText = Translations.getMessage(input.getLanguage(), Translations.TranslationKey.HELP)
        val text = Translations.getMessage(input.getLanguage(), Translations.TranslationKey.WELCOME)
        return input.responseBuilder
                .withSpeech(text)
                .withReprompt(repromptText)
                .build()
    }
}