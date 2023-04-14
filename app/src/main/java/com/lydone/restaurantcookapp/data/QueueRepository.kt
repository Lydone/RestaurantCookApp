package com.lydone.restaurantcookapp.data

import javax.inject.Inject

class QueueRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getEntries() = apiService.getDishQueueEntries()

    suspend fun updateEntryStatus(id: Int) = apiService.updateEntryStatus(id)
}