package com.raghav.mynotes.utils

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.room.testing.MigrationTestHelper
import androidx.test.core.app.ApplicationProvider
import com.raghav.mynotes.db.TasksDatabase

/**
 * Creates an instance of RoomDatabase which contains the exported data from the previous version
 */

object MigrationUtil {
    fun getDatabaseAfterMigrations(
        migrationTestHelper: MigrationTestHelper,
        databaseClass: Class<TasksDatabase>,
        databaseName: String,
        vararg migrations: Migration?
    ): RoomDatabase {
        val roomDatabase: RoomDatabase = Room.databaseBuilder(
            ApplicationProvider.getApplicationContext(),
            databaseClass,
            databaseName
        )
            .addMigrations(*migrations)
            .build()
        migrationTestHelper.closeWhenFinished(roomDatabase)
        return roomDatabase
    }
}