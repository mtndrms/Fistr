package com.fistr.fistr.data.local.data_store

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.fistr.fistr.data.local.data_store.DataStoreKeys.KEEP_ME_SIGNED_IN
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Named

class SettingsRepository @Inject constructor(@Named("settings") private val settingsDataStore: DataStore<Preferences>) {
    suspend fun toggleKeepMeSignedIn(value: Boolean) {
        settingsDataStore.edit { settings ->
            settings[KEEP_ME_SIGNED_IN] = value
        }
    }

    suspend fun isKeepMeSignedInOn(): Boolean {
        val preferences = settingsDataStore.data.first()
        return preferences[KEEP_ME_SIGNED_IN] ?: false
    }
}
