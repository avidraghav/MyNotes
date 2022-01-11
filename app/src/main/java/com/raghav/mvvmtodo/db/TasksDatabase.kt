package com.raghav.mvvmtodo.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.raghav.mvvmtodo.models.TaskEntity

@Database(entities = [TaskEntity::class], version = 1)
abstract class TasksDatabase : RoomDatabase() {

    abstract fun dao(): TasksDao

    companion object {
        private var INSTANCE: TasksDatabase? = null

        fun getDatabase(context: Context): TasksDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TasksDatabase::class.java,
                    "tasks.db"
                ).build()
                INSTANCE = instance
                instance
            }

        }
    }
}