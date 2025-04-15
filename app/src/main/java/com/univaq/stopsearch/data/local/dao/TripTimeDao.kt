package com.univaq.stopsearch.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.univaq.stopsearch.data.local.entityModel.LocalTripTime
import kotlinx.coroutines.flow.Flow


@Dao
interface TripTimeDao {
    @Upsert
    suspend fun insertTripTime(trip: LocalTripTime)

    @Upsert
    suspend fun insertTripTimes(trips: List<LocalTripTime>)

    @Query("SELECT * FROM TripTime")
    fun getAllTrips(): Flow<List<LocalTripTime>>

    @Query("DELETE FROM TripTime")
    suspend fun deleteAllTrips()

    @Query("SELECT * " +
            "FROM TripTime " +
            "WHERE stop_id = :stopId " +
            "AND departure_time > :time " +
            "AND arrival_time > :time  " +
            "GROUP BY trip_id, stop_sequence " +
            "ORDER BY departure_time, route_long_name")
    fun getTripAtStop(stopId: Int, time: String): Flow<List<LocalTripTime>>
}