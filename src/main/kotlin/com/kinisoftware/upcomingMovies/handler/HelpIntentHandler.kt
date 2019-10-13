package com.kinisoftware.upcomingMovies.handler

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.dispatcher.request.handler.RequestHandler
import com.amazon.ask.model.Response
import com.amazon.ask.request.Predicates.intentName
import com.kinisoftware.upcomingMovies.Translations
import com.kinisoftware.upcomingMovies.getLanguage
import java.util.Optional

class HelpIntentHandler : RequestHandler {

    override fun canHandle(input: HandlerInput): Boolean {
        return input.matches(intentName("AMAZON.HelpIntent"))
    }

    override fun handle(input: HandlerInput): Optional<Response> {
        val text = Translations.getMessage(input.getLanguage(), Translations.TranslationKey.HELP)
        return input.responseBuilder
                .withSpeech(text)
                .withReprompt(text)
                .build()
    }
}
