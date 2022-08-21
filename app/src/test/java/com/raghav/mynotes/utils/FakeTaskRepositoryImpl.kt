package com.raghav.mynotes.utils

import com.raghav.mynotes.models.TaskEntity
import com.raghav.mynotes.repository.TasksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeTaskRepositoryImpl : TasksRepository {

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
    private var checkBoxState: Boolean = false

    override suspend fun saveTask(task: TaskEntity) {
        tasksList.add(task)
    }

    override fun getAllTasks(): Flow<List<TaskEntity>> {
        return flow { emit(tasksList) }
    }

    override suspend fun deleteTask(task: TaskEntity) {
        tasksList.remove(task)
    }

    override suspend fun saveSortCheckBoxState(checkBoxState: Boolean) {
        this.checkBoxState = checkBoxState
    }

    override suspend fun getSortCheckBoxState(): Boolean {
        return checkBoxState
    }
}