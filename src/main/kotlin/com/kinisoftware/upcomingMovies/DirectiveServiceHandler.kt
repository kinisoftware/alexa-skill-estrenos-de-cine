package com.kinisoftware.upcomingMovies

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.model.services.directive.Header
import com.amazon.ask.model.services.directive.SendDirectiveRequest
import com.amazon.ask.model.services.directive.SpeakDirective
import kotlin.random.Random

class DirectiveServiceHandler(
        private val input: HandlerInput
) {

    fun onRequestingUpcomings(language: String) {
        enqueueMessage(Translations.getMessage(language, Translations.TranslationKey.REQUESTING_UPCOMINGS))
    }

    fun onRequestingNowPlayingMovies(language: String) {
        enqueueMessage(Translations.getMessage(language, Translations.TranslationKey.REQUESTING_NOW_PLAYING_MOVIES))
    }

    private fun enqueueMessage(message: String) {
        val requestId = input.requestEnvelope.request.requestId
        val sendDirectiveRequest = SendDirectiveRequest.builder()
                .withDirective(SpeakDirective.builder()
                        .withSpeech("${getRandomOkSpeech()}, $message")
                        .build())
                .withHeader(Header.builder()
                        .withRequestId(requestId)
                        .build())
                .build()
        input.serviceClientFactory.directiveService.enqueue(sendDirectiveRequest)
    }

    private fun getRandomOkSpeech(): String {
        val randomOkSpeechcon = listOf("okey", "okey dokey", "okey makey")[Random.nextInt(2)]
        return "<say-as interpret-as=\"interjection\">$randomOkSpeechcon</say-as>"


    }
}