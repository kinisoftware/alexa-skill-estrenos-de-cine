package com.kinisoftware.upcomingMovies

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.model.IntentRequest
import com.amazon.ask.model.LaunchRequest
import com.amazonaws.services.dynamodbv2.model.InternalServerErrorException

object Utils {
    fun getLanguage(locale: String) = locale.substringBefore('-')
    fun getRegion(locale: String) = locale.substringAfter('-')
}

fun HandlerInput.getLanguage() =
        when (requestEnvelope.request) {
            is IntentRequest -> Utils.getLanguage((requestEnvelope.request as IntentRequest).locale)
            is LaunchRequest -> Utils.getLanguage((requestEnvelope.request as LaunchRequest).locale)
            else -> throw InternalServerErrorException("Error casting the input to get the locale")
        }