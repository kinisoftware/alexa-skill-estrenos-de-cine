package com.kinisoftware.upcomingMovies.handler

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.dispatcher.request.handler.RequestHandler
import com.amazon.ask.model.Response
import com.amazon.ask.request.Predicates.intentName
import com.kinisoftware.upcomingMovies.UpcomingMoviesStreamHandler.Companion.CARD_TITLE
import java.util.*

class HelpIntentHandler : RequestHandler {

    override fun canHandle(input: HandlerInput): Boolean {
        return input.matches(intentName("AMAZON.HelpIntent"))
    }

    override fun handle(input: HandlerInput): Optional<Response> {
        val text = "Pregúntame por los estrenos de cine de esta semana, de la próxima semana o de este mes"
        return input.responseBuilder
                .withSpeech(text)
                .withSimpleCard(CARD_TITLE, text)
                .withReprompt(text)
                .build()
    }
}
