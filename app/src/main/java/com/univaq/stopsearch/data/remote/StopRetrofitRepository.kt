package com.univaq.stopsearch.data.remote

import com.univaq.stopsearch.data.remote.model.Stops
import com.univaq.stopsearch.data.remote.model.TripTimes
import com.univaq.stopsearch.domain.repository.StopRemoteRepository
import com.univaq.stopsearch.domain.model.Stop
import com.univaq.stopsearch.domain.model.TripTime
import javax.inject.Inject

fun Stops.toModel(): Stop = Stop(
    locationType = this.location_type,
    parentStation = this.parent_station,
    id = this.stop_id,
    desc = this.stop_desc,
    code = this.stop_code,
    lat = this.stop_lat,
    lon = this.stop_lon,
    name = this.stop_name,
    timezone = this.stop_timezone,
    url = this.stop_url,
    zoneId = this.zone_id
)

fun TripTimes.toModel(): TripTime = TripTime(
    arrivalTime = arrival_time,
    departureTime = departure_time,
    routeId = route_id,
    routeLongName = route_long_name,
    routeShortName = route_short_name,
    routeType = route_type,
    serviceId = service_id,
    stopId = stop_id,
    stopSequence = stop_sequence,
    tripId = trip_id
)

class StopRetrofitRepository @Inject constructor(
    private val stopService: StopService
): StopRemoteRepository {


    override suspend fun getStop(): List<Stop> {
        return stopService.downloadData().map(Stops::toModel)
    }

    override suspend fun getTripTime(): List<TripTime> {
        return stopService.downloadTime().map(TripTimes::toModel)
    }

}
