package com.univaq.stopsearch.ui.screen.map

import android.content.Intent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCompositionContext
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.PermissionChecker
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberMarkerState
import com.univaq.stopsearch.DetailActivity
import com.univaq.stopsearch.ui.tools.LifecycleEvent
import com.univaq.stopsearch.ui.tools.LocationPermission
import com.univaq.stopsearch.ui.tools.PermissionChecker

@Composable
fun MapScreen (
    modifier: Modifier = Modifier,
    viewModel: MapViewModel = hiltViewModel()
){
    val uiState = viewModel.uiState
    val context = LocalContext.current

    PermissionChecker(
        permission = LocationPermission(),
        events = listOf(
            LifecycleEvent(event = Lifecycle.Event.ON_RESUME){
                viewModel.onEvent(MapEvent.StartLocation)
            },
            LifecycleEvent(event = Lifecycle.Event.ON_PAUSE){
                viewModel.onEvent(MapEvent.StopLocation)
            }
        )
    ) {

        GoogleMap(
            modifier = modifier,
            cameraPositionState = uiState.cameraPositionState
        ) {
            uiState.markerState?.let {
                Marker(
                    state = it,
                    title = "Current Location",
                    snippet = "Your Current Location",
                    icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE),
                )
            }

            uiState.filteredStops.forEach { stop ->

                Marker(
                    title = stop.id,
                    snippet = "${stop.name}",
                    state = rememberMarkerState(
                        position = LatLng(
                            stop.lat.toDouble(),
                            stop.lon.toDouble()
                        )
                    ),
                    onInfoWindowClick = {
                        context.startActivity(Intent(context, DetailActivity::class.java)
                            .apply {
                                putExtra("name", stop.name)
                                putExtra("stop_id", stop.id)
                            }
                        )

                    }
                )
            }
        }
    }
}