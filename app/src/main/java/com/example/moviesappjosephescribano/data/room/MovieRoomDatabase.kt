package com.example.moviesappjosephescribano.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.moviesappjosephescribano.data.model.entity.MovieEntityFav
import com.example.moviesappjosephescribano.data.room.dao.MovieDao


@Database(
    entities = [MovieEntityFav::class],
    version = 1,
    exportSchema = true
)
abstract class MovieRoomDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
}