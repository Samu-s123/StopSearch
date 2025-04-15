package com.univaq.stopsearch.data.local.entityModel

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(
    tableName = "TripTime",
    primaryKeys = ["trip_id", "stop_sequence"]
)
data class LocalTripTime (
    val arrival_time: String,
    val departure_time: String,
    val route_id: Int,
    val route_long_name: String,
    val route_short_name: String,
    val route_type: Int,
    val service_id: Int,
    val stop_id: Int,

    val stop_sequence: Int,
    val trip_id: Int
)