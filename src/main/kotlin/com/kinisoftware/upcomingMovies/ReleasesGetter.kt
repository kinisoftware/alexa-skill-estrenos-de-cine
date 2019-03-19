package com.kinisoftware.upcomingMovies

import com.kinisoftware.upcomingMovies.model.NewRelease
import java.nio.file.Files
import java.nio.file.Paths
import javax.inject.Singleton
import kotlin.streams.toList

@Singleton
class ReleasesGetter {

    private var releases = emptyList<NewRelease>()

    fun get(): List<NewRelease> {
        if (releases.isEmpty()) {
            releases = getReleases()
        }
        return releases
    }

    private fun getReleases(): List<NewRelease> {
        try {
            val resource = ReleasesGetter::class.java.classLoader.getResource("releases.csv")
            return resource?.let {
                val fileUri = resource.toURI()
                val lines = Files.lines(Paths.get(fileUri))
                lines
                        .map { line -> line.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray() }
                        .map { line -> NewRelease(line[0], line[1]) }
                        .toList()
            }.orEmpty()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return emptyList()
    }
}
