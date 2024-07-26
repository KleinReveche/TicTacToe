package domain.model

import kotlinx.serialization.Serializable

@Serializable
data class AppSetting(
    val setting: String,
    val value: String,
)
