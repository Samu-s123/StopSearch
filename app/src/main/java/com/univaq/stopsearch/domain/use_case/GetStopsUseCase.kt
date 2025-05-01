package com.univaq.stopsearch.domain.use_case

import android.util.Log
import com.univaq.stopsearch.common.Resource
import com.univaq.stopsearch.domain.model.Stop
import com.univaq.stopsearch.domain.model.TripTime
import com.univaq.stopsearch.domain.repository.StopLocalRepository
import com.univaq.stopsearch.domain.repository.StopRemoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class GetStopsUseCase @Inject constructor(
    private val stopRemoteRepository: StopRemoteRepository,
    private val stopLocalRepository: StopLocalRepository
) {


    operator fun invoke(): Flow<Resource<List<Stop>>> {
        return flow {
            emit(Resource.Loading("Loading... "))

            stopLocalRepository.getAllStops()
                .catch {
                    emit(Resource.Error("Error fetching stops from local database"))
                }
                .collect { list ->

                    if (list.isEmpty()) {
                        try {
                            val data = stopRemoteRepository.getStop()
                            stopLocalRepository.insertStops(data)
                            emit(Resource.Success(data))
                            //data successfully fetched from remote database
                        } catch (e: retrofit2.HttpException) {
                            e.printStackTrace()
                            emit(Resource.Error("Error fetching stops from remote database"))
                        }
                    } else {
                        emit(Resource.Success(list))
                    }
                }

        }
    }
}
