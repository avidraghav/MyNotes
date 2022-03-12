package com.raghav.mynotes.db

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.raghav.mynotes.models.TaskEntity

@Database(
    entities = [TaskEntity::class],
    version = 3,
    autoMigrations = [AutoMigration(from = 1, to = 2)]
)
abstract class TasksDatabase : RoomDatabase() {

    abstract fun dao(): TasksDao

}