package com.example.moviesappjosephescribano.framework.screenpopularmovies

import com.example.moviesappjosephescribano.domain.Movie


interface PopularMoviesContract {


    sealed class Event {
        object getPopularMovies : Event()
        data class insertMovie(val movie: Movie) : Event()
        data class deleteMovie(val movie: Movie) : Event()
        data class onIdChange(val check: Int) : Event()
        data class search(val titulo: String, val region: String) : Event()
    }

    data class StatePopularMovies(
        val popularMovies: List<Movie> = emptyList(),
        val isLoading: Boolean = false,
        val exist: Boolean = false
    )
}