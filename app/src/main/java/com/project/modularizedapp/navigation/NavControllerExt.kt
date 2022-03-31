package com.project.modularizedapp.navigation

import androidx.navigation.NavController
import com.project.core.util.UiEvent

fun NavController.navigate(event:UiEvent.Navigate){
    this.navigate(event.route)
}