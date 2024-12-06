package com.kleinreveche.tictactoe.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "appSettings")
data class AppSettingRoom(
    @PrimaryKey val setting: String,
    val value: String,
)
