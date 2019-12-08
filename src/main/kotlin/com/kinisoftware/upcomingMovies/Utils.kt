package com.kinisoftware.upcomingMovies

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.model.IntentRequest
import com.amazon.ask.model.LaunchRequest
import com.amazonaws.services.dynamodbv2.model.InternalServerErrorException
import com.kinisoftware.upcomingMovies.model.Movie

object Utils {
    fun getLanguage(locale: String) = locale.substringBefore('-')
    fun getRegion(locale: String) = locale.substringAfter('-')
}

fun HandlerInput.supportAPL() = requestEnvelope.context.system.device.supportedInterfaces.alexaPresentationAPL != null

fun List<String>.getResponse(language: String) =
        when {
            isNotEmpty() -> take(size - 1).joinToString(", ").plus(" ${Translations.getMessage(language, Translations.TranslationKey.AND)} ${last()}.")
            else -> ""
        }

fun Movie.getTitle() =
        when {
            originalTitle == title && originalLanguage != "es" -> {
                val lang = ssmlLangByMovieOriginalLanguage[originalLanguage]
                when {
                    lang != null && lang.isNotBlank() -> "<lang xml:lang=$lang>${originalTitle.sanitaze()}</lang>"
                    else -> {
                        println("Unhandled original language $originalLanguage")
                        title.sanitaze()
                    }
                }

            }
            else -> title.sanitaze()
        }

private val ssmlLangByMovieOriginalLanguage = mapOf(
        "en" to "\"en-US\"",
        "fr" to "\"fr-FR\""
)

private fun String.sanitaze() = replace("&", "&amp;")

fun HandlerInput.getLanguage() =
        when (requestEnvelope.request) {
            is IntentRequest -> Utils.getLanguage((requestEnvelope.request as IntentRequest).locale)
            is LaunchRequest -> Utils.getLanguage((requestEnvelope.request as LaunchRequest).locale)
            else -> throw InternalServerErrorException("Error casting the input to get the locale")
        }