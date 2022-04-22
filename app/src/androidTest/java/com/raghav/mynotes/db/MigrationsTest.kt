package com.raghav.mynotes.db

import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth.assertThat
import com.raghav.mynotes.db.Migrations.MIGRATION_2_3
import com.raghav.mynotes.models.TaskEntity
import com.raghav.mynotes.utils.MigrationUtil
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

/**
 * Created by Raghav Aggarwal on 12/03/22.
 */
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class MigrationsTest {

    private val testDb = "test_db"

    @get:Rule
    @Suppress("DEPRECATION")
    val helper: MigrationTestHelper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        TasksDatabase::class.java.canonicalName,
        FrameworkSQLiteOpenHelperFactory()
    )
    /**
     * This test case is not needed as the migration from 1 to 2 was performed
     * by using AutoMigration 'without using any AutoMigration specs'
     * */
//    @Test
//    @Throws(IOException::class)
//    fun migrate1to2(){
//
//    }
    /**
     * Test to check error free migration of Room Database from Version 2 to 3
     * */
    @Test
    @Throws(IOException::class)
    fun migrate2To3() = runBlocking {
        // Creating the database in version 2
        helper.createDatabase(testDb, 2).apply {
            // Inserting some data
            execSQL("INSERT INTO tasks VALUES ('title1', 'description1', 1,'Saturday, 19 March 2022')")
            // closing the db
            close()
        }
        // re-opening the database with version 3 and providing MIGRATION_2_3
        helper.runMigrationsAndValidate(testDb, 3, true, MIGRATION_2_3)
        // getting the instance of database with the exported data from version 2
        val tasksDb: TasksDatabase = MigrationUtil.getDatabaseAfterMigrations(
            helper,
            TasksDatabase::class.java,
            testDb,
            MIGRATION_2_3
        ) as TasksDatabase
        //checking for data integrity
        val dao = tasksDb.dao()
        val tasks = dao.getTasks().first()
        val aTask = TaskEntity(1, "title1", "description1", "Saturday, 19 March 2022")
        assertThat(tasks.first()).isEqualTo(aTask)
        tasksDb.close()
    }
}





















