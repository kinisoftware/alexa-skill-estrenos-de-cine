package com.kinisoftware.upcomingMovies

import com.amazon.ask.SkillStreamHandler
import com.amazon.ask.Skills
import com.kinisoftware.upcomingMovies.handler.CancelAndStopIntentHandler
import com.kinisoftware.upcomingMovies.handler.HelpIntentHandler
import com.kinisoftware.upcomingMovies.handler.LaunchRequestHandler
import com.kinisoftware.upcomingMovies.handler.NewReleasesIntentHandler
import com.kinisoftware.upcomingMovies.handler.NoIntentHandler
import com.kinisoftware.upcomingMovies.handler.YesIntentHandler

class UpcomingMoviesStreamHandler : SkillStreamHandler(skill) {
    companion object {
        private val skill = Skills.standard()
                //.addRequestInterceptor(LogRequestInterceptor())
                //.addResponseInterceptor(LogResponseInterceptor())
                .addRequestHandlers(
                        LaunchRequestHandler(),
                        HelpIntentHandler(),
                        CancelAndStopIntentHandler(),
                        NewReleasesIntentHandler(MoviesGetter()),
                        YesIntentHandler(MoviesGetter()),
                        NoIntentHandler()
                ).build()
    }
}
