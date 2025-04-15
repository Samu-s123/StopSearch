package com.univaq.stopsearch.ui.screen.detail

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.univaq.stopsearch.StopSearch_GeneratedInjector
import com.univaq.stopsearch.common.Resource
import com.univaq.stopsearch.domain.model.Stop
import com.univaq.stopsearch.domain.model.TripTime
import com.univaq.stopsearch.domain.repository.StopLocalRepository
import com.univaq.stopsearch.domain.use_case.GetStopsUseCase
import com.univaq.stopsearch.domain.use_case.GetTripTimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalTime
import network.chaintech.kmp_date_time_picker.utils.now
import javax.inject.Inject


data class DetailUIState(
    val tripTimes: List<TripTime> = emptyList(),
    val loadingMsg: String? = null,
    val error: String? = null,
    val stopId: Int? = null,
    val time: String? = null
)

sealed class DetailEvent {
    data class OnStopSelected(val stopId: String): DetailEvent()
    data class OnTimeSelected(val time: String): DetailEvent()
}


@HiltViewModel
class DetailViewModel @Inject constructor(
    private val localRepository: StopLocalRepository,
    private val getTripTimeUseCase: GetTripTimeUseCase
): ViewModel(){

    var uiState by mutableStateOf(DetailUIState())
        private set

    fun onEvent(event: DetailEvent) {
        when(event) {
            is DetailEvent.OnStopSelected -> {
                val id: Int = event.stopId.toIntOrNull() ?: return
                val time: String =  LocalTime.now().toString().substring(0, 8)
                uiState = uiState.copy(
                    stopId = id,
                    time = time
                )
                downloadTripTime(id, time)
            }

            is DetailEvent.OnTimeSelected -> {
                val id: Int = uiState.stopId ?: return
                val time: String = event.time
                uiState = uiState.copy(
                    stopId = id,
                    time = time
                )
                downloadTripTime(id, time)
            }
        }
    }

    private fun downloadTripTime(stopId: Int, time: String){
        viewModelScope.launch{
            getTripTimeUseCase(stopId, time).collect { resource ->
                when(resource){
                    is Resource.Loading<*> -> {
                        uiState = uiState.copy(
                            loadingMsg = resource.message,
                            error = null
                        )
                    }
                    is Resource.Success<*> -> {
                        uiState = uiState.copy(
                            tripTimes = resource.data as List<TripTime>,
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
    fun setStopId(stopId: Int){
        uiState = uiState.copy(stopId = stopId)
    }
}