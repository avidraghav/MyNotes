package com.raghav.mvvmtodo

import android.app.Application
import com.raghav.mvvmtodo.db.TasksDatabase
import com.raghav.mvvmtodo.repository.TasksRepository

class MvvmTodo : Application() {

    val database by lazy { TasksDatabase.getDatabase(this) }
    val repository by lazy { TasksRepository(database.dao()) }

}