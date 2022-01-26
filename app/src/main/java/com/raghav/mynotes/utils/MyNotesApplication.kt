package com.raghav.mynotes.utils

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyNotesApplication : Application() {

//    val database by lazy { TasksDatabase.getDatabase(this) }
//    val repository by lazy { TasksRepository(database.dao()) }

}