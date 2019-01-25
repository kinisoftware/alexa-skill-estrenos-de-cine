package com.kinisoftware.upcomingMovies.handler;

import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;
import static com.kinisoftware.upcomingMovies.UpcomingMoviesStreamHandler.CARD_TITLE;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;

public class CancelAndStopIntentHandler implements RequestHandler {

	@Override
	public boolean canHandle(HandlerInput input) {
		return input.matches(intentName("AMAZON.StopIntent").or(intentName("AMAZON.CancelIntent")));
	}

	@Override
	public Optional<Response> handle(HandlerInput input) {
		String text = "Gracias por usar Estrenos de cine";
		return input.getResponseBuilder()
				.withSpeech(text)
				.withSimpleCard(CARD_TITLE, text)
				.withShouldEndSession(true)
				.build();
	}
}
