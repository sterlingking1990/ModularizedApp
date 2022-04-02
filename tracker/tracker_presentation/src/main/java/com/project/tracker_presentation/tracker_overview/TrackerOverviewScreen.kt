package com.project.tracker_presentation.tracker_overview

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.project.core.util.UiEvent
import com.project.core_ui.LocalSpacing
import com.project.tracker_presentation.tracker_overview.component.NutrientsHeader

@Composable
fun TrackerOverviewScreen(
    onNavigate: (UiEvent.Navigate)->Unit,
    viewModel: TrackerOverviewViewModel = hiltViewModel()
){
    val spacing = LocalSpacing.current
    val state = viewModel.trackerState
    val context = LocalContext.current
    
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = spacing.spaceMedium)
    ){
        item { 
            NutrientsHeader(state = state)
        }
    }
}