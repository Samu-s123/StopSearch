package com.univaq.stopsearch.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.univaq.stopsearch.data.local.entityModel.LocalStop
import kotlinx.coroutines.flow.Flow

@Dao
interface StopDao {

    @Upsert
    suspend fun insertStop(stop: LocalStop)

    @Upsert
    suspend fun insertStops(stops: List<LocalStop>)

    @Query("SELECT * FROM Stops ORDER BY name")
    fun getAllStops(): Flow<List<LocalStop>>

    @Query("DELETE FROM Stops")
    suspend fun deleteAllStops()

    @Query("SELECT * FROM Stops WHERE name LIKE :stopFiltered ")
    fun getStopsByName(stopFiltered : String): Flow<List<LocalStop>>
}