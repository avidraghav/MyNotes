package com.raghav.mvvmtodo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raghav.mvvmtodo.models.TaskEntity
import com.raghav.mvvmtodo.repository.TasksRepository
import kotlinx.coroutines.launch

class AddTasksVM(private val repository: TasksRepository) : ViewModel() {

    suspend fun saveTask(task: TaskEntity) {
        viewModelScope.launch {
            repository.saveTask(task)
        }
    }

}