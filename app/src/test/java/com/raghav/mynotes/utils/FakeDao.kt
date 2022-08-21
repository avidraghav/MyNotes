package com.raghav.mynotes.utils

import com.raghav.mynotes.db.TasksDao
import com.raghav.mynotes.models.TaskEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeDao : TasksDao {

    private val tasksList = mutableListOf(
        TaskEntity(
            id = 0,
            title = "task1",
            description = "des1",
            deadLine = "Saturday, 30 April 2022"
        ),
        TaskEntity(
            id = 1,
            title = "task2",
            description = "des2",
            deadLine = "Friday, 29 April 2022"
        )
    )

    override suspend fun saveTask(task: TaskEntity) {
        tasksList.add(task)
    }

    override suspend fun deleteTask(task: TaskEntity) {
        tasksList.remove(task)
    }

    override fun getTasks(): Flow<List<TaskEntity>> {
        return flowOf(tasksList)
    }
}