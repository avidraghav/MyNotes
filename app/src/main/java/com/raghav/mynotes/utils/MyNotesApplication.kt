package com.raghav.mynotes.utils

import android.app.Application
import com.raghav.mynotes.db.TasksDatabase
import com.raghav.mynotes.repository.TasksRepository

class MyNotesApplication : Application() {

    val database by lazy { TasksDatabase.getDatabase(this) }
    val repository by lazy { TasksRepository(database.dao()) }
}