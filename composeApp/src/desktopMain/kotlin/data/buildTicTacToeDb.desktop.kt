package data

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import data.sources.TicTacToeDatabase
import data.sources.instantiateImpl

fun buildTicTacToeDb(): TicTacToeDatabase =
  Room.databaseBuilder<TicTacToeDatabase>(
      name = TicTacToeDatabase.DATABASE_NAME,
      factory = { TicTacToeDatabase::class.instantiateImpl() },
    )
    .setDriver(BundledSQLiteDriver())
    .build()
