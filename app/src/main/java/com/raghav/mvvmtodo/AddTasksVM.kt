package com.raghav.mvvmtodo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raghav.mvvmtodo.models.TaskEntity
import com.raghav.mvvmtodo.repository.TasksRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AddTasksVM(private val repository: TasksRepository) : ViewModel() {

    private val _tasks = MutableLiveData<List<TaskEntity>>()
    val tasks: LiveData<List<TaskEntity>> = _tasks


    suspend fun saveTask(task: TaskEntity) {
        viewModelScope.launch {
            repository.saveTask(task)
        }
    }

    fun getTasks() {
        viewModelScope.launch {
            repository.getAllTasks().collect {
                _tasks.value = it
            }
        }
    }
}