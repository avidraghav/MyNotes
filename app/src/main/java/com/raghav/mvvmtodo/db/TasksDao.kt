package com.raghav.mvvmtodo.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.raghav.mvvmtodo.models.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TasksDao {

    @Insert()
    suspend fun saveTask(task: TaskEntity)

    @Query("SELECT * from tasks")
    suspend fun getTasks(): Flow<TaskEntity>
}