package data.sources

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RenameTable
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.AutoMigrationSpec
import data.util.Converters
import domain.model.AppSetting
import domain.model.GameData
import domain.model.LocalMatch
import domain.model.LocalPlayer

@Database(
  entities = [LocalPlayer::class, GameData::class, AppSetting::class, LocalMatch::class],
  version = 2,
  autoMigrations =
    [AutoMigration(from = 1, to = 2, spec = TicTacToeDatabase.PlayerToLocalPlayer::class)],
)
@TypeConverters(Converters::class)
abstract class TicTacToeDatabase : RoomDatabase() {
  abstract fun playerVsComputerDao(): PlayerVsComputerDao

  abstract fun gameDataDao(): GameDataDao

  abstract fun appSettingDao(): AppSettingDao

  abstract fun localMatchDao(): LocalMatchDao

  @RenameTable(fromTableName = "Player", toTableName = "LocalPlayer")
  class PlayerToLocalPlayer : AutoMigrationSpec

  companion object {
    const val DATABASE_NAME = "tictactoe.db"
  }
}
