package com.univaq.stopsearch.ui.navigation

import kotlinx.serialization.Serializable

sealed class Screen (){
    @Serializable
    data object List: Screen()
    @Serializable
    data object Map: Screen()
}