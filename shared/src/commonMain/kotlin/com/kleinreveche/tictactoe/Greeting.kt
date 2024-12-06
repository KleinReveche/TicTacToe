package com.kleinreveche.tictactoe

import com.kleinreveche.tictactoe.platform.getPlatform

class Greeting {
    private val platform = getPlatform()

    fun greet(): String {
        return "Hello, ${platform.name}!"
    }
}