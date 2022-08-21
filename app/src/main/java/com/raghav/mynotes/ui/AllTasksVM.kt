package com.raghav.mynotes.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raghav.mynotes.models.TaskEntity
import com.raghav.mynotes.repository.TasksRepository
import com.raghav.mynotes.utils.DateTimeUtils.toTime
import com.raghav.mynotes.utils.Resource
import com.raghav.mynotes.utils.dispatchers.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllTasksVM @Inject constructor(
    private val repository: TasksRepository,
    private val dispatchers: DispatchersProvider,
) : ViewModel() {

    private val _tasks = MutableLiveData<Resource<List<TaskEntity>>>()
    val tasks: LiveData<Resource<List<TaskEntity>>> = _tasks
    private val _checkBoxState = MutableLiveData<Boolean>()
    val checkBoxState: LiveData<Boolean> = _checkBoxState

    fun getTasks(sort: Boolean = false) {
        viewModelScope.launch(dispatchers.main) {
            _tasks.postValue(Resource.Loading())
            repository.getAllTasks().collect {
                if (sort)
                    _tasks.value = Resource.Success(sortTasks(it))
                else
                    _tasks.value = Resource.Success(it)
            }
        }
    }

    fun deleteTask(task: TaskEntity) {
        viewModelScope.launch(dispatchers.main) {
            repository.deleteTask(task)
        }
    }

    private fun sortTasks(tasks: List<TaskEntity>): List<TaskEntity> {
        return tasks.sortedBy {
            it.deadLine.toTime()
        }
    }

    fun saveSortCheckBoxState(isChecked: Boolean) {
        viewModelScope.launch(dispatchers.main) {
            repository.saveSortCheckBoxState(isChecked)
        }
    }

    fun getSortCheckBoxState() {
        viewModelScope.launch(dispatchers.main) {
            _checkBoxState.value = repository.getSortCheckBoxState()
        }
    }

    fun clear() {
        this.onCleared()
    }
}
