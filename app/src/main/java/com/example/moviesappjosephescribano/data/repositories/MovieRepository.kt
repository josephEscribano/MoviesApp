package com.example.moviesappjosephescribano.data.repositories

import com.example.moviesappjosephescribano.data.sources.remote.RemoteDataSource
import com.example.moviesappjosephescribano.domain.Movie
import com.example.moviesappjosephescribano.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
) {

    suspend fun getPopularMovies(): Flow<NetworkResult<List<Movie>>> {
        return flow {
            emit(NetworkResult.Loading())
            emit(remoteDataSource.getPopularMovies())
        }.flowOn(Dispatchers.IO)
    }

    suspend fun search(titulo: String, region: String): Flow<NetworkResult<List<Movie>>> {
        return flow {
            emit(NetworkResult.Loading())
            emit(remoteDataSource.search(titulo, region))
        }.flowOn(Dispatchers.IO)
    }
}