package data.sources

import androidx.room.Database
import androidx.room.RoomDatabase
import domain.model.GameData
import domain.model.Player

@Database(entities = [Player::class, GameData::class], version = 1)
abstract class TicTacToeDatabase : RoomDatabase() {
    abstract fun playerDao(): PlayerDao
    abstract fun gameDataDao(): GameDataDao

    companion object {
        const val DATABASE_NAME = "tictactoe.db"
    }
}
