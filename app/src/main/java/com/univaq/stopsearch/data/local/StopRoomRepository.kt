package com.univaq.stopsearch.data.local

import com.univaq.stopsearch.data.local.dao.StopDao
import com.univaq.stopsearch.data.local.dao.TripTimeDao
import com.univaq.stopsearch.data.local.entityModel.LocalStop
import com.univaq.stopsearch.data.local.entityModel.LocalTripTime
import com.univaq.stopsearch.domain.model.Stop
import com.univaq.stopsearch.domain.model.TripTime
import com.univaq.stopsearch.domain.repository.StopLocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

fun Stop.toLocal(): LocalStop = LocalStop(
    locationType = this.locationType,
    parentStation = this.parentStation,
    id = this.id,
    desc = this.desc,
    code = this.code,
    lat = this.lat,
    lon = this.lon,
    name = this.name,
    timezone = this.timezone,
    url = this.url,
    zoneId = this.zoneId
)

fun LocalStop.toModel(): Stop = Stop(
    locationType = this.locationType,
    parentStation = this.parentStation,
    id = this.id,
    desc = this.desc,
    code = this.code,
    lat = this.lat,
    lon = this.lon,
    name = this.name,
    timezone = this.timezone,
    url = this.url,
    zoneId = this.zoneId
)

fun TripTime.toLocal(): LocalTripTime = LocalTripTime(
    arrival_time = this.arrivalTime,
    departure_time = this.departureTime,
    route_id = this.routeId,
    route_long_name = this.routeLongName,
    route_short_name = this.routeShortName,
    route_type = this.routeType,
    service_id = this.serviceId,
    stop_id = this.stopId,
    stop_sequence = this.stopSequence,
    trip_id = this.tripId,
)

fun LocalTripTime.toModel(): TripTime = TripTime(
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


class StopRoomRepository @Inject constructor(
    private val stopDao: StopDao,
    private val tripTimeDao: TripTimeDao
): StopLocalRepository {

    /* STOP DATABASE */
    override suspend fun insertStop(stop: Stop) {
        stopDao.insertStop(stop.toLocal())
    }
    override suspend fun insertStops(stops: List<Stop>) {
        stopDao.insertStops(stops.map(Stop::toLocal))
    }
    override fun getAllStops(): Flow<List<Stop>> {
        return stopDao.getAllStops()
            .map { list ->
                list.map(LocalStop::toModel)
        }
    }
    override suspend fun deleteAllStops() {
        stopDao.deleteAllStops()
    }

    /* TRIP TIME DATABASE */
    override suspend fun insertTripTime(tripTime: TripTime) {
        tripTimeDao.insertTripTime(tripTime.toLocal())
    }

    override suspend fun insertTripTime(tripTimes: List<TripTime>) {
        tripTimeDao.insertTripTimes(tripTimes.map(TripTime::toLocal))
    }

    override fun getAllTrips(): Flow<List<TripTime>> {
        return tripTimeDao.getAllTrips()
            .map { list ->
                list.map(LocalTripTime::toModel)
            }
    }

    override suspend fun deleteAllTrips() {
        tripTimeDao.deleteAllTrips()
    }

    override fun getTripAtStop(stopId: Int, time: String): Flow<List<TripTime>> {
        return tripTimeDao.getTripAtStop(stopId, time)
            .map { list ->
                list.map(LocalTripTime::toModel)
            }
    }


}