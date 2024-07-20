interface Platform {
  val name: String
  val version: String
}

expect fun getPlatform(): Platform
