package com.kinisoftware.upcomingMovies;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
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

		List<NewRelease> newReleases = new ArrayList<>();
		newReleases.add(new NewRelease("Rompe Ralph", "2018-12-06"));
		newReleases.add(new NewRelease("Robin Hood", "2018-12-06"));
		newReleases.add(new NewRelease("Kursk", "2018-12-06"));
		newReleases.add(new NewRelease("El regreso de Ben", "2018-12-06"));
		newReleases.add(new NewRelease("Mortal Engines", "2018-12-14"));
		newReleases.add(new NewRelease("Miamor perdido", "2018-12-14"));
		newReleases.add(new NewRelease("Yuli", "2018-12-14"));
		newReleases.add(new NewRelease("Aquaman", "2018-12-21"));
		newReleases.add(new NewRelease("El regreso de Mary Poppins", "2018-12-21"));
		newReleases.add(new NewRelease("Bumblebee", "2018-12-28"));
		newReleases.add(new NewRelease("Tiempo después", "2018-12-28"));
		newReleases.add(new NewRelease("El gran baño", "2019-01-11"));
		newReleases.add(new NewRelease("Glass", "2019-01-18"));
		newReleases.add(new NewRelease("X-Men: Fénix Oscura", "2019-02-05"));
		newReleases.add(new NewRelease("Alita: Ángel de combate", "2019-02-15"));
		newReleases.add(new NewRelease("Cómo entrenar a tu dragón 3", "2019-02-22"));
		newReleases.add(new NewRelease("Capitana Marvel", "2019-03-08"));

		List<String> movies = newReleases
				.parallelStream()
				.filter(isReleasedOnDate(dateValue))
				.map(NewRelease::getTitle)
				.collect(Collectors.toList());

		String text;
		if (movies.isEmpty()) {
			text = "Lo siento, aún no tengo estrenos para esa fecha";
		} else {
			text = "Las películas que se estrenan son: ".concat(String.join(", ", movies));
		}
		System.out.println("Output:" + text);

		return input.getResponseBuilder()
				.withSpeech(text)
				.withSimpleCard(CARD_TITLE, text)
				.build();
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
