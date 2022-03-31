package com.project.tracker_data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface TrackerAccessObject {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrackedFood(trackerFoodEntity: TrackerFoodEntity)

    @Delete
    suspend fun deleteTrackedFood(trackerFoodEntity: TrackerFoodEntity)

    @Query(
        """
            SELECT * FROM trackerfoodentity WHERE dayOfMonth = :day AND month = :month AND year = :year
        """
    )
    fun getFoodsForDate(day:Int, month:Int, year:Int): Flow<List<TrackerFoodEntity>>
}