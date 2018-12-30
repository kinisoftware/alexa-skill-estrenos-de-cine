package com.kinisoftware.upcomingMovies;

import com.amazon.ask.Skill;
import com.amazon.ask.SkillStreamHandler;
import com.amazon.ask.Skills;

public class UpcomingMoviesStreamHandler extends SkillStreamHandler {

	static final String CARD_TITLE = "Estrenos de cine";

	public UpcomingMoviesStreamHandler() {
		super(getSkill());
	}

	private static Skill getSkill() {
		return Skills.standard()
				.addRequestHandlers(
						new LaunchRequestHandler(),
						new HelpIntentHandler(),
						new CancelAndStopIntentHandler(),
						new NewReleasesIntentHandler(),
						new RepeatNewReleasesDateIntentHandler()
				).build();
	}
}
