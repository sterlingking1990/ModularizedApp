package com.project.tracker_domain.usecases

import com.project.tracker_domain.model.TrackedFood
import com.project.tracker_domain.repository.TrackerRepository

class DeleteTrackedFood(
    private val repository: TrackerRepository
) {

    suspend operator fun invoke(trackedFood: TrackedFood){
        return repository.deleteTrackedFood(trackedFood)
    }
}