package com.univaq.stopsearch.domain.model

data class Stop(
    val locationType: String,
    val parentStation: String,
    val id: String,
    val desc: String,
    val code: String,
    val lat: String,
    val lon: String,
    val name: String,
    val timezone: String,
    val url: String,
    val zoneId: String
)
