package com.raghav.mynotes.repository

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.raghav.mynotes.models.TaskEntity
import com.raghav.mynotes.utils.FakeDao
import com.raghav.mynotes.utils.FakeDatastore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class TasksRepositoryImplTest {

    private lateinit var tasksRepository: TasksRepository
    private val fakeDao = FakeDao()
    private val fakeDatastore = FakeDatastore()
    private val tasksList = listOf(
        TaskEntity(
            id = 0,
            title = "task1",
            description = "des1",
            deadLine = "Saturday, 30 April 2022"
        ),
        TaskEntity(
            id = 1,
            title = "task2",
            description = "des2",
            deadLine = "Friday, 29 April 2022"
        )
    )

    @Before
    fun setupRepository() {
        tasksRepository = TasksRepositoryImpl(fakeDao, fakeDatastore)
    }

    @Test
    fun getAllTasks_returnsFlowContainingTaskList() = runTest {
        val receivedList = tasksRepository.getAllTasks().first()
        assertThat(receivedList).isEqualTo(tasksList)
    }

    @Test
    fun getSortCheckBoxState_returnsFalseIfNull() = runTest {
        val state = tasksRepository.getSortCheckBoxState()
        assertThat(state).isFalse()
    }

    @Test
    fun saveSortCheckBoxState_Boolean_savesCheckBoxState() = runTest {
        tasksRepository.saveSortCheckBoxState(true)
        val state = tasksRepository.getSortCheckBoxState()
        assertThat(state).isTrue()
    }
}