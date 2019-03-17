package com.kinisoftware.upcomingMovies

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.dispatcher.request.handler.RequestHandler
import com.amazon.ask.model.LaunchRequest
import com.amazon.ask.model.Response
import com.amazon.ask.request.Predicates
import com.kinisoftware.upcomingMovies.UpcomingMoviesSkill.Companion.CARD_TITLE
import java.util.*

class LaunchRequestHandler : RequestHandler {

    override fun canHandle(input: HandlerInput): Boolean {
        return input.matches(Predicates.requestType(LaunchRequest::class.java))
    }

    override fun handle(input: HandlerInput): Optional<Response> {
        val repromptText = "Pregúntame por los estrenos de cine de esta semana, de la próxima semana o de este mes"
        val text = "Bienvenido a Estrenos de Cine! Pregúntame por los estrenos de cine de esta semana"
        return input.responseBuilder
                .withSpeech(text)
                .withSimpleCard(CARD_TITLE, text)
                .withReprompt(repromptText)
                .build()
    }
}