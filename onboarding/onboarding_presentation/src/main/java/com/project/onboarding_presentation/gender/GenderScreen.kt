package com.project.onboarding_presentation.gender

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.project.core.util.UiEvent
import com.project.core_ui.LocalSpacing
import kotlinx.coroutines.flow.collect
import com.project.core.R
import com.project.core.domain.model.sealed.Gender
import com.project.onboarding_presentation.components.ActionButton
import com.project.onboarding_presentation.components.SelectableButton
import dagger.hilt.android.AndroidEntryPoint

@Composable
fun GenderScreen(
    onNavigate:(UiEvent.Navigate)->Unit,
    viewModel: GenderViewModel = hiltViewModel()
){
    val spacing = LocalSpacing.current
    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect {
            when(it){
                is UiEvent.Navigate -> onNavigate(it)
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
               text = stringResource(id = R.string.whats_your_gender),
               style = MaterialTheme.typography.h3
           )
           Spacer(modifier = Modifier.height(spacing.spaceMedium))
           Row() {
               SelectableButton(
                   text = stringResource(id = R.string.male),
                   isSelected = viewModel.selectedGender is Gender.Male,
                   color = MaterialTheme.colors.primaryVariant,
                   selectedTextColor = Color.White,
                   onClick = {
                       viewModel.onGenderClick(Gender.Male)
                   },
                   textStyle = MaterialTheme.typography.button.copy(
                       fontWeight = FontWeight.Normal
                   )
               )
               Spacer(modifier = Modifier.width(spacing.spaceMedium))
               SelectableButton(
                   text = stringResource(id = R.string.female),
                   isSelected = viewModel.selectedGender is Gender.Female,
                   color = MaterialTheme.colors.primaryVariant,
                   selectedTextColor = Color.White,
                   onClick = {
                       viewModel.onGenderClick(Gender.Female)
                   },
                   textStyle = MaterialTheme.typography.button.copy(
                       fontWeight = FontWeight.Normal
                   )
               )
           }
       }
       ActionButton(
           text = stringResource(id = R.string.next),
           onClick = viewModel::onNextClick,
           modifier = Modifier.align(Alignment.BottomEnd)
       )
    }

}