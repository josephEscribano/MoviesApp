package com.example.moviesappjosephescribano.data.sources.local

import com.example.moviesappjosephescribano.data.model.entity.MovieEntityFav
import com.example.moviesappjosephescribano.data.room.dao.MovieDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSoruce @Inject constructor(
    private val movieDao: MovieDao
) {

    fun getAll(): Flow<List<MovieEntityFav>> = movieDao.getAll()

    fun existMovie(id: Int) = movieDao.existMovie(id)

    suspend fun insertMovie(movieEntityFav: MovieEntityFav) = movieDao.insertMovie(movieEntityFav)

    suspend fun deleteMovie(movieEntityFav: MovieEntityFav) = movieDao.deleteMovie(movieEntityFav)
}