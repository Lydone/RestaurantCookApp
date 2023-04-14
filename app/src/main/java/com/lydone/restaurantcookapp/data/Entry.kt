package com.lydone.restaurantcookapp.data

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Entry(
    @SerialName("id") val id: Int,
    @SerialName("dish") val dish: Dish,
    @SerialName("status") val status: Status,
    @SerialName("comment") val comment: String?,
    @SerialName("instant") val instant: Instant,
    @SerialName("table") val table: Int,
) {

    @Serializable
    enum class Status {
        @SerialName("QUEUE")
        QUEUE,

        @SerialName("COOKING")
        COOKING,

        @SerialName("READY")
        READY
    }
}