package com.raghav.mynotes.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.raghav.mynotes.models.TaskEntity

@Database(entities = [TaskEntity::class], version = 1)
abstract class TasksDatabase : RoomDatabase() {

    abstract fun dao(): TasksDao

}