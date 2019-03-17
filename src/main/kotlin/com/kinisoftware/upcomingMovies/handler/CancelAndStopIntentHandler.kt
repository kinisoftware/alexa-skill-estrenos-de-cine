package com.kinisoftware.upcomingMovies.handler

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.dispatcher.request.handler.RequestHandler
import com.amazon.ask.model.Response
import com.amazon.ask.request.Predicates.intentName
import com.kinisoftware.upcomingMovies.UpcomingMoviesStreamHandler.Companion.CARD_TITLE
import java.util.*

class CancelAndStopIntentHandler : RequestHandler {

    override fun canHandle(input: HandlerInput): Boolean {
        return input.matches(intentName("AMAZON.StopIntent").or(intentName("AMAZON.CancelIntent")))
    }

    override fun handle(input: HandlerInput): Optional<Response> {
        val text = "Gracias por usar Estrenos de cine"
        return input.responseBuilder
                .withSpeech(text)
                .withSimpleCard(CARD_TITLE, text)
                .withShouldEndSession(true)
                .build()
    }
}
