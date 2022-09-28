package com.example.moviesappjosephescribano.data.model

import com.example.moviesappjosephescribano.data.pojos.modelpopular.ResultPopularMovie
import com.example.moviesappjosephescribano.data.pojos.modelsearch.ResultSearch
import com.example.moviesappjosephescribano.domain.Movie

fun ResultPopularMovie.toMovie(): Movie {
    return Movie(id, title, posterPath, null)
}

fun ResultSearch.toMovie(): Movie {
    return Movie(id, title, posterPath, null)
}