package com.example.moviesappjosephescribano.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val idMovie: Int,
    val title: String?,
    val image: String?,
    var fav: Boolean?
) : Parcelable
