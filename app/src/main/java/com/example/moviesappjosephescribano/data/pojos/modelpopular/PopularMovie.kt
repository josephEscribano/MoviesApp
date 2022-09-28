package com.example.moviesappjosephescribano.data.pojos.modelpopular


import com.google.gson.annotations.SerializedName

data class PopularMovie(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val resultPopularMovies: List<ResultPopularMovie>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)