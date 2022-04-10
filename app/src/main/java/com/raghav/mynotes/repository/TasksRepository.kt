package com.raghav.mynotes.repository

import com.raghav.mynotes.models.TaskEntity
import kotlinx.coroutines.flow.Flow

interface TasksRepository {
    suspend fun saveTask(task: TaskEntity)

    fun getAllTasks(): Flow<List<TaskEntity>>

    suspend fun deleteTask(task: TaskEntity)
}