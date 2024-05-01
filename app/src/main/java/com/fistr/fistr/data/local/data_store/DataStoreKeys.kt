package com.fistr.fistr.data.local.data_store

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object DataStoreKeys {
    val KEEP_ME_SIGNED_IN = booleanPreferencesKey("keep_me_signed_in")
    val LOGGED_IN_USER_ID = intPreferencesKey("logged_in_user_id")
    val LOGGED_IN_USER_USERNAME = stringPreferencesKey("logged_in_user_username")
    val LOGGED_IN_USER_FULL_NAME = stringPreferencesKey("logged_in_user_full_name")
}
