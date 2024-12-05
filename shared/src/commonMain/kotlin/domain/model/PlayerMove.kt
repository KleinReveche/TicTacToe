package domain.model

import kotlinx.serialization.Serializable

@Serializable
data class PlayerMove(val move: Int, val gameVersion: Int)
