package com.lydone.restaurantcookapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.lydone.restaurantcookapp.ui.queue.QueueRoute

private const val ROUTE_QUEUE = "queue"
private const val ROUTE_STOP_LIST = "stop_list"

@Composable
fun RestaurantCookNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController, ROUTE_QUEUE, modifier) {
        composable(ROUTE_QUEUE) { QueueRoute() }
        composable(ROUTE_STOP_LIST) { }
    }
}

fun NavController.navigateToQueue() = navigate(ROUTE_QUEUE)
fun NavController.navigateToStopList() = navigate(ROUTE_STOP_LIST)
fun NavDestination?.isQueue() = this?.route == ROUTE_QUEUE
fun NavDestination?.isStopList() = this?.route == ROUTE_STOP_LIST

