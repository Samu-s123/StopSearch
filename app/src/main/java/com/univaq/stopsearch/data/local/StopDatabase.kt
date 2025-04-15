package com.univaq.stopsearch.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.univaq.stopsearch.data.local.dao.StopDao
import com.univaq.stopsearch.data.local.dao.TripTimeDao
import com.univaq.stopsearch.data.local.entityModel.LocalStop
import com.univaq.stopsearch.data.local.entityModel.LocalTripTime

@Database(entities = [LocalStop::class, LocalTripTime::class], version = 3, exportSchema = false)
abstract class StopDatabase: RoomDatabase() {

    abstract fun getStopDao(): StopDao
    abstract fun getTripTimeDao(): TripTimeDao
}