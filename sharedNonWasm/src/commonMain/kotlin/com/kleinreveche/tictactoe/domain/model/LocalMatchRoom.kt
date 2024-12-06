package com.kleinreveche.tictactoe.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "localMatches")
data class LocalMatchRoom(
  val player1: String,
  val player2: String,
  val player1Wins: Int,
  val player2Wins: Int,
  val draws: Int,
  @PrimaryKey(autoGenerate = true) val id: Int = 0,
)
