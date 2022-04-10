package com.raghav.mynotes.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raghav.mynotes.models.TaskEntity
import com.raghav.mynotes.repository.TasksRepository
import com.raghav.mynotes.utils.dispatchers.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTasksVM @Inject constructor(
    private val repository: TasksRepository,
    private val dispatchers: DispatchersProvider,
) : ViewModel() {

    suspend fun saveTask(task: TaskEntity) {
        viewModelScope.launch(dispatchers.main) {
            repository.saveTask(task)
        }
    }
}