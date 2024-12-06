package com.kleinreveche.tictactoe.platform

class WasmPlatform: Platform {
    override val name: String = "Web with Kotlin/Wasm - ${kotlinx.browser.window.navigator.platform}"
    override val version: String = ""
}

actual fun getPlatform(): Platform = WasmPlatform()