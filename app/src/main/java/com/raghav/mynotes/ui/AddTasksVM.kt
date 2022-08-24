package com.raghav.mynotes.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raghav.mynotes.models.TaskEntity
import com.raghav.mynotes.repository.TasksRepository
import com.raghav.mynotes.utils.Constants.KEY_SAVED_STATE_DEADLINE
import com.raghav.mynotes.utils.dispatchers.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTasksVM @Inject constructor(
    private val repository: TasksRepository,
    private val dispatchers: DispatchersProvider,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    suspend fun saveTask(task: TaskEntity) {
        viewModelScope.launch(dispatchers.main) {
            repository.saveTask(task)
        }
    }

    fun fetchDeadline() = savedStateHandle.get<String>(KEY_SAVED_STATE_DEADLINE)

    fun setDeadline(date: String) {
        savedStateHandle[KEY_SAVED_STATE_DEADLINE] = date
    }
}