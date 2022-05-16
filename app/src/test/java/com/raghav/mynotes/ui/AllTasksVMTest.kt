package com.raghav.mynotes.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.raghav.mynotes.models.TaskEntity
import com.raghav.mynotes.repository.TasksRepository
import com.raghav.mynotes.utils.Resource
import com.raghav.mynotes.utils.TestDispatchers
import getOrAwaitValueTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class AllTasksVMTest {

    /**
     * makes sure that all the background tasks related to
     * architectural components happen in the same thread important if observing live data
     */
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var allTasksVM: AllTasksVM

    @Mock
    private lateinit var mockRepository: TasksRepository
    private val dispatchers = TestDispatchers()
    private val tasksList: List<TaskEntity> = listOf(
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
        ),
    )

    @Before
    fun setUp() {
        allTasksVM = AllTasksVM(mockRepository, dispatchers)
    }

    /**
     * Test cases follow naming convention:
     * unitUnderTest_inputProvidedToUnit_resultExpected
     * */
    @Test
    fun getTasks_notSortedByDeadline_returnsUnSortedTasksList() = runBlocking {
        val testFlow = flow { emit(tasksList) }

        `when`(mockRepository.getAllTasks()).thenReturn(testFlow)
        allTasksVM.getTasks()
        val value = allTasksVM.tasks.getOrAwaitValueTest()

        assertThat(value.data).isEqualTo(Resource.Success(tasksList).data)
    }

    @Test
    fun getTasks_sortedByDeadline_returnsSortedTasksList() = runBlocking {
        val testFlow = flow { emit(tasksList) }

        `when`(mockRepository.getAllTasks()).thenReturn(testFlow)
        allTasksVM.getTasks(true)
        val value = allTasksVM.tasks.getOrAwaitValueTest()

        assertThat(value.data).isEqualTo(Resource.Success(tasksList).data?.reversed())
    }
}











