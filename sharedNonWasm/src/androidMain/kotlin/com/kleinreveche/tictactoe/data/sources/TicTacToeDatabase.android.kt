package com.kleinreveche.tictactoe.data.sources

import android.content.Context
import androidx.room.Room

fun buildTicTacToeDb(context: Context): TicTacToeDatabase =
    Room
        .databaseBuilder<TicTacToeDatabase>(
            context = context.applicationContext,
            name = context.getDatabasePath(TicTacToeDatabase.DATABASE_NAME).absolutePath,
        ).build()