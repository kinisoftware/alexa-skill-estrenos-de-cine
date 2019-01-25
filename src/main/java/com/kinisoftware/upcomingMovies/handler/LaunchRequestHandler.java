package com.kinisoftware.upcomingMovies.handler;

import java.util.Optional;

import static com.kinisoftware.upcomingMovies.UpcomingMoviesStreamHandler.CARD_TITLE;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.LaunchRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.request.Predicates;

public class LaunchRequestHandler implements RequestHandler {

	@Override
	public boolean canHandle(HandlerInput input) {
		return input.matches(Predicates.requestType(LaunchRequest.class));
	}

	@Override
	public Optional<Response> handle(HandlerInput input) {
		String repromptText = "Pregúntame por los estrenos de cine de esta semana, de la próxima semana o de este mes";
		String text = "Bienvenido a Estrenos de Cine! Pregúntame por los estrenos de cine de esta semana";
		return input.getResponseBuilder()
				.withSpeech(text)
				.withSimpleCard(CARD_TITLE, text)
				.withReprompt(repromptText)
				.build();
	}
}