package com.raghav.mvvmtodo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raghav.mvvmtodo.models.TaskEntity
import com.raghav.mvvmtodo.repository.TasksRepository
import com.raghav.mvvmtodo.utils.Resource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AllTasksVM(private val repository: TasksRepository) : ViewModel() {

    private val _tasks = MutableLiveData<Resource<List<TaskEntity>>>()
    val tasks: LiveData<Resource<List<TaskEntity>>> = _tasks

    init {
        getTasks()
    }

    private fun getTasks() {
        viewModelScope.launch {
            repository.getAllTasks().collect {
                _tasks.value = Resource.Loading()
                _tasks.value = Resource.Success(it)
            }
        }
    }
}