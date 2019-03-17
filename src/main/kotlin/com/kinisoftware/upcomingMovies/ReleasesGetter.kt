package com.kinisoftware.upcomingMovies

import com.kinisoftware.upcomingMovies.api.TheMovieDBService
import com.kinisoftware.upcomingMovies.model.Upcoming

class ReleasesGetter {

    fun get(): List<Upcoming> {
        val upcomings = TheMovieDBService().getUpcomings()
        return upcomings?.results.orEmpty()
    }
}
