package com.raghav.mynotes.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raghav.mynotes.models.TaskEntity
import com.raghav.mynotes.repository.TasksRepository
import com.raghav.mynotes.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllTasksVM @Inject constructor(private val repository: TasksRepository) : ViewModel() {

    private val _tasks = MutableLiveData<Resource<List<TaskEntity>>>()
    val tasks: LiveData<Resource<List<TaskEntity>>> = _tasks

    init {
        getTasks()
    }

    private fun getTasks() {
        viewModelScope.launch {
            _tasks.postValue(Resource.Loading())
            repository.getAllTasks().collect {
                _tasks.postValue(Resource.Success(it))
            }
        }
    }

    fun deleteTask(task: TaskEntity) {
        viewModelScope.launch {
            repository.deleteTask(task)
        }
    }
}
