package com.kinisoftware.upcomingMovies.model

data class Movie(
        val id: Int,
        val title: String,
        val originalTitle: String,
        val releaseDate: String,
        val originalLanguage: String,
        val posterPath: String?
) {
    var image: String = ""
}