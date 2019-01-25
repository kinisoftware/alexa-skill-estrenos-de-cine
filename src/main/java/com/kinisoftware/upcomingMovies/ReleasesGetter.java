package com.kinisoftware.upcomingMovies;

import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.kinisoftware.upcomingMovies.model.NewRelease;

public class ReleasesGetter {

	private static List<NewRelease> releases = Collections.emptyList();

	public static List<NewRelease> get() {
		if (releases.isEmpty()) {
			releases = getReleases();
		}
		return releases;
	}

	private static List<NewRelease> getReleases() {
		try {
			URL resource = ReleasesGetter.class.getClassLoader().getResource("releases.csv");
			if (resource != null) {
				URI fileUri = resource.toURI();
				Stream<String> lines = Files.lines(Paths.get(fileUri));
				return lines
						.map(line -> line.split(","))
						.map(line -> new NewRelease(line[0], line[1]))
						.collect(Collectors.toList());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Collections.emptyList();
	}
}
