package com.project.tracker_presentation.tracker_overview

import com.project.tracker_domain.model.TrackedFood

sealed class TrackerOverviewEvent {
    object OnNextDayClick: TrackerOverviewEvent()
    object OnPreviousDayClick: TrackerOverviewEvent()
    data class OnToggleMealClick(val meal: Meal): TrackerOverviewEvent()
    data class OnDeleteTrackFoodClicked(val trackedFood: TrackedFood): TrackerOverviewEvent()
    data class OnAddFood(val meal:Meal): TrackerOverviewEvent()

}

