package com.kinisoftware.upcomingMovies.model;

public class NewRelease {

	private final String title;
	private final String releaseDate;

	public NewRelease(String title, String releaseDate) {
		this.title = title;
		this.releaseDate = releaseDate;
	}

	public String getTitle() {
		return title;
	}

	public String getReleaseDate() {
		return releaseDate;
	}
}
