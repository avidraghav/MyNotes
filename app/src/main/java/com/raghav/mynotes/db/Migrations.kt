package com.raghav.mynotes.db

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 * Created by Raghav Aggarwal on 12/03/22.
 */
object Migrations {

    val MIGRATION_2_3 = object : Migration(2, 3) {
        override fun migrate(database: SupportSQLiteDatabase) {
            // Create the new table
            database.execSQL(
                "CREATE TABLE tasks_new (id INTEGER, title TEXT NOT NULL, description TEXT NOT NULL,deadLine TEXT NOT NULL DEFAULT '0', PRIMARY KEY(id))"
            )
            // Copy the data
            database.execSQL(
                "INSERT INTO tasks_new (id, title, description,deadLine) SELECT id, title,description,deadLine FROM tasks"
            )
            // Remove the old table
            database.execSQL("DROP TABLE tasks")
            // Change the table name to the correct one
            database.execSQL("ALTER TABLE tasks_new RENAME TO tasks")
        }
    }
}