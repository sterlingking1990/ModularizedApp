package com.project.onboarding_presentation.height

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.project.core.R
import com.project.core.domain.model.sealed.Gender
import com.project.onboarding_presentation.height.HeightViewModel
import com.project.core.util.UiEvent
import com.project.core_ui.LocalSpacing
import com.project.onboarding_presentation.components.ActionButton
import com.project.onboarding_presentation.components.SelectableButton
import com.project.onboarding_presentation.components.UnitTextField
import kotlinx.coroutines.flow.collect

@Composable
fun HeightScreen(
    scaffoldState: ScaffoldState,
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: HeightViewModel = hiltViewModel()
){
    val spacing = LocalSpacing.current
    val context = LocalContext.current

    //accessing the data stored in uiEvent from view model
    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect {
            when(it){
                is UiEvent.Navigate -> onNavigate(it)
                is UiEvent.showSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = it.message.asString(context = context))
                }
                else -> Unit
            }
        }
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(spacing.spaceLarge)
    ) {
        Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.whats_your_height),
                style = MaterialTheme.typography.h3
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            UnitTextField(value = viewModel.enteredHeight,
                unit = stringResource(id = R.string.cm),
                onValueChange = viewModel::onHeightEntered)
        }
        ActionButton(
            text = stringResource(id = R.string.next),
            onClick = viewModel::onNextClick,
            modifier = Modifier.align(Alignment.BottomEnd)
        )
    }
}