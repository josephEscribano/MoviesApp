package com.example.moviesappjosephescribano.data.repositories

import com.example.moviesappjosephescribano.data.model.toMovie
import com.example.moviesappjosephescribano.data.model.toMovieEntityFav
import com.example.moviesappjosephescribano.data.sources.local.LocalDataSoruce
import com.example.moviesappjosephescribano.domain.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieLocalRepository @Inject constructor(
    private val localDataSoruce: LocalDataSoruce
) {

    fun getAll(): Flow<List<Movie>> {
        return localDataSoruce.getAll().map {
            it.map { movieEntityFav ->
                movieEntityFav.toMovie()
            }

        }.flowOn(Dispatchers.IO)
    }

    suspend fun insertMovie(movie: Movie) = withContext(Dispatchers.IO) {
        localDataSoruce.insertMovie(movie.toMovieEntityFav())
    }

    suspend fun deleteMovie(movie: Movie) = withContext(Dispatchers.IO) {
        localDataSoruce.deleteMovie(movie.toMovieEntityFav())
    }
}