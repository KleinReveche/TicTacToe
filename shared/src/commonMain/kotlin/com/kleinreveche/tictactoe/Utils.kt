package com.kleinreveche.tictactoe

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime

fun Instant.toDefaultFormat(): String {
    val format = LocalDateTime.Format {
        monthNumber(); char('/'); dayOfMonth(); char('/'); year()
        char(' ')
        hour(); char(':'); minute()
    }
    return this.toLocalDateTime(TimeZone.currentSystemDefault()).format(format)
}