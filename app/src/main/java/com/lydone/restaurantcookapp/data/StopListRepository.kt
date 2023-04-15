package com.lydone.restaurantcookapp.data

import javax.inject.Inject

class StopListRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun addToStopList(dish: Int) = apiService.addToStopList(dish)

    suspend fun removeFromStopList(dish: Int) = apiService.removeFromStopList(dish)

    suspend fun getDishes() = apiService.getDishes()
}