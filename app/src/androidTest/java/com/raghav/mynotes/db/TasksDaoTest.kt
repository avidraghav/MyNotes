package com.raghav.mynotes.db

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.raghav.mynotes.models.TaskEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

// Test Class for TasksDao
class TasksDaoTest {

    private lateinit var db: TasksDatabase
    private lateinit var dao: TasksDao

    // createDb() will be executed before running every test case
    @Before
    fun createDb() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            TasksDatabase::class.java
        ).allowMainThreadQueries()
            .build()

        dao = db.dao()
    }

    // closeDb will be executed after executing every test case
    @After
    fun closeDb() {
        db.close()
    }
    // Naming convention for test cases used is 'subjectUnderTest_actionOrInput_resultState'
    @Test
    fun saveTask_taskProvided_TaskSaved() = runBlocking {
        val aTask = TaskEntity(1, "test", "test", "Friday, 29 April 2022")
        dao.saveTask(aTask)
        val tasks = dao.getTasks().first()
        assertThat(tasks).contains(aTask)
        assertThat(tasks).hasSize(1)
    }

    @Test
    fun saveTask_sameTaskSavedAgain_ReplacesNewTaskWithPrevious() = runBlocking {
        val aTask = TaskEntity(1, "test", "test", "Friday, 29 April 2022")
        val bTask = TaskEntity(1, "test", "test", "Friday, 29 April 2022")
        var tasks: List<TaskEntity>
        dao.saveTask(aTask)
        dao.saveTask(bTask)
        val job = launch {
            dao.getTasks().test {
                tasks = awaitItem()
                assertThat(tasks).contains(bTask)
                assertThat(tasks).hasSize(1)
                cancelAndConsumeRemainingEvents()
            }
        }
        job.join()
        job.cancel()
    }

    @Test
    fun deleteTask_taskProvided_TaskDeleted() = runBlocking {
        var tasks: List<TaskEntity>
        val aTask = TaskEntity(1, "test", "test", "Friday, 29 April 2022")
        val bTask = TaskEntity(2, "test2", "test2", "Saturday, 30 April 2022")
        val cTask = TaskEntity(3, "test3", "test3", "Sunday, 31 April 2022")

        dao.saveTask(aTask)
        dao.saveTask(bTask)
        dao.saveTask(cTask)
        dao.deleteTask(bTask)
        val job = launch {
            dao.getTasks().test {
                tasks = awaitItem()
                assertThat(tasks).doesNotContain(bTask)
                assertThat(tasks).containsExactly(aTask, cTask)
                assertThat(tasks).hasSize(2)
                cancelAndIgnoreRemainingEvents()
            }
        }
        job.join()
        job.cancel()
    }

    @Test
    fun getTasks_noTaskPresent_returnEmptyList() = runBlocking {
        var tasks: List<TaskEntity>
        val aTask = TaskEntity(1, "test", "test", "Friday, 29 April 2022")

        dao.saveTask(aTask)
        dao.deleteTask(aTask)
        val job = launch {
            dao.getTasks().test {
                tasks = awaitItem()
                assertThat(tasks).hasSize(0)
                cancelAndIgnoreRemainingEvents()
            }
        }
        job.join()
        job.cancel()
    }

    @Test
    fun getTasks_tasksPresent_returnTasksList() = runBlocking {
        var tasks: List<TaskEntity>
        val aTask = TaskEntity(1, "test", "test", "Friday, 29 April 2022")
        val bTask = TaskEntity(2, "test2", "test2", "Saturday, 30 April 2022")

        dao.saveTask(aTask)
        dao.saveTask(bTask)
        val job = launch {
            dao.getTasks().test {
                tasks = awaitItem()
                assertThat(tasks).hasSize(2)
                cancelAndConsumeRemainingEvents()
            }
        }
        job.join()
        job.cancel()
    }
}
