package com.univaq.stopsearch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.univaq.stopsearch.ui.screen.detail.DetailScreen
import com.univaq.stopsearch.ui.theme.StopAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StopAppTheme {

                val stop_id = intent.getStringExtra("stop_id")?: ""

                DetailScreen(
                    stopId = stop_id
                )
            }
        }


    }

}