package data.sources

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver

fun buildTicTacToeDb(): TicTacToeDatabase =
    Room
        .databaseBuilder<TicTacToeDatabase>(name = TicTacToeDatabase.getDatabaseLocation())
        .setDriver(BundledSQLiteDriver())
        .build()
