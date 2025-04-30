package com.univaq.stopsearch.ui.screen.list

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.materialIcon
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.univaq.stopsearch.DetailActivity
import com.univaq.stopsearch.domain.model.Stop
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun ListScreen (
    modifier: Modifier = Modifier,
    viewModel: ListViewModel = hiltViewModel()
){
    val uiState = viewModel.uiState

    val context = LocalContext.current

    val refreshing = rememberPullToRefreshState()
    var isRefreshing by remember{ mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    PullToRefreshBox(
        state = refreshing,
        isRefreshing = isRefreshing,
        onRefresh = {
            coroutineScope.launch {
                isRefreshing = true
                viewModel.refresh()
                delay(2.seconds)
                isRefreshing = false
            }
        },
        modifier = Modifier,
    ) {

    if (uiState.loadingMsg != null){
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center,
        ){
            Text(text = uiState.loadingMsg)
        }

    } else if (uiState.error != null){
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center,
        ){
            Text(text = uiState.error)
        }

    }else {
        Column(
            modifier = modifier
        ) {

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
            ) {
                items(uiState.stops.size) { index ->
                    val stop = uiState.stops[index]
                    StopItem(
                        modifier = Modifier.fillMaxWidth(),
                        stop = stop,
                        onItemClick = {
                            context.startActivity(
                                Intent(context, DetailActivity::class.java)
                                    .also {
                                        it.putExtra("name", stop.name)
                                        it.putExtra("stop_id", stop.id)
                                    }
                            )
                        }
                    )
                }
            }
        }
    }
    }
}

@Composable
fun StopItem (
    modifier: Modifier,
    stop: Stop,
    onItemClick: (Stop) -> Unit = {}
){
    Row (
        modifier = modifier
            .padding(16.dp)
            .clickable { onItemClick(stop) }
    ){
        Icon(imageVector = Icons.Default.LocationOn, contentDescription = "")
        Text(text = "   ${stop.name}")
    }
}