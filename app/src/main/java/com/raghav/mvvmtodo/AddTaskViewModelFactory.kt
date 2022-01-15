package com.raghav.mvvmtodo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.raghav.mvvmtodo.repository.TasksRepository

class AddTaskViewModelFactory(private val repository: TasksRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddTasksVM::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddTasksVM(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}