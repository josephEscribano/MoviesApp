package com.example.moviesappjosephescribano.data.sources.remote

import com.example.moviesappjosephescribano.data.model.toMovie
import com.example.moviesappjosephescribano.data.sources.local.LocalDataSoruce
import com.example.moviesappjosephescribano.domain.Movie
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val moviesService: MoviesService,
    private val localDataSoruce: LocalDataSoruce
) : BaseApiResponse() {

    suspend fun getPopularMovies() =
        safeApiCall(apicall = { moviesService.getPopularMovie() }, transform = {
            it.resultPopularMovies.map { resultPopularMovie ->
                val movie: Movie = resultPopularMovie.toMovie()
                val exist = localDataSoruce.existMovie(movie.idMovie)
                movie.fav = exist > 0

                movie

            }
        })

    suspend fun search(titulo: String, region: String) =
        safeApiCall(apicall = { moviesService.search(titulo, region) },
            transform = {
                it.resultSearches.map { resultSearch ->
                    val movie: Movie = resultSearch.toMovie()
                    val exist = localDataSoruce.existMovie(movie.idMovie)
                    movie.fav = exist > 0

                    movie
                }
            })
}