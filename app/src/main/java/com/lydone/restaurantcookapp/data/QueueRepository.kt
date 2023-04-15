package com.lydone.restaurantcookapp.data

import javax.inject.Inject

class QueueRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getActiveEntries() = apiService.getDishQueueEntries().asReversed().filterNot { it.status == Entry.Status.READY }

    suspend fun updateEntryStatus(id: Int) = apiService.updateEntryStatus(id)
}