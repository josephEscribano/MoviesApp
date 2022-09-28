package com.example.moviesappjosephescribano.data.model

import com.example.moviesappjosephescribano.data.model.entity.MovieEntityFav
import com.example.moviesappjosephescribano.domain.Movie

fun MovieEntityFav.toMovie(): Movie {
    return Movie(idMovie, title, image, fav)
}

fun Movie.toMovieEntityFav(): MovieEntityFav {
    return MovieEntityFav(idMovie, title, image, fav)
}