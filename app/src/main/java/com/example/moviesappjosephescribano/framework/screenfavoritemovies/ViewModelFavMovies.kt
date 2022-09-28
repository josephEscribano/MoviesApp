package com.example.moviesappjosephescribano.framework.screenfavoritemovies

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesappjosephescribano.data.repositories.MovieLocalRepository
import com.example.moviesappjosephescribano.utils.ConstantesLog
import com.example.moviesappjosephescribano.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelFavMovies @Inject constructor(
    private val movieLocalRepository: MovieLocalRepository
) : ViewModel() {

    private val _moviesFav: MutableStateFlow<FavMoviesContract.StateMovieFav> by lazy {
        MutableStateFlow(FavMoviesContract.StateMovieFav())
    }
    val moviesFav: StateFlow<FavMoviesContract.StateMovieFav> = _moviesFav

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()


    fun handleEvent(event: FavMoviesContract.Event) {
        when (event) {
            FavMoviesContract.Event.getAll -> {
                viewModelScope.launch {
                    try {

                        movieLocalRepository.getAll().collect {
                            _moviesFav.update { stateFavMovie -> stateFavMovie.copy(favMovies = it) }
                        }
                    } catch (e: Exception) {
                        e.message?.let { Log.e(ConstantesLog.TAG_ERROR_GET_FAV, it) }
                    }
                }
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

}