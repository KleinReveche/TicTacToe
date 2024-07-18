package data.sources

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import data.util.Converters
import domain.model.AppSetting
import domain.model.GameData
import domain.model.Player

@Database(entities = [Player::class, GameData::class, AppSetting::class], version = 1)
@TypeConverters(Converters::class)
abstract class TicTacToeDatabase : RoomDatabase() {
  abstract fun playerDao(): PlayerDao

  abstract fun gameDataDao(): GameDataDao

  abstract fun appSettingDao(): AppSettingDao

  companion object {
    const val DATABASE_NAME = "tictactoe.db"
  }
}
