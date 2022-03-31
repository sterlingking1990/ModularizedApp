package com.project.tracker_domain.usecases

import com.project.tracker_domain.model.TrackedFood
import com.project.tracker_domain.repository.TrackerRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class GetFoodsForDate(
    private val repository: TrackerRepository
) {

    operator fun invoke(
        localDate: LocalDate
    ): Flow<List<TrackedFood>> {
        return repository.getFoodsForDate(localDate)
    }
}