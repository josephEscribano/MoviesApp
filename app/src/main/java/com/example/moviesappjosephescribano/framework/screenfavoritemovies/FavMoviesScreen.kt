package com.example.moviesappjosephescribano.framework.screenfavoritemovies

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import coil.compose.rememberImagePainter
import com.example.moviesappjosephescribano.R
import com.example.moviesappjosephescribano.domain.Movie
import com.example.moviesappjosephescribano.ui.theme.Primario
import com.example.moviesappjosephescribano.utils.CargaDrawable


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FavMovieScreen(
    viewModel: ViewModelFavMovies = hiltViewModel()
) {

    LaunchedEffect(key1 = true) {
        viewModel.handleEvent(FavMoviesContract.Event.getAll)
    }
    val moviesFav = viewModel.moviesFav.collectAsState().value.favMovies
    val circularProgressDrawable = CargaDrawable()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxSize()
            .background(Primario)
    ) {

        LazyVerticalGrid(
            cells = GridCells.Fixed(3),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(moviesFav) { movie ->
                ImageAndNameFav(movie = movie, circularProgressDrawable = circularProgressDrawable)
            }
        }

    }
}


@Composable
private fun ImageAndNameFav(
    movie: Movie,
    circularProgressDrawable: CircularProgressDrawable
) {


    Box(
        modifier = Modifier
            .background(Primario)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp)
        ) {

            Image(
                painter = rememberImagePainter(
                    data = stringResource(id = R.string.pathimage) + movie.image,
                    builder = {
                        placeholder(circularProgressDrawable)
                        crossfade(durationMillis = 2000)
                    }
                ),
                contentDescription = movie.title,
                modifier = Modifier
                    .size(200.dp),
                contentScale = ContentScale.Fit
            )

            movie.title?.let {
                Text(
                    text = it,
                    fontFamily = FontFamily.Default,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.White
                )
            }
        }
    }
}


