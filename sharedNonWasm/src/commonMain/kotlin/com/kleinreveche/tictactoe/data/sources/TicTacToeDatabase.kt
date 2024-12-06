package com.kleinreveche.tictactoe.data.sources

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import com.kleinreveche.tictactoe.data.util.Converters
import com.kleinreveche.tictactoe.domain.model.AppSettingRoom
import com.kleinreveche.tictactoe.domain.model.GameDataRoom
import com.kleinreveche.tictactoe.domain.model.LocalMatchRoom
import com.kleinreveche.tictactoe.domain.model.LocalPlayerRoom
import com.kleinreveche.tictactoe.platform.getPlatform
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.div

expect object TicTacToeDatabaseCtor : RoomDatabaseConstructor<TicTacToeDatabase>

@Database(
  entities = [LocalPlayerRoom::class, GameDataRoom::class, AppSettingRoom::class, LocalMatchRoom::class],
  version = 1,
)
@ConstructedBy(TicTacToeDatabaseCtor::class)
@TypeConverters(Converters::class)
abstract class TicTacToeDatabase : RoomDatabase() {
  abstract fun localPlayerDao(): LocalPlayerDao

  abstract fun gameDataDao(): GameDataDao

  abstract fun appSettingDao(): AppSettingDao

  abstract fun localMatchDao(): LocalMatchDao

  companion object {
    private const val APP_NAME = "TicTacToe"
    const val DATABASE_NAME = "tictactoe.db"

    fun getDatabaseLocation(): String = getPath().toString()

    private fun getPath(): Path =
      when {
        getPlatform().name.startsWith("Windows") ->
          Path(System.getenv("APPDATA") ?: ".") / APP_NAME / DATABASE_NAME

        getPlatform().name.startsWith("Mac") ->
          Path(System.getenv("HOME") ?: ".") / "Library/Application Support" / APP_NAME / DATABASE_NAME

        getPlatform().name.startsWith("Linux") ->
          Path(System.getenv("HOME") ?: ".") / ".local/share" / APP_NAME / DATABASE_NAME

        else ->
          Path(System.getenv("HOME") ?: ".") / ".$APP_NAME" / DATABASE_NAME
      }
  }
}
