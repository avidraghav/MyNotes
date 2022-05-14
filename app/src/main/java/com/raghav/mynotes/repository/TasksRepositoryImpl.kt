package com.raghav.mynotes.repository

import com.raghav.mynotes.db.TasksDao
import com.raghav.mynotes.models.TaskEntity
import com.raghav.mynotes.prefstore.TaskDatastore
import com.raghav.mynotes.prefstore.TaskDatastoreImpl
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class TasksRepositoryImpl @Inject constructor(
    private val dao: TasksDao,
    private val datastore: TaskDatastore,
) : TasksRepository {

    override suspend fun saveTask(task: TaskEntity) = dao.saveTask(task)
    override fun getAllTasks() = dao.getTasks()
    override suspend fun deleteTask(task: TaskEntity) = dao.deleteTask(task)
    override suspend fun saveSortCheckBoxState(checkBoxState: Boolean) = datastore.setValue(
        TaskDatastoreImpl.IS_SORTED_KEY,
        checkBoxState
    )

    override suspend fun getSortCheckBoxState() = datastore.isTasksSorted.firstOrNull() ?: false
}
