package com.example.moviesappjosephescribano.data.room.connect

import android.content.Context
import androidx.room.Room
import com.example.moviesappjosephescribano.R
import com.example.moviesappjosephescribano.data.room.MovieRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RoomModule {


    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            MovieRoomDatabase::class.java,
            context.getString(R.string.db)
        )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun movieDao(movieRoomDatabase: MovieRoomDatabase) = movieRoomDatabase.movieDao()
}