package com.raghav.mvvmtodo.repository

import com.raghav.mvvmtodo.db.TasksDao
import com.raghav.mvvmtodo.models.TaskEntity


class TasksRepository(private val dao: TasksDao) {

    suspend fun saveTask(task: TaskEntity) = dao.saveTask(task)
    suspend fun getAllTasks() = dao.getTasks()

}
