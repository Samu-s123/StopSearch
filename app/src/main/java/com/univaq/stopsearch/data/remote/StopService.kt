package com.univaq.stopsearch.data.remote

import com.univaq.stopsearch.common.API_DATA
import com.univaq.stopsearch.common.API_DATA_TIME
import com.univaq.stopsearch.data.remote.model.Stops
import com.univaq.stopsearch.data.remote.model.TripTimes
import retrofit2.http.GET


interface StopService {

    @GET(API_DATA)
    suspend fun downloadData(): List<Stops>

    @GET(API_DATA_TIME)
    suspend fun downloadTime(): List<TripTimes>
}