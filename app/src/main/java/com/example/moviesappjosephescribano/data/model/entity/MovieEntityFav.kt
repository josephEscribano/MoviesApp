package com.example.moviesappjosephescribano.data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "moviesfav")
data class MovieEntityFav(
    @PrimaryKey()
    val idMovie: Int,
    val title: String?,
    val image: String?,
    val fav: Boolean?
)