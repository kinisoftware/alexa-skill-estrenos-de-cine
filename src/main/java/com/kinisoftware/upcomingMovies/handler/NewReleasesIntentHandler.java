package com.kinisoftware.upcomingMovies.handler;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.kinisoftware.upcomingMovies.UpcomingMoviesStreamHandler.CARD_TITLE;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Intent;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Request;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.Slot;
import com.amazon.ask.request.Predicates;
import com.amazon.ask.response.ResponseBuilder;
import com.kinisoftware.upcomingMovies.ReleasesGetter;
import com.kinisoftware.upcomingMovies.model.NewRelease;

public class NewReleasesIntentHandler implements RequestHandler {

	@Override
	public boolean canHandle(HandlerInput input) {
		return input.matches(Predicates.intentName("NewReleasesIntent"));
	}

	@Override
	public Optional<Response> handle(HandlerInput input) {
		Request request = input.getRequestEnvelope().getRequest();
		IntentRequest intentRequest = (IntentRequest) request;
		Intent intent = intentRequest.getIntent();
		Map<String, Slot> slots = intent.getSlots();

		Slot releasesDate = slots.get("releasesDate");
		String dateValue = releasesDate.getValue();
		System.out.println("Slot value:" + dateValue);

		String text = null;
		String reprompText = null;
		if (dateValue == null) {
			reprompText = "Lo siento, no he entendido la fecha que quieres consultar. ¿Podrías repetir, por favor?";
		} else {
			List<String> movies = ReleasesGetter.get()
					.parallelStream()
					.filter(isReleasedOnDate(dateValue))
					.map(NewRelease::getTitle)
					.collect(Collectors.toList());

			if (movies.isEmpty()) {
				text = "Lo siento, aún no tengo estrenos para esa fecha";
			} else {
				text = "Las películas que se estrenan son: ".concat(String.join(", ", movies));
			}
		}
		System.out.println("Output:" + text);

		ResponseBuilder responseBuilder = input.getResponseBuilder().withSpeech(text).withSimpleCard(CARD_TITLE, text);
		if (reprompText != null) {
			responseBuilder.withReprompt(reprompText);
		}

		return responseBuilder.build();
	}

	private Predicate<NewRelease> isReleasedOnDate(String dateValue) {
		return newRelease -> getWeekFormatStyle(newRelease.getReleaseDate()).equals(dateValue) || getMonthFormatStyle(newRelease.getReleaseDate()).equals(dateValue);
	}

	private String getWeekFormatStyle(String releaseDate) {
		LocalDate localDate = LocalDate.parse(releaseDate);
		WeekFields weekFields = WeekFields.of(Locale.getDefault());
		return localDate.getYear() + "-W" + localDate.get(weekFields.weekOfWeekBasedYear());
	}

	private String getMonthFormatStyle(String releaseDate) {
		LocalDate localDate = LocalDate.parse(releaseDate);
		return localDate.getMonthValue() < 10
				? localDate.getYear() + "-0" + localDate.getMonthValue()
				: localDate.getYear() + "-" + localDate.getMonthValue();
	}
}
