package com.example.moviesappjosephescribano.data.pojos.modelsearch


import com.google.gson.annotations.SerializedName

data class SearchMovie(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val resultSearches: List<ResultSearch>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)