package com.project.onboarding_presentation.nutrient_goal

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.project.core.R
import com.project.core.util.UiEvent
import com.project.core_ui.LocalSpacing
import com.project.onboarding_domain.NutritionGoalActionValueHolder
import com.project.onboarding_domain.NutritionGoalTypeValueHolder
import com.project.onboarding_presentation.components.ActionButton
import com.project.onboarding_presentation.components.UnitTextField
import kotlinx.coroutines.flow.collect


@Composable
fun NutritionGoalScreen(
    scaffoldState: ScaffoldState,
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: NutritionGoalViewModel = hiltViewModel()
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
                text = stringResource(id = R.string.what_are_your_nutrient_goals),
                style = MaterialTheme.typography.h3
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            UnitTextField(value = viewModel.nutritionGoalType.protein,
                unit = stringResource(id = R.string.kg),
                onValueChange = {viewModel.processNutritionGoal(nutritionGoalValue = NutritionGoalTypeValueHolder.ProteinValue(it))})

            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            UnitTextField(value = viewModel.nutritionGoalType.carb,
                unit = stringResource(id = R.string.kg),
                onValueChange = {viewModel.processNutritionGoal(nutritionGoalValue = NutritionGoalTypeValueHolder.CarbValue(it))})

            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            UnitTextField(value = viewModel.nutritionGoalType.fat,
                unit = stringResource(id = R.string.kg),
                onValueChange = {viewModel.processNutritionGoal(nutritionGoalValue = NutritionGoalTypeValueHolder.FatValue(it))})
        }
        ActionButton(
            text = stringResource(id = R.string.next),
            onClick = {viewModel.processNutritionScreenAction(nutritionGoalActionValueHolder = NutritionGoalActionValueHolder.OnNextClick)},
            modifier = Modifier.align(Alignment.BottomEnd)
        )
    }
}