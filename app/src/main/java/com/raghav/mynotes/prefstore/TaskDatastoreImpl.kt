package com.raghav.mynotes.prefstore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import com.raghav.mynotes.utils.Constants.DATASTORE_NAME
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATASTORE_NAME)

class TaskDatastoreImpl @Inject constructor(context: Context) : TaskDatastore {

    private val datastore = context.dataStore
    override val isTasksSorted = datastore.data.catch { exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map {
        it[IS_SORTED_KEY] ?: false
    }

    override suspend fun <T> setValue(key: Preferences.Key<T>, value: T) {
        datastore.edit { preferences ->
            preferences[key] = value
        }
    }

    companion object {

        val IS_SORTED_KEY = booleanPreferencesKey("is_sorted")
    }
}