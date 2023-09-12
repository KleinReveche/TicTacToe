package com.kleinreveche.tictactoe.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

enum class LocalDataStoreKeys {
    WINS_EASY,
    LOSES_EASY,
    DRAWS_EASY,
    WINS_NORMAL,
    LOSES_NORMAL,
    DRAWS_NORMAL,
    WINS_HARD,
    LOSES_HARD,
    DRAWS_HARD,
    SHOW_DRAWS,
    COMPUTER_FIRST_MOVE,
    COMPUTER_DIFFICULTY,
    COMPUTER_AS_OPPONENT
}

class TicTacToeLocalDataStore(private val dataStore: DataStore<Preferences>) {

    companion object {
        private val WINS_EASY_KEY = intPreferencesKey("wins_easy")
        private val LOSES_EASY_KEY = intPreferencesKey("loses_easy")
        private val DRAWS_EASY_KEY = intPreferencesKey("draws_easy")
        private val WINS_NORMAL_KEY = intPreferencesKey("wins_normal")
        private val LOSES_NORMAL_KEY = intPreferencesKey("loses_normal")
        private val DRAWS_NORMAL_KEY = intPreferencesKey("draws_normal")
        private val WINS_HARD_KEY = intPreferencesKey("wins_insane")
        private val LOSES_HARD_KEY = intPreferencesKey("loses_insane")
        private val DRAWS_HARD_KEY = intPreferencesKey("draws_insane")
        private val COMPUTER_DIFFICULTY_KEY = intPreferencesKey("computer_difficulty")
        private val SHOW_DRAWS_KEY = booleanPreferencesKey("show_draw_counter")
        private val COMPUTER_FIRST_MOVE_KEY = booleanPreferencesKey("computer_first_move")
        private val COMPUTER_AS_OPPONENT_KEY = booleanPreferencesKey("computer_as_opponent")
    }

    fun getWins(localComputerDifficulty: LocalComputerDifficulty): Flow<Int> =
        dataStore.data.map {
            it[
                when (localComputerDifficulty) {
                    LocalComputerDifficulty.EASY -> WINS_EASY_KEY
                    LocalComputerDifficulty.NORMAL -> WINS_NORMAL_KEY
                    LocalComputerDifficulty.HARD -> WINS_HARD_KEY
                }
            ] ?: 0
        }

    fun getLoses(localComputerDifficulty: LocalComputerDifficulty): Flow<Int> =
        dataStore.data.map {
            it[
                when (localComputerDifficulty) {
                    LocalComputerDifficulty.EASY -> LOSES_EASY_KEY
                    LocalComputerDifficulty.NORMAL -> LOSES_NORMAL_KEY
                    LocalComputerDifficulty.HARD -> LOSES_HARD_KEY
                }
            ] ?: 0
        }

    fun getDraws(localComputerDifficulty: LocalComputerDifficulty): Flow<Int> =
        dataStore.data.map {
            it[
                when (localComputerDifficulty) {
                    LocalComputerDifficulty.EASY -> DRAWS_EASY_KEY
                    LocalComputerDifficulty.NORMAL -> DRAWS_NORMAL_KEY
                    LocalComputerDifficulty.HARD -> DRAWS_HARD_KEY
                }
            ] ?: 0
        }

    fun getComputerDifficulty(): Flow<Int> = dataStore.data.map { it[COMPUTER_DIFFICULTY_KEY] ?: 1 }
    fun getShowDraws(): Flow<Boolean> = dataStore.data.map { it[SHOW_DRAWS_KEY] ?: false }
    fun getComputerFirstMove(): Flow<Boolean> =
        dataStore.data.map { it[COMPUTER_FIRST_MOVE_KEY] ?: false }

    fun getComputerAsOpponent(): Flow<Boolean> =
        dataStore.data.map { it[COMPUTER_AS_OPPONENT_KEY] ?: true }

    suspend fun incrementWins(localComputerDifficulty: LocalComputerDifficulty) {
        dataStore.edit { it ->
            it[
                when (localComputerDifficulty) {
                    LocalComputerDifficulty.EASY -> WINS_EASY_KEY
                    LocalComputerDifficulty.NORMAL -> WINS_NORMAL_KEY
                    LocalComputerDifficulty.HARD -> WINS_HARD_KEY
                }] = getWins(localComputerDifficulty).map { it + 1 }.first()
        }
    }

    suspend fun incrementLoses(localComputerDifficulty: LocalComputerDifficulty) {
        dataStore.edit { it ->
            it[
                when (localComputerDifficulty) {
                    LocalComputerDifficulty.EASY -> LOSES_EASY_KEY
                    LocalComputerDifficulty.NORMAL -> LOSES_NORMAL_KEY
                    LocalComputerDifficulty.HARD -> LOSES_HARD_KEY
                }] = getLoses(localComputerDifficulty).map { it + 1 }.first()
        }
    }

    suspend fun incrementDraws(localComputerDifficulty: LocalComputerDifficulty) {
        dataStore.edit { it ->
            it[
                when (localComputerDifficulty) {
                    LocalComputerDifficulty.EASY -> DRAWS_EASY_KEY
                    LocalComputerDifficulty.NORMAL -> DRAWS_NORMAL_KEY
                    LocalComputerDifficulty.HARD -> DRAWS_HARD_KEY
                }
            ] = getDraws(localComputerDifficulty).map { it + 1 }.first()
        }
    }

    suspend fun setComputerDifficulty(gameDifficulty: LocalComputerDifficulty) {
        dataStore.edit { it ->
            it[COMPUTER_DIFFICULTY_KEY] = getComputerDifficulty().map {
                when (gameDifficulty) {
                    LocalComputerDifficulty.EASY -> 0
                    LocalComputerDifficulty.NORMAL -> 1
                    else -> 2
                }
            }.first()
        }
    }

    suspend fun setShowDraws() {
        dataStore.edit { it -> it[SHOW_DRAWS_KEY] = getShowDraws().map { !it }.first() }
    }

    suspend fun setComputerFirstMove() {
        dataStore.edit { it ->
            it[COMPUTER_FIRST_MOVE_KEY] = getComputerFirstMove().map { !it }.first()
        }
    }

    suspend fun setComputerAsOpponent() {
        dataStore.edit { it ->
            it[COMPUTER_AS_OPPONENT_KEY] = getComputerAsOpponent().map { !it }.first()
        }
    }
}
