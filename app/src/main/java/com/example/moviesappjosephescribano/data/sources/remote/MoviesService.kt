package com.example.moviesappjosephescribano.data.sources.remote

import com.example.moviesappjosephescribano.data.pojos.modelpopular.PopularMovie
import com.example.moviesappjosephescribano.data.pojos.modelsearch.SearchMovie
import com.example.moviesappjosephescribano.utils.Constantes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesService {


    @GET(Constantes.MOVIE_POPULAR_PATH)
    suspend fun getPopularMovie(): Response<PopularMovie>

    @GET(Constantes.MULTI_PATH)
    suspend fun search(
        @Query(Constantes.QUERY) titulo: String,
        @Query(Constantes.REGION) region: String
    ): Response<SearchMovie>
}