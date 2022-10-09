package com.raghav.mynotes.utils

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import com.raghav.mynotes.prefstore.TaskDatastore
import com.raghav.mynotes.utils.Constants.PREFERENCE_KEY_IS_SORTED
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeDatastore : TaskDatastore {

    private var hashMap: HashMap<Any, Any> = HashMap()

    companion object {
        private val DUMMY_IS_SORTED_KEY = booleanPreferencesKey(PREFERENCE_KEY_IS_SORTED)
    }

    override val isTasksSorted: Flow<Boolean>
        get() = flow { emit((hashMap[DUMMY_IS_SORTED_KEY] ?: false) as Boolean) }

    override suspend fun <T> setValue(key: Preferences.Key<T>, value: T) {
        hashMap[key] = value!!
    }
}