package com.univaq.stopsearch.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun bottomNavigationBar(
    navController: NavHostController
){

    val items = remember {
        listOf(
            NavigationItem(
                title = "Stops",
                icon = Icons.AutoMirrored.Default.List,
                route = Screen.List
            ),
            NavigationItem(
                title = "Map",
                icon = Icons.Default.LocationOn,
                route = Screen.Map
            )
        )
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar{
        items.forEach {
            NavigationBarItem(
                selected = currentRoute == it.route.javaClass.canonicalName,
                onClick = {
                    navController.navigate(it.route){
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {Icon(imageVector = it.icon, contentDescription = it.title)},
                label = {Text(text = it.title)},
                alwaysShowLabel = false
            )
        }
    }
}