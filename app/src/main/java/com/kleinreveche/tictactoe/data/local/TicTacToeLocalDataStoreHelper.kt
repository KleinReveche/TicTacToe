package com.kleinreveche.tictactoe.data.local

import com.kleinreveche.tictactoe.MainActivity.Companion.getDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

object TicTacToeLocalDataStoreHelper {

    private val dataStore = TicTacToeLocalDataStore(getDataStore())

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getPref(key: LocalDataStoreKeys): Any {
        var result: Any
        runBlocking {
            val getPref = async(Dispatchers.IO) {
                when (key) {
                    LocalDataStoreKeys.WINS_EASY -> dataStore.getWins(LocalComputerDifficulty.EASY)
                    LocalDataStoreKeys.DRAWS_EASY -> dataStore.getDraws(LocalComputerDifficulty.EASY)
                    LocalDataStoreKeys.LOSES_EASY -> dataStore.getLoses(LocalComputerDifficulty.EASY)
                    LocalDataStoreKeys.WINS_NORMAL -> dataStore.getWins(LocalComputerDifficulty.NORMAL)
                    LocalDataStoreKeys.DRAWS_NORMAL -> dataStore.getDraws(LocalComputerDifficulty.NORMAL)
                    LocalDataStoreKeys.LOSES_NORMAL -> dataStore.getLoses(LocalComputerDifficulty.NORMAL)
                    LocalDataStoreKeys.WINS_HARD -> dataStore.getWins(LocalComputerDifficulty.HARD)
                    LocalDataStoreKeys.DRAWS_HARD -> dataStore.getDraws(LocalComputerDifficulty.HARD)
                    LocalDataStoreKeys.LOSES_HARD -> dataStore.getLoses(LocalComputerDifficulty.HARD)
                    LocalDataStoreKeys.COMPUTER_DIFFICULTY -> dataStore.getComputerDifficulty()
                    LocalDataStoreKeys.SHOW_DRAWS -> dataStore.getShowDraws()
                    LocalDataStoreKeys.COMPUTER_AS_OPPONENT -> dataStore.getComputerAsOpponent()
                    LocalDataStoreKeys.COMPUTER_FIRST_MOVE -> dataStore.getComputerFirstMove()
                }
            }
            runBlocking {
                getPref.await()
                result = getPref.getCompleted().first()
            }
            return@runBlocking
        }
        return result
    }

    fun CoroutineScope.setPref(key: LocalDataStoreKeys, value: Any = Any()) {
        launch {
            when (key) {
                LocalDataStoreKeys.WINS_EASY -> dataStore.incrementWins(LocalComputerDifficulty.EASY)
                LocalDataStoreKeys.DRAWS_EASY -> dataStore.incrementDraws(LocalComputerDifficulty.EASY)
                LocalDataStoreKeys.LOSES_EASY -> dataStore.incrementLoses(LocalComputerDifficulty.EASY)
                LocalDataStoreKeys.WINS_NORMAL -> dataStore.incrementWins(LocalComputerDifficulty.NORMAL)
                LocalDataStoreKeys.DRAWS_NORMAL -> dataStore.incrementDraws(LocalComputerDifficulty.NORMAL)
                LocalDataStoreKeys.LOSES_NORMAL -> dataStore.incrementLoses(LocalComputerDifficulty.NORMAL)
                LocalDataStoreKeys.WINS_HARD -> dataStore.incrementWins(LocalComputerDifficulty.HARD)
                LocalDataStoreKeys.DRAWS_HARD -> dataStore.incrementDraws(LocalComputerDifficulty.HARD)
                LocalDataStoreKeys.LOSES_HARD -> dataStore.incrementLoses(LocalComputerDifficulty.HARD)
                LocalDataStoreKeys.COMPUTER_AS_OPPONENT -> dataStore.setComputerAsOpponent()
                LocalDataStoreKeys.COMPUTER_DIFFICULTY -> dataStore.setComputerDifficulty(value as LocalComputerDifficulty)
                LocalDataStoreKeys.SHOW_DRAWS -> dataStore.setShowDraws()
                LocalDataStoreKeys.COMPUTER_FIRST_MOVE -> dataStore.setComputerFirstMove()
            }
        }
    }
}