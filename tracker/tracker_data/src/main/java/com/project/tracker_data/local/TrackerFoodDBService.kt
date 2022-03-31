package com.project.tracker_data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [TrackerFoodEntity::class], version = 1
)
abstract class TrackerFoodDBService:RoomDatabase() {

    abstract val trackerAccessObject: TrackerAccessObject
}