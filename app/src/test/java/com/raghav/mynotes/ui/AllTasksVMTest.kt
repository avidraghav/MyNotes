package com.raghav.mynotes.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.raghav.mynotes.models.TaskEntity
import com.raghav.mynotes.utils.FakeTaskRepositoryImpl
import com.raghav.mynotes.utils.TestDispatchers
import getOrAwaitValueTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class AllTasksVMTest {

    /**
     * makes sure that all the background tasks related to
     * architectural components happen in the same thread important if observing live data
     */
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var allTasksVM: AllTasksVM
    private val fakeTaskRepositoryImpl = FakeTaskRepositoryImpl()
    private val dispatchers = TestDispatchers()
    private val tasksList = mutableListOf(
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
    private val sampleTask = tasksList[0]

    @Before
    fun setupViewModel() {
        allTasksVM = AllTasksVM(fakeTaskRepositoryImpl, dispatchers)
    }

    @After
    fun clearViewModel() {
        allTasksVM.clear()
    }

    /**
     * Test cases follow naming convention:
     * unitUnderTest_inputProvidedToUnit_resultExpected
     * */
    @Test
    fun getTasks_notSortedByDeadline_returnsUnSortedTasksList() =
        runTest(UnconfinedTestDispatcher()) {
            allTasksVM.getTasks(false)
            val receivedList = allTasksVM.tasks.getOrAwaitValueTest().data
            assertThat(receivedList).isEqualTo(tasksList)
        }

    @Test
    fun getTasks_sortedByDeadline_returnsSortedTasksList() = runTest(UnconfinedTestDispatcher()) {
        allTasksVM.getTasks(true)
        val receivedList = allTasksVM.tasks.getOrAwaitValueTest().data
        assertThat(receivedList).isEqualTo(tasksList.reversed())
    }

    @Test
    fun deleteTask_task_taskDeleted() = runTest(UnconfinedTestDispatcher()) {
        allTasksVM.deleteTask(sampleTask)
        allTasksVM.getTasks()
        val receivedList = allTasksVM.tasks.getOrAwaitValueTest().data
        assertThat(receivedList).doesNotContain(sampleTask)
    }

    @Test
    fun getSortCheckBoxState_returnsFalseIfNull() = runTest(UnconfinedTestDispatcher()) {
        allTasksVM.getSortCheckBoxState()
        val state = allTasksVM.checkBoxState.getOrAwaitValueTest()
        assertThat(state).isFalse()
    }

    @Test
    fun saveSortCheckBoxState_true_stateSaved() = runTest(UnconfinedTestDispatcher()) {
        allTasksVM.saveSortCheckBoxState(true)
        allTasksVM.getSortCheckBoxState()
        val state = allTasksVM.checkBoxState.getOrAwaitValueTest()
        assertThat(state).isTrue()
    }
}











