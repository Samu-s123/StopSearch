package com.univaq.stopsearch.ui.screen.detail

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import com.univaq.stopsearch.domain.model.TripTime
import network.chaintech.kmp_date_time_picker.ui.timepicker.WheelTimePickerView
import network.chaintech.kmp_date_time_picker.utils.DateTimePickerView


@SuppressLint("ContextCastToActivity")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen (
    modifier: Modifier = Modifier,
    stopId: String = "",
    viewModel: DetailViewModel = hiltViewModel()
){
    val activity = LocalContext.current as Activity

    val uiState = viewModel.uiState

    var showTimePicker by remember { mutableStateOf(false) }

    var hasLaunched by remember { mutableStateOf(false) }


    if (uiState.loadingMsg != null){
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center,
        ){
            Text(text = uiState.loadingMsg)
        }
        return
    }

    if (uiState.error != null){
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center,
        ){
            Text(text = uiState.error)
        }
        return
    }

    LifecycleEventEffect(Lifecycle.Event.ON_CREATE) {
        if (!hasLaunched) {
            println("gay")
            viewModel.onEvent(
                DetailEvent.OnStopSelected(
                    stopId = stopId
                )
            )
            hasLaunched = !hasLaunched
        }
    }

    WheelTimePickerView(
        height = 200.dp,
        showTimePicker = showTimePicker,
        dateTimePickerView = DateTimePickerView.DIALOG_VIEW,
        rowCount = 3,
        onDismiss = {},
        onDoneClick = {
            showTimePicker = false
            val timeSelected: String = "${it.toString()}:00"
            viewModel.onEvent(DetailEvent.OnTimeSelected(
                time = timeSelected
                )
            )
        },
        titleStyle = TextStyle(
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            color = MaterialTheme.colorScheme.primary
        ),
        doneLabelStyle = TextStyle(
            fontSize = MaterialTheme.typography.titleMedium.fontSize,
            color = MaterialTheme.colorScheme.primary
        ),
        title = "Select An Hour"
    )

    Scaffold (
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = "Detail")},
                navigationIcon = {
                    IconButton(onClick = {
                        activity.finish()
                    }) {
                        Icon(contentDescription = "Back",
                            imageVector = Icons.AutoMirrored.Default.ArrowBack
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showTimePicker = true },
            ) {
                Icon(imageVector = Icons.Filled.DateRange, contentDescription = "Filter by hour")
            }
        }
    ){padding ->
        if (uiState.tripTimes.isEmpty()){
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ){
                Text(text = "No Trip found")
            }
            return@Scaffold
        }
        Column {

            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
            ) {
                items(uiState.tripTimes.size) {
                    BusItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        tripTime = uiState.tripTimes[it]
                    )
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun BusItem(
    modifier: Modifier = Modifier,
    tripTime: TripTime = TripTime(
        arrivalTime = "15:30:00",
        departureTime = "15:30:00",
        routeId = 1,
        routeLongName = "LINEA 13 TERMINALBUS COLLEMAGGIO PRETURO",
        routeShortName = "13",
        routeType = 3,
        serviceId = 3,
        stopId = 3,
        stopSequence = 1,
        tripId = 1
    )
){
    Card(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .padding(16.dp)
        ) {
            Row {
                Text(
                    modifier = Modifier
                        .padding(16.dp)
                        .background(color = Color(0xFF91FFFF), shape = RoundedCornerShape(16.dp))
                        .padding(24.dp),
                    text = tripTime.routeShortName,
                    fontSize = 16.sp
                )
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Text(
                        modifier = Modifier
                            .padding(16.dp),
                        text = tripTime.routeLongName,
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                        fontStyle = MaterialTheme.typography.bodyLarge.fontStyle,
                    )
                    Row {
                        Text(
                            modifier = Modifier
                                .padding(horizontal = 16.dp),
                            text = tripTime.departureTime.substring(0, 5),
                            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                        )
                        Text(
                            modifier = Modifier
                                .padding(horizontal = 16.dp),
                            text = if (tripTime.departureTime == tripTime.arrivalTime) "(Fermata)" else "(Capolinea)",
                            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                        )
                    }
                }
            }
        }
    }
}
