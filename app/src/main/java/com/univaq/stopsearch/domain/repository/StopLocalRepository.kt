package com.univaq.stopsearch.domain.repository

import com.univaq.stopsearch.domain.model.Stop
import com.univaq.stopsearch.domain.model.TripTime
import kotlinx.coroutines.flow.Flow


interface StopLocalRepository {

    suspend fun insertStop(stop: Stop)

    suspend fun insertStops(stops: List<Stop>)

    fun getAllStops(): Flow<List<Stop>>

    fun getStopsByName(stopFiltered : String): Flow<List<Stop>>

    suspend fun deleteAllStops()


    suspend fun insertTripTime(tripTime: TripTime)

    suspend fun insertTripTime(tripTimes: List<TripTime>)

    fun getAllTrips(): Flow<List<TripTime>>

    suspend fun deleteAllTrips()

    fun getTripAtStop(stopId: Int, time: String): Flow<List<TripTime>>

}