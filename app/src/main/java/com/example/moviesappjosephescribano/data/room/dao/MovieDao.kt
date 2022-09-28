package com.example.moviesappjosephescribano.data.room.dao

import androidx.room.*
import com.example.moviesappjosephescribano.data.model.entity.MovieEntityFav
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("select * from moviesfav")
    fun getAll(): Flow<List<MovieEntityFav>>

    @Query("SELECT count(*) from moviesfav where idMovie = :id")
    fun existMovie(id: Int): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovie(movieEntityFav: MovieEntityFav)

    @Delete
    suspend fun deleteMovie(movieEntityFav: MovieEntityFav)
}