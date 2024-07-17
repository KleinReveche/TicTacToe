package data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
//import getDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class DataStoreManager {
    init {
        //dataStore = getDataStore()
    }

    suspend fun saveDeviceTokenIdentifier(identifier: String) {
        val savedIdentifiers =
            dataStore.data.map { preferences -> preferences[DEVICE_TOKEN_IDENTIFIER_KEY] }.first()

        if (savedIdentifiers != null) return

        dataStore.edit { preferences -> preferences[DEVICE_TOKEN_IDENTIFIER_KEY] = identifier }
    }

    fun getDeviceTokenIdentifier() =
        dataStore.data.map { preferences -> preferences[DEVICE_TOKEN_IDENTIFIER_KEY] }

    suspend fun setDefaultPlayer(player: String) {
        dataStore.edit { preferences -> preferences[DEFAULT_PLAYER_KEY] = player }
    }

    fun getDefaultPlayer() = dataStore.data.map { preferences -> preferences[DEFAULT_PLAYER_KEY] }

    companion object {
        private lateinit var dataStore: DataStore<Preferences>
        private val DEVICE_TOKEN_IDENTIFIER_KEY = stringPreferencesKey("deviceTokenIdentifier")
        private val DEFAULT_PLAYER_KEY = stringPreferencesKey("defaultPlayer")
    }
}
