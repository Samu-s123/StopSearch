package com.univaq.stopsearch.ui.screen.list

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.univaq.stopsearch.common.Resource
import com.univaq.stopsearch.domain.model.Stop
import com.univaq.stopsearch.domain.use_case.GetStopsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ListUIState(
    val stops: List<Stop> = emptyList(),
    val loadingMsg: String? = null,
    val error: String? = null,
    val stopFiltered: String? = null
)

sealed class ListEvent {
    data class OnFiltering(val stopFiltered: String) : ListEvent()
}

@HiltViewModel
class ListViewModel @Inject constructor(
    private val getStopUseCase: GetStopsUseCase
): ViewModel() {
    var uiState by mutableStateOf(ListUIState())
        private set

    init {
        downloadStops()
    }

    fun refresh() : Unit {
        downloadStops()
    }

    fun onEvent(event: ListEvent){
        when(event) {
            is ListEvent.OnFiltering -> {
                val stopFiltered = event.stopFiltered
                uiState = uiState.copy(stopFiltered = stopFiltered)
                downloadStops(stopFiltered)
            }
        }
    }


    private fun downloadStops(filteredStops :String? = null){
        viewModelScope.launch{
            getStopUseCase().collect { resource ->
                when(resource){
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