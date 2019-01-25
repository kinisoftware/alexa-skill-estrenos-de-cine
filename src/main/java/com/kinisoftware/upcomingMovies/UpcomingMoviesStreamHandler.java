package com.kinisoftware.upcomingMovies;

import com.amazon.ask.Skill;
import com.amazon.ask.SkillStreamHandler;
import com.amazon.ask.Skills;
import com.kinisoftware.upcomingMovies.handler.CancelAndStopIntentHandler;
import com.kinisoftware.upcomingMovies.handler.HelpIntentHandler;
import com.kinisoftware.upcomingMovies.handler.LaunchRequestHandler;
import com.kinisoftware.upcomingMovies.handler.NewReleasesIntentHandler;

public class UpcomingMoviesStreamHandler extends SkillStreamHandler {

	public static final String CARD_TITLE = "Estrenos de cine";

	public UpcomingMoviesStreamHandler() {
		super(getSkill());
	}

	private static Skill getSkill() {
		return Skills.standard()
				.addRequestHandlers(
						new LaunchRequestHandler(),
						new HelpIntentHandler(),
						new CancelAndStopIntentHandler(),
						new NewReleasesIntentHandler()
				).build();
	}
}
