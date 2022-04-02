package com.project.tracker_data.repository

import com.project.tracker_data.local.TrackerAccessObject
import com.project.tracker_data.local.TrackerFoodDBService
import com.project.tracker_data.mapper.toTrackableFood
import com.project.tracker_data.mapper.toTrackedFood
import com.project.tracker_data.mapper.toTrackedFoodEntity
import com.project.tracker_data.remote.FoodAPI
import com.project.tracker_domain.model.TrackableFood
import com.project.tracker_domain.model.TrackedFood
import com.project.tracker_domain.repository.TrackerRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

class TrackerRepositoryImpl(
    private val foodAPI: FoodAPI,
    private val trackerAccessObject: TrackerAccessObject
): TrackerRepository {
    override suspend fun searchFood(
        query: String,
        page: Int,
        pageSize: Int
    ): Result<List<TrackableFood>> {

        return try {
            val searchDto = foodAPI.searchFood(query,page,pageSize)
            Result.success(searchDto.products.mapNotNull {
                it.toTrackableFood()
            })
        }catch (e:Exception){
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun insertTrackedFood(food: TrackedFood) {
        trackerAccessObject.insertTrackedFood(food.toTrackedFoodEntity())
    }

    override suspend fun deleteTrackedFood(food: TrackedFood) {
        return trackerAccessObject.deleteTrackedFood(food.toTrackedFoodEntity())
    }

    override fun getFoodsForDate(localDate: LocalDate): Flow<List<TrackedFood>> {
        return trackerAccessObject.getFoodsForDate(localDate.dayOfMonth,localDate.monthValue,localDate.year).map {
            it-> it.map {
                it.toTrackedFood()
        }
        }
    }

}