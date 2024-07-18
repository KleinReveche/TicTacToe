package data

import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import data.sources.TicTacToeDatabase

fun buildTicTacToeDb(context: Context): TicTacToeDatabase =
  Room.databaseBuilder<TicTacToeDatabase>(
      context = context.applicationContext,
      name = context.getDatabasePath(TicTacToeDatabase.DATABASE_NAME).absolutePath,
    )
    .setDriver(BundledSQLiteDriver())
    .build()
