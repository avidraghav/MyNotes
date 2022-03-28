package com.raghav.mynotes.repository

import com.raghav.mynotes.db.TasksDao
import com.raghav.mynotes.models.TaskEntity
import javax.inject.Inject

class TasksRepository @Inject constructor(private val dao: TasksDao) {

    suspend fun saveTask(task: TaskEntity) = dao.saveTask(task)
    fun getAllTasks() = dao.getTasks()
    suspend fun deleteTask(task: TaskEntity) = dao.deleteTask(task)

}
