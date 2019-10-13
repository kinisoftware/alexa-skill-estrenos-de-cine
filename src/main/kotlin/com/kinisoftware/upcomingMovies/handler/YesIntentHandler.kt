package com.kinisoftware.upcomingMovies.handler

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.dispatcher.request.handler.RequestHandler
import com.amazon.ask.model.IntentRequest
import com.amazon.ask.model.Response
import com.amazon.ask.request.Predicates
import com.kinisoftware.upcomingMovies.DirectiveServiceHandler
import com.kinisoftware.upcomingMovies.MoviesGetter
import com.kinisoftware.upcomingMovies.Translations
import com.kinisoftware.upcomingMovies.getLanguage
import java.util.Optional

class YesIntentHandler(private val moviesGetter: MoviesGetter) : RequestHandler {

    override fun canHandle(input: HandlerInput): Boolean {
        return input.matches(Predicates.intentName("AMAZON.YesIntent"))
    }

    override fun handle(input: HandlerInput): Optional<Response> {
        DirectiveServiceHandler(input).onRequestingNowPlayingMovies(input.getLanguage())
        val request = input.requestEnvelope.request
        val intentRequest = request as IntentRequest
        val movies = moviesGetter.getNowPlayingMovies(intentRequest.locale)
        val text = if (movies.isBlank()) {
            Translations.getMessage(input.getLanguage(), Translations.TranslationKey.ERROR_ASKING_NOW_PLAYING)
        } else {
            "${Translations.getMessage(input.getLanguage(), Translations.TranslationKey.NOW_PLAYING_RESPONSE)}: $movies"
        }
        return input.responseBuilder
                .withSpeech(text)
                .withShouldEndSession(true)
                .build()
    }
}
