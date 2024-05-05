package com.fistr.fistr.data.local.data_store

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.fistr.fistr.data.local.data_store.DataStoreKeys.LOGGED_IN_USER_FULL_NAME
import com.fistr.fistr.data.local.data_store.DataStoreKeys.LOGGED_IN_USER_ID
import com.fistr.fistr.data.local.data_store.DataStoreKeys.LOGGED_IN_USER_USERNAME
import com.fistr.fistr.data.model.User
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Named

class AppRepository @Inject constructor(@Named("app") private val appDataStore: DataStore<Preferences>) {
    suspend fun saveLoggedInUserLocally(id: Int, username: String, fullName: String) {
        appDataStore.edit { preferences ->
            preferences[LOGGED_IN_USER_ID] = id
            preferences[LOGGED_IN_USER_USERNAME] = username
            preferences[LOGGED_IN_USER_FULL_NAME] = fullName
        }
    }

    suspend fun getLoggedInUser(): User {
        val preferences = appDataStore.data.first()
        return User(
            id = preferences[LOGGED_IN_USER_ID] ?: -1,
            username = preferences[LOGGED_IN_USER_USERNAME] ?: "",
            fullName = preferences[LOGGED_IN_USER_FULL_NAME] ?: ""
        )
    }

    suspend fun getLoggedInUserID(): Int {
        val preferences = appDataStore.data.first()
        return preferences[LOGGED_IN_USER_ID] ?: -1
    }

    suspend fun clear() {
        appDataStore.edit {
            it.clear()
        }
    }
}
