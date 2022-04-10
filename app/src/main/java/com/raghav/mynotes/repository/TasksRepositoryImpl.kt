package com.raghav.mynotes.repository

import com.raghav.mynotes.db.TasksDao
import com.raghav.mynotes.models.TaskEntity
import javax.inject.Inject

class TasksRepositoryImpl @Inject constructor(private val dao: TasksDao) : TasksRepository {

   override suspend fun saveTask(task: TaskEntity) = dao.saveTask(task)
   override fun getAllTasks() = dao.getTasks()
   override suspend fun deleteTask(task: TaskEntity) = dao.deleteTask(task)
}
