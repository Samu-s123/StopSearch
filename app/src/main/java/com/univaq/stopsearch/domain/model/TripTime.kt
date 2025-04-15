package com.univaq.stopsearch.domain.model

data class TripTime (
    val arrivalTime: String,
    val departureTime: String,
    val routeId: Int,
    val routeLongName: String,
    val routeShortName: String,
    val routeType: Int,
    val serviceId: Int,
    val stopId: Int,
    val stopSequence: Int,
    val tripId: Int
)