package com.kinisoftware.upcomingMovies

import com.amazon.ask.SkillStreamHandler
import com.amazon.ask.Skills
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.kinisoftware.upcomingMovies.handler.CancelAndStopIntentHandler
import com.kinisoftware.upcomingMovies.handler.HelpIntentHandler
import com.kinisoftware.upcomingMovies.handler.LaunchRequestHandler
import com.kinisoftware.upcomingMovies.handler.NewReleasesIntentHandler
import com.kinisoftware.upcomingMovies.handler.NoIntentHandler
import com.kinisoftware.upcomingMovies.handler.SessionEndedRequestHandler
import com.kinisoftware.upcomingMovies.handler.YesIntentHandler
import com.kinisoftware.upcomingMovies.interceptor.LogRequestInterceptor
import com.kinisoftware.upcomingMovies.interceptor.LogResponseInterceptor

class UpcomingMoviesStreamHandler : SkillStreamHandler(skill) {
    companion object {
        private val gson = GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()

        private val moviesGetter = MoviesGetter(gson)

        private val skill = Skills.standard()
                .addRequestInterceptor(LogRequestInterceptor())
                .addResponseInterceptor(LogResponseInterceptor())
                .addRequestHandlers(
                        LaunchRequestHandler(),
                        HelpIntentHandler(),
                        CancelAndStopIntentHandler(),
                        NewReleasesIntentHandler(moviesGetter),
                        YesIntentHandler(moviesGetter),
                        NoIntentHandler(),
                        SessionEndedRequestHandler()
                ).build()
    }
}
