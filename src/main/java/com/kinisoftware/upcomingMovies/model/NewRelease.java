package com.kinisoftware.upcomingMovies.model;

import java.util.Objects;

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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		NewRelease that = (NewRelease) o;
		return Objects.equals(title, that.title) &&
				Objects.equals(releaseDate, that.releaseDate);
	}

	@Override
	public int hashCode() {
		return Objects.hash(title, releaseDate);
	}

	@Override
	public String toString() {
		return "NewRelease{" +
				"title='" + title + '\'' +
				", releaseDate='" + releaseDate + '\'' +
				'}';
	}
}
