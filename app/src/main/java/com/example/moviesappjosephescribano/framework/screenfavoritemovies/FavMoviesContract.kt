package com.example.moviesappjosephescribano.framework.screenfavoritemovies

import com.example.moviesappjosephescribano.domain.Movie

interface FavMoviesContract {

    sealed class Event {
        object getAll : Event()
    }

    data class StateMovieFav(
        val favMovies: List<Movie> = emptyList()
    )
}