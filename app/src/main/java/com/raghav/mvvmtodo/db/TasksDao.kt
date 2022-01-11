package com.raghav.mvvmtodo.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.raghav.mvvmtodo.models.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TasksDao {

    @Insert(onConflict = REPLACE)
    suspend fun saveTask(task: TaskEntity)

    @Query("SELECT * from tasks")
    fun getTasks(): Flow<List<TaskEntity>>
}