package com.example.moviesappjosephescribano.framework

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.moviesappjosephescribano.framework.navegation.NavigationApp
import com.example.moviesappjosephescribano.framework.navegation.RouteScreens
import com.example.moviesappjosephescribano.ui.theme.MoviesAppJosephEscribanoTheme
import com.example.moviesappjosephescribano.utils.BottomBarNavigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoviesAppJosephEscribanoTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),

                    ) {
                    App()
                }
            }
        }
    }
}

@Composable
fun App() {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val screens = listOf(
        RouteScreens.PopularMoviesScreen,
        RouteScreens.FavScreen
    )

    Scaffold(
        bottomBar = {
            BottomBarNavigation(
                items = screens, navController = navController,
                onItemClick = {
                    navController.navigate(it.route)
                },
                currentDestination = currentDestination
            )
        }
    ) {
        Box(modifier = Modifier.padding(it)) {

            NavigationApp(navController = navController)

        }
    }


}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MoviesAppJosephEscribanoTheme {
        App()
    }
}