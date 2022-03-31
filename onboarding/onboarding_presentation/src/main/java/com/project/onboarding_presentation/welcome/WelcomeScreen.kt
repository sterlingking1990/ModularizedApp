package com.project.onboarding_presentation.welcome

import android.graphics.Paint
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.project.core.R
import com.project.core.navigation.Route
import com.project.core.util.UiEvent
import com.project.core_ui.LocalSpacing
import com.project.onboarding_presentation.components.ActionButton

@Composable
fun WelcomeScreen(
    onNavigate: (UiEvent.Navigate) -> Unit
){
    val spacing = LocalSpacing.current
     Column(
         modifier = Modifier.fillMaxSize()
             .padding(spacing.spaceMedium),
         verticalArrangement = Arrangement.Center,
         horizontalAlignment = Alignment.CenterHorizontally) {
         
         Text(
             text = stringResource(id = R.string.welcome_text), 
             textAlign = TextAlign.Center,
             style = MaterialTheme.typography.h1, color = MaterialTheme.colors.onBackground)
         
         Spacer(modifier = Modifier.height(spacing.spaceMedium))
         ActionButton(
             text = stringResource(id = R.string.next),
             onClick = { onNavigate(UiEvent.Navigate(Route.GENDER)) },
             modifier = Modifier.align(Alignment.CenterHorizontally))
         
     }
}