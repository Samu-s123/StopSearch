package com.univaq.stopsearch.ui.screen.map

import android.content.Context
import android.location.Location
import androidx.collection.IntObjectMap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.MarkerState
import com.univaq.stopsearch.common.LocationHelper
import com.univaq.stopsearch.common.Resource
import com.univaq.stopsearch.data.remote.model.Stops
import com.univaq.stopsearch.domain.model.Stop
import com.univaq.stopsearch.domain.use_case.GetStopsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MapUiState (
    val stops: List<Stop> = emptyList(),
    val loadingMsg: String? = null,
    val error: String? = null,
    val markerState: MarkerState? = null,
    val cameraPositionState: CameraPositionState = CameraPositionState(
        position = CameraPosition(
            LatLng(0.0,0.0),
            10f,
            0f,
            0f
        )
    ),
    val filteredStops: List<Stop> = emptyList()
)

sealed class MapEvent {
    data object StartLocation: MapEvent()
    data object StopLocation: MapEvent()
}

@HiltViewModel
class MapViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getStopsUseCase: GetStopsUseCase
): ViewModel() {

    private val locationHelper = LocationHelper(context = context){location->
        val markerState = MarkerState(
            position = LatLng(location.latitude, location.longitude)
        )

        val cameraPosition = CameraPosition(
            LatLng(location.latitude, location.longitude),
            13f,
            0f,
            0f
        )

        val filteredStops = uiState.stops.filter {
            val stopLocation = Location("stop")
                .apply {
                    latitude = it.lat.toDouble()
                    longitude = it.lon.toDouble()
                }
            location.distanceTo(stopLocation) <= 100
        }

        uiState = uiState.copy(
            markerState = markerState,
            filteredStops = filteredStops
        )
    }

    var uiState by mutableStateOf(MapUiState())
        private set

    init {
        getStops()
    }

    fun onEvent(event: MapEvent){
        when(event) {
            is MapEvent.StartLocation -> {
                locationHelper.start()
            }
            is MapEvent.StopLocation -> {
                locationHelper.stop()
            }
        }
    }

    private fun getStops() {
        viewModelScope.launch {
            getStopsUseCase().collect { resource ->
                when(resource) {
                    is Resource.Loading<*> -> {
                        uiState = uiState.copy(
                            loadingMsg = resource.message,
                            error = null
                        )
                    }

                    is Resource.Success<*> -> {
                        uiState = uiState.copy(
                            stops = resource.data as List<Stop>,
                            loadingMsg = null,
                            error = null
                        )
                    }

                    is Resource.Error<*> -> {
                        uiState = uiState.copy(
                            loadingMsg = null,
                            error = resource.message
                        )
                    }
                }
            }
        }
    }
}