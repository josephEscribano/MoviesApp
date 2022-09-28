package com.example.moviesappjosephescribano.framework.screenpopularmovies

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
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
import com.example.moviesappjosephescribano.utils.Constantes
import com.example.moviesappjosephescribano.utils.UiEvent

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PopularMoviesScreen(
    viewModel: ViewModelPopularMovies = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
                    )
                }
            }

        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.handleEvent(PopularMoviesContract.Event.getPopularMovies)
    }


    val popularMovies = viewModel.popularMovies.collectAsState().value.popularMovies
    val circularProgressDrawable: CircularProgressDrawable = CargaDrawable()


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxSize()
            .background(Primario)
    ) {
        Column {

            Spacer(modifier = Modifier.height(20.dp))
            SearchBar(
                hint = Constantes.SEARCH,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                viewModel.handleEvent(PopularMoviesContract.Event.search(it, Constantes.ALL))
            }

            LazyVerticalGrid(
                cells = GridCells.Fixed(3),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(popularMovies) { movie ->
                    ImageAndName(
                        movie = movie,
                        viewModel,
                        circularProgressDrawable = circularProgressDrawable
                    )
                }
            }
        }

    }
}


@Composable
private fun ImageAndName(
    movie: Movie,
    viewModel: ViewModelPopularMovies,
    circularProgressDrawable: CircularProgressDrawable
) {

    val exist = viewModel.popularMovies.collectAsState().value.exist
    Box(
        modifier = Modifier
            .background(Primario)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .align(Center)
                .padding(16.dp)
        ) {


            Box(
                modifier = Modifier.align(CenterHorizontally)
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
                IconButton(onClick = {

                    if (movie.fav == true) {
                        movie.fav = false
                        viewModel.handleEvent(PopularMoviesContract.Event.deleteMovie(movie))
                        viewModel.handleEvent(PopularMoviesContract.Event.onIdChange(movie.idMovie))

                    } else {
                        movie.fav = true
                        viewModel.handleEvent(PopularMoviesContract.Event.insertMovie(movie))
                        viewModel.handleEvent(PopularMoviesContract.Event.onIdChange(movie.idMovie))
                    }
                }) {

                    if (viewModel.saveId == movie.idMovie && exist) {
                        ChangeIcon(movie)
                    } else {
                        ChangeIcon(movie)
                    }

                }
            }

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


@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    hint: String = "",
    onSearch: (String) -> Unit = {}
) {
    var text by remember {
        mutableStateOf("")
    }
    var isHintDisplayed by remember {
        mutableStateOf(hint != "")
    }


    Box(modifier = modifier) {
        BasicTextField(
            value = text,
            onValueChange = {
                text = it
                onSearch(it)
            },
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(color = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .shadow(5.dp, CircleShape)
                .background(Color.White, CircleShape)
                .padding(horizontal = 20.dp, vertical = 12.dp)
                .onFocusChanged {
                    isHintDisplayed = it.isFocused

                }
        )
        if (isHintDisplayed) {
            Text(
                text = hint,
                color = Color.LightGray,
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 12.dp)
            )
        }
    }
}

@Composable
private fun ChangeIcon(movie: Movie) {
    if (movie.fav == true) {
        Icon(
            painter = painterResource(id = R.drawable.favorite_icon_selected),
            tint = Color.Red,
            contentDescription = null
        )

    } else {

        Icon(
            painter = painterResource(id = R.drawable.favorite_icon),
            tint = Color.Red,
            contentDescription = null
        )
    }
}


