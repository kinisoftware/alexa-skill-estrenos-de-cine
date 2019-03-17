package com.kinisoftware.upcomingMovies

import com.amazon.ask.SkillStreamHandler
import com.amazon.ask.Skills
import com.kinisoftware.upcomingMovies.handler.CancelAndStopIntentHandler
import com.kinisoftware.upcomingMovies.handler.HelpIntentHandler
import com.kinisoftware.upcomingMovies.handler.LaunchRequestHandler
import com.kinisoftware.upcomingMovies.handler.NewReleasesIntentHandler

class UpcomingMoviesStreamHandler : SkillStreamHandler(skill) {
    companion object {
        const val CARD_TITLE = "Estrenos de cine"

        private val skill = Skills.standard().addRequestHandlers(
                LaunchRequestHandler(),
                HelpIntentHandler(),
                CancelAndStopIntentHandler(),
                NewReleasesIntentHandler(ReleasesGetter())
        ).build()
    }
}
