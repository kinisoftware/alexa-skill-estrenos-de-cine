package com.kinisoftware.upcomingMovies

import com.amazonaws.services.dynamodbv2.model.InternalServerErrorException

object Translations {

    enum class TranslationKey {
        WELCOME, HELP, THANKS, UPCOMINGS_NOT_FOUND, ASKING_FOR_NOW_PLAYING, UPCOMINGS_RESPONSE,
        ERROR_ASKING_NOW_PLAYING, NOW_PLAYING_RESPONSE, REQUESTING_UPCOMINGS, REQUESTING_NOW_PLAYING_MOVIES, AND
    }

    fun getMessage(language: String, messageKey: TranslationKey) =
            messages.getValue(language)[messageKey]
                    ?: throw InternalServerErrorException("There is not messages for $language - $messageKey")

    private val messages = mapOf(
            "es" to mapOf(
                    TranslationKey.WELCOME to "¡Bienvenido a Estrenos de Cine! Pregúntame por los estrenos de cine de esta " + "semana",
                    TranslationKey.HELP to "Pregúntame por los estrenos de cine de esta semana, de la próxima semana o de este mes",
                    TranslationKey.THANKS to "¡Gracias por usar Estrenos de cine!",
                    TranslationKey.UPCOMINGS_NOT_FOUND to "Lo siento, no tengo estrenos para esa fecha.",
                    TranslationKey.ASKING_FOR_NOW_PLAYING to " ¿Te gustaría conocer la cartelera actual?",
                    TranslationKey.UPCOMINGS_RESPONSE to "Las películas que se estrenan son",
                    TranslationKey.ERROR_ASKING_NOW_PLAYING to "Lo siento, no he podido consultar la cartelera actual.",
                    TranslationKey.NOW_PLAYING_RESPONSE to "Las películas en cartelera son",
                    TranslationKey.REQUESTING_UPCOMINGS to "voy a consultar los estrenos para esa fecha.",
                    TranslationKey.REQUESTING_NOW_PLAYING_MOVIES to "voy a consultar la cartelera actual.",
                    TranslationKey.AND to "y"
            ),
            "en" to mapOf(
                    TranslationKey.WELCOME to "Welcome to Upcoming Movies! tu-.",
                    TranslationKey.HELP to "Ask me for the upcoming movies of this week, next week or this month.",
                    TranslationKey.THANKS to "Ok. Thank you for using Upcoming Movies!",
                    TranslationKey.UPCOMINGS_NOT_FOUND to "Sorry, I could not find any upcoming for that date.",
                    TranslationKey.ASKING_FOR_NOW_PLAYING to " Do you want to know what movies are playing now?",
                    TranslationKey.UPCOMINGS_RESPONSE to "Upcoming movies are",
                    TranslationKey.ERROR_ASKING_NOW_PLAYING to "Sorry, I could not get the movies that are playing now",
                    TranslationKey.NOW_PLAYING_RESPONSE to "Movies playing now are",
                    TranslationKey.REQUESTING_UPCOMINGS to "I am gonna check it!",
                    TranslationKey.REQUESTING_NOW_PLAYING_MOVIES to "I am gonna check what is playing now",
                    TranslationKey.AND to "and"
            ),
            "it" to mapOf(
                    TranslationKey.WELCOME to "¡Bienvenido a Estrenos de Cine! Pregúntame por los estrenos de cine de esta " + "semana",
                    TranslationKey.HELP to "Pregúntame por los estrenos de cine de esta semana, de la próxima semana o de este mes",
                    TranslationKey.THANKS to "Vale. ¡Gracias por usar Estrenos de cine!",
                    TranslationKey.UPCOMINGS_NOT_FOUND to "Lo siento, no tengo estrenos para esa fecha.",
                    TranslationKey.ASKING_FOR_NOW_PLAYING to " ¿Te gustaría conocer la cartelera actual?",
                    TranslationKey.UPCOMINGS_RESPONSE to "Las películas que se estrenan son",
                    TranslationKey.ERROR_ASKING_NOW_PLAYING to "Lo siento, no he podido consultar la cartelera actual.",
                    TranslationKey.NOW_PLAYING_RESPONSE to "Las películas en cartelera son"
            ),
            "fr" to mapOf(
                    TranslationKey.WELCOME to "¡Bienvenido a Estrenos de Cine! Pregúntame por los estrenos de cine de esta " + "semana",
                    TranslationKey.HELP to "Pregúntame por los estrenos de cine de esta semana, de la próxima semana o de este mes",
                    TranslationKey.THANKS to "Vale. ¡Gracias por usar Estrenos de cine!",
                    TranslationKey.UPCOMINGS_NOT_FOUND to "Lo siento, no tengo estrenos para esa fecha.",
                    TranslationKey.ASKING_FOR_NOW_PLAYING to " ¿Te gustaría conocer la cartelera actual?",
                    TranslationKey.UPCOMINGS_RESPONSE to "Las películas que se estrenan son",
                    TranslationKey.ERROR_ASKING_NOW_PLAYING to "Lo siento, no he podido consultar la cartelera actual.",
                    TranslationKey.NOW_PLAYING_RESPONSE to "Las películas en cartelera son"
            ),
            "pt" to mapOf(
                    TranslationKey.WELCOME to "¡Bienvenido a Estrenos de Cine! Pregúntame por los estrenos de cine de esta " + "semana",
                    TranslationKey.HELP to "Pregúntame por los estrenos de cine de esta semana, de la próxima semana o de este mes",
                    TranslationKey.THANKS to "Vale. ¡Gracias por usar Estrenos de cine!",
                    TranslationKey.UPCOMINGS_NOT_FOUND to "Lo siento, no tengo estrenos para esa fecha.",
                    TranslationKey.ASKING_FOR_NOW_PLAYING to " ¿Te gustaría conocer la cartelera actual?",
                    TranslationKey.UPCOMINGS_RESPONSE to "Las películas que se estrenan son",
                    TranslationKey.ERROR_ASKING_NOW_PLAYING to "Lo siento, no he podido consultar la cartelera actual.",
                    TranslationKey.NOW_PLAYING_RESPONSE to "Las películas en cartelera son"
            )
    )
}