import oshi.SystemInfo

class JVMPlatform : Platform {
  override val name: String = System.getProperty("os.name")
  override val version: String = SystemInfo().operatingSystem.versionInfo.buildNumber
}

actual fun getPlatform(): Platform = JVMPlatform()
