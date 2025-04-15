package com.univaq.stopsearch.domain.repository

import com.univaq.stopsearch.domain.model.Stop
import com.univaq.stopsearch.domain.model.TripTime

interface StopRemoteRepository {

    suspend fun getStop(): List<Stop>

    suspend fun getTripTime(): List<TripTime>

}