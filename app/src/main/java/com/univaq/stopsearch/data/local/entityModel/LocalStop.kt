package com.univaq.stopsearch.data.local.entityModel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Stops")
data class LocalStop(
    @PrimaryKey
    val id: String,
    //The annotation is for rename the column with camel case notation to snake case notation
    @ColumnInfo(name = "location_type")
    val locationType: String,

    @ColumnInfo(name = "parent_station")
    val parentStation: String,

    val desc: String,
    val code: String,
    val lat: String,
    val lon: String,
    val name: String,
    val timezone: String,
    val url: String,

    @ColumnInfo(name = "zone_id")
    val zoneId: String
)