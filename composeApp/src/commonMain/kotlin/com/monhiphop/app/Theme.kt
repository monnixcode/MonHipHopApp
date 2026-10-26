package com.monhiphop.app

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

/** Same colors as the website's style.css */
object Palette {
    val Ink = Color(0xFF0B0B0C)
    val Bone = Color(0xFFF1EDE4)
    val Muted = Color(0xFF9A958A)
    val Gold = Color(0xFFD4A64A)
    val Line = Color(0x24F1EDE4) // bone at ~14% opacity
    val Card = Color(0xFF121213)
}

/** The logo is black on transparent. This turns it white (CSS: filter: invert(1)). */
val InvertFilter: ColorFilter = ColorFilter.colorMatrix(
    ColorMatrix(
        floatArrayOf(
            -1f, 0f, 0f, 0f, 255f,
            0f, -1f, 0f, 0f, 255f,
            0f, 0f, -1f, 0f, 255f,
            0f, 0f, 0f, 1f, 0f
        )
    )
)

/** Heavy, tight headline style (the website uses Big Shoulders Display). */
fun display(size: TextUnit): TextStyle = TextStyle(
    fontWeight = FontWeight.Black,
    fontSize = size,
    lineHeight = size * 1.0f,
    letterSpacing = (-0.5).sp
)
