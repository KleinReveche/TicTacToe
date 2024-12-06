package com.kleinreveche.tictactoe

import android.app.Application
import com.kleinreveche.tictactoe.di.initKoin
import org.koin.android.ext.koin.androidContext

class TicTacToeApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin { androidContext(this@TicTacToeApp) }
    }
}
