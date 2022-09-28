package com.example.moviesappjosephescribano.framework.screenpopularmovies

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesappjosephescribano.data.repositories.MovieLocalRepository
import com.example.moviesappjosephescribano.data.repositories.MovieRepository
import com.example.moviesappjosephescribano.utils.ConstantesLog
import com.example.moviesappjosephescribano.utils.NetworkResult
import com.example.moviesappjosephescribano.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelPopularMovies @Inject constructor(
    private val movieRepository: MovieRepository,
    private val movieLocalRepository: MovieLocalRepository
) : ViewModel() {


    private val _popularMovies: MutableStateFlow<PopularMoviesContract.StatePopularMovies> by lazy {
        MutableStateFlow(PopularMoviesContract.StatePopularMovies())
    }
    val popularMovies: StateFlow<PopularMoviesContract.StatePopularMovies> = _popularMovies

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var saveId by mutableStateOf(0)


    fun handleEvent(event: PopularMoviesContract.Event) {
        when (event) {
            is PopularMoviesContract.Event.search -> {
                viewModelScope.launch {
                    try {
                        movieRepository.search(event.titulo, event.region)
                            .catch(action = { cause ->
                                Log.e(
                                    ConstantesLog.TAG_ERROR_SEARCH,
                                    cause.message ?: ConstantesLog.MESSAGE_ERROR_SEARCH
                                )
                            }).collect { result ->
                                when (result) {
                                    is NetworkResult.Error -> {
                                        _popularMovies.update { it.copy(isLoading = true) }
                                        result.message?.let {
                                            sendUiEvent(
                                                UiEvent.ShowSnackbar(
                                                    message = ConstantesLog.CONNECTION_ERROR
                                                )
                                            )
                                        }
                                    }
                                    is NetworkResult.Loading -> {
                                        _popularMovies.update { it.copy(isLoading = false) }
                                    }
                                    is NetworkResult.Succcess -> {
                                        _popularMovies.update {
                                            it.copy(
                                                popularMovies = result.data ?: emptyList(),
                                                isLoading = true
                                            )
                                        }
                                    }
                                }

                            }
                    } catch (e: Exception) {
                        e.message?.let { Log.e(ConstantesLog.TAG_ERROR_SEARCH, it) }
                    }
                }
            }
            PopularMoviesContract.Event.getPopularMovies -> {
                viewModelScope.launch {
                    try {
                        movieRepository.getPopularMovies().catch(action = { cause ->
                            Log.e(
                                ConstantesLog.TAG_ERROR_POPULAR_MOVIES,
                                cause.message ?: ConstantesLog.MESSAGE_ERROR_POPULAR_MOVIE
                            )
                        }).collect { result ->
                            when (result) {
                                is NetworkResult.Error -> {
                                    _popularMovies.update { it.copy(isLoading = true) }
                                    result.message?.let {
                                        sendUiEvent(
                                            UiEvent.ShowSnackbar(
                                                message = ConstantesLog.CONNECTION_ERROR
                                            )
                                        )
                                    }
                                }
                                is NetworkResult.Loading -> {
                                    _popularMovies.update { it.copy(isLoading = false) }
                                }
                                is NetworkResult.Succcess -> {
                                    _popularMovies.update {
                                        it.copy(
                                            popularMovies = result.data ?: emptyList(),
                                            isLoading = true
                                        )
                                    }
                                }
                            }

                        }
                    } catch (e: Exception) {
                        e.message?.let { Log.e(ConstantesLog.TAG_ERROR_POPULAR_MOVIES, it) }
                    }
                }
            }
            is PopularMoviesContract.Event.insertMovie -> {
                viewModelScope.launch {
                    try {
                        movieLocalRepository.insertMovie(event.movie)
                        _popularMovies.update { it.copy(exist = true) }
                    } catch (e: Exception) {
                        e.message?.let { Log.e(ConstantesLog.TAG_ERROR_INSERT, it) }
                        sendUiEvent(
                            UiEvent.ShowSnackbar(
                                message = ConstantesLog.INSERT_ERROR
                            )
                        )
                    }
                }
            }
            is PopularMoviesContract.Event.deleteMovie -> {
                viewModelScope.launch {
                    try {
                        movieLocalRepository.deleteMovie(event.movie)
                        _popularMovies.update { it.copy(exist = false) }
                    } catch (e: Exception) {
                        e.message?.let { Log.e(ConstantesLog.TAG_ERROR_DELETE, it) }
                        sendUiEvent(
                            UiEvent.ShowSnackbar(
                                message = ConstantesLog.DELETE_ERROR
                            )
                        )
                    }
                }
            }
            is PopularMoviesContract.Event.onIdChange -> {
                saveId = event.check
            }
        }
    }


    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }


}