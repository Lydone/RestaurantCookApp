package com.lydone.restaurantcookapp.data

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @GET("order/all_entries")
    suspend fun getDishQueueEntries(): List<Entry>

    @POST("order/update_entry_status/{entry}")
    suspend fun updateEntryStatus(@Path("entry") entry: Int)
}