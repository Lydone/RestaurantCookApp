package com.lydone.restaurantcookapp.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.lydone.restaurantcookapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantCookApp() {
    val navController = rememberNavController()
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination
    Scaffold {
        Row {
            NavigationRail {
                NavigationRailItem(
                    selected = currentDestination.isQueue(),
                    onClick = navController::navigateToQueue,
                    icon = { Icon(Icons.Default.List, contentDescription = null) },
                    label = { Text(stringResource(R.string.item_dishes)) },
                )
                NavigationRailItem(
                    selected = currentDestination.isStopList(),
                    onClick = navController::navigateToStopList,
                    icon = { Icon(Icons.Default.Lock, contentDescription = null) },
                    label = { Text(stringResource(R.string.item_stop_list)) },
                )
            }
            RestaurantCookNavHost(navController, Modifier.padding(it))
        }
    }
}