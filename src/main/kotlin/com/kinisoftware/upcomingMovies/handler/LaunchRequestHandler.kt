package com.kinisoftware.upcomingMovies.handler

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.dispatcher.request.handler.RequestHandler
import com.amazon.ask.model.LaunchRequest
import com.amazon.ask.model.Response
import com.amazon.ask.request.Predicates
import java.util.Optional

class LaunchRequestHandler : RequestHandler {

    override fun canHandle(input: HandlerInput): Boolean {
        return input.matches(Predicates.requestType(LaunchRequest::class.java))
    }

    override fun handle(input: HandlerInput): Optional<Response> {
        val repromptText = "Pregúntame por los estrenos de cine de esta semana, de la próxima semana o de este mes"
        val text = "Bienvenido a Estrenos de Cine! Pregúntame por los estrenos de cine de esta semana"
        return input.responseBuilder
                .withSpeech(text)
                .withReprompt(repromptText)
                .build()
    }
}