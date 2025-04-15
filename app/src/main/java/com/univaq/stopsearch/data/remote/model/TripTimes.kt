package com.univaq.stopsearch.data.remote.model

data class TripTimes(
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