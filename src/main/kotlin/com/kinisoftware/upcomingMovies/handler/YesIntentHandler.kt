package com.kinisoftware.upcomingMovies.handler

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.dispatcher.request.handler.RequestHandler
import com.amazon.ask.model.Response
import com.amazon.ask.request.Predicates
import com.kinisoftware.upcomingMovies.MoviesGetter
import java.util.Optional

class YesIntentHandler(private val moviesGetter: MoviesGetter) : RequestHandler {

    override fun canHandle(input: HandlerInput): Boolean {
        return input.matches(Predicates.intentName("AMAZON.YesIntent"))
    }

    override fun handle(input: HandlerInput): Optional<Response> {
        val movies = moviesGetter.getNowPlayingMovies()
        val text = if (movies.isBlank()) {
            "Lo siento, no he podido consultar la cartelera actual."
        } else {
            "Las películas en cartelera son: $movies"
        }
        return input.responseBuilder
                .withSpeech(text)
                .withShouldEndSession(true)
                .build()
    }
}
