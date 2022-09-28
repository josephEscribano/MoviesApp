package com.example.moviesappjosephescribano.framework.navegation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.moviesappjosephescribano.framework.screenfavoritemovies.FavMovieScreen
import com.example.moviesappjosephescribano.framework.screenpopularmovies.PopularMoviesScreen


@Composable
fun NavigationApp(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = RouteScreens.PopularMoviesScreen.route
    ) {
        composable(route = RouteScreens.PopularMoviesScreen.route) {
            PopularMoviesScreen()
        }

        composable(route = RouteScreens.FavScreen.route) {
            FavMovieScreen()
        }
    }

}