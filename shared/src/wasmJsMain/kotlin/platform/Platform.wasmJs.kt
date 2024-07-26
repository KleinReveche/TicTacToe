package platform

class WasmPlatform : Platform {
    override val name: String = "Web with Kotlin/Wasm"
    override val version: String = ""
}

actual fun getPlatform(): Platform = WasmPlatform()
