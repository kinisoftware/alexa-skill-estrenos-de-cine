package com.kinisoftware.upcomingMovies.handler

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.dispatcher.request.handler.RequestHandler
import com.amazon.ask.model.Response
import com.amazon.ask.request.Predicates
import com.kinisoftware.upcomingMovies.Translations
import com.kinisoftware.upcomingMovies.getLanguage
import java.util.Optional

class NoIntentHandler() : RequestHandler {

    override fun canHandle(input: HandlerInput): Boolean {
        return input.matches(Predicates.intentName("AMAZON.NoIntent"))
    }

    override fun handle(input: HandlerInput): Optional<Response> {
        return input.responseBuilder
                .withSpeech(Translations.getMessage(input.getLanguage(), Translations.TranslationKey.THANKS))
                .withShouldEndSession(true)
                .build()
    }
}
