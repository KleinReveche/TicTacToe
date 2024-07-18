package domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AppSetting(
    @PrimaryKey val setting: String,
    val value: String
)