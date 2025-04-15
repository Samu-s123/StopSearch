package com.univaq.stopsearch.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.univaq.stopsearch.ui.screen.list.ListScreen
import com.univaq.stopsearch.ui.screen.map.MapScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    padding: PaddingValues
){
    //NavHost is a composable that hosts the navigation graph
    //the navigation graph is a collection of destinations that can be navigated between
    NavHost(
        navController = navController,
        startDestination = Screen.List, //we specify the starting screen of the navigation graph
    ) {
        //composable function that defines a destination in the navigation graph
        composable<Screen.List>{
            ListScreen(Modifier.padding(padding))
        }
        composable<Screen.Map>{
            MapScreen(Modifier.padding(padding))
        }
    }
}

data class NavigationItem(
    val title: String,
    val icon: ImageVector,
    val route: Screen
)