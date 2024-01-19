package com.example.datasharedtest.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("data_store")
class DataStoreManager(private val context: Context) {
    suspend fun saveSetts(settings: Settings) {
        context.dataStore.edit { pref ->
            pref[stringPreferencesKey("name")] = settings.name
        }
    }

    fun getSetts() = context.dataStore.data.map { pref ->
        return@map Settings(
            pref[stringPreferencesKey("name")] ?: ""
        )
    }
}