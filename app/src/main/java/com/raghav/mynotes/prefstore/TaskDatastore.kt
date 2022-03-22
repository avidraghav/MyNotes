package com.raghav.mynotes.prefstore

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
interface TaskDatastore {

    val isTasksSorted: Flow<Boolean>

    /**
     * Sets a value to a datastore key
     * */
    suspend fun <T> setValue(key: Preferences.Key<T>, value: T)
}