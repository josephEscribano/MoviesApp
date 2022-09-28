package com.example.moviesappjosephescribano.framework.navegation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ViewList
import androidx.compose.ui.graphics.vector.ImageVector

sealed class RouteScreens(
    val route: String,
    val title: String,
    val icon: ImageVector? = null
) {
    object PopularMoviesScreen : RouteScreens(
        route = "popularMoviesScreen",
        title = "popular",
        icon = Icons.Default.ViewList
    )

    object FavScreen : RouteScreens(
        route = "favMoviesScreen",
        title = "favorite",
        icon = Icons.Default.Favorite
    )
}