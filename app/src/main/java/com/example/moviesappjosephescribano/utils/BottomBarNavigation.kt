package com.example.moviesappjosephescribano.utils

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.moviesappjosephescribano.framework.navegation.RouteScreens
import com.example.moviesappjosephescribano.ui.theme.AzulTurquesa
import com.example.moviesappjosephescribano.ui.theme.Secundario


@Composable
fun BottomBarNavigation(
    items: List<RouteScreens>,
    navController: NavController,
    currentDestination: NavDestination?,
    onItemClick: (RouteScreens) -> Unit
) {

    AnimatedVisibility(
        visible = true,
        enter = slideInVertically(initialOffsetY = { -it }),
        exit = slideOutVertically(targetOffsetY = { -it }),
        content = {
            val backStackEntry = navController.currentBackStackEntryAsState()
            BottomNavigation(
                backgroundColor = Secundario,
                elevation = 5.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
            ) {
                items.forEach { item ->
                    val selected = currentDestination?.hierarchy?.any(){ destination ->
                        destination.route == item.route
                    } == true

                    BottomNavigationItem(

                        label = { Text(text = item.title) },
                        selected = selected,
                        onClick = { onItemClick(item) },
                        unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
                        icon = {
                            when (item) {
                                RouteScreens.PopularMoviesScreen -> {
                                    Icon(
                                        modifier = Modifier
                                            .width(30.dp)
                                            .size(30.dp),
                                        tint = Color.Black,
                                        painter = painterResource(id = com.example.moviesappjosephescribano.R.drawable.list_icon),
                                        contentDescription = "Navigation Icon"
                                    )
                                }
                                RouteScreens.FavScreen -> {
                                    Icon(
                                        modifier = Modifier
                                            .width(40.dp)
                                            .size(40.dp),
                                        tint = Color.Black,
                                        painter = painterResource(id = com.example.moviesappjosephescribano.R.drawable.favorite_icon_selected),
                                        contentDescription = "Navigation Icon"
                                    )
                                }
                            }

                        }
                    )
                }
            }
        })


}