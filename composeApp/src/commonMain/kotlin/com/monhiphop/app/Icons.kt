package com.monhiphop.app

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.unit.dp

/** Same icons as the website (the SVG path data is copied from index.html). */

private fun icon(name: String, block: ImageVector.Builder.() -> Unit): ImageVector =
    ImageVector.Builder(
        name = name,
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply(block).build()

private fun ImageVector.Builder.fillPath(d: String) = addPath(
    pathData = PathParser().parsePathString(d).toNodes(),
    fill = SolidColor(Color.Black)
)

private fun ImageVector.Builder.strokePath(d: String, width: Float) = addPath(
    pathData = PathParser().parsePathString(d).toNodes(),
    stroke = SolidColor(Color.Black),
    strokeLineWidth = width,
    strokeLineCap = StrokeCap.Round,
    strokeLineJoin = StrokeJoin.Round
)

private fun circlePath(cx: Float, cy: Float, r: Float): String =
    "M${cx - r},${cy} a${r},${r} 0 1,0 ${2 * r},0 a${r},${r} 0 1,0 ${-2 * r},0 z"

private val SpotifyIcon = icon("spotify") {
    fillPath("M12 0C5.4 0 0 5.4 0 12s5.4 12 12 12 12-5.4 12-12S18.66 0 12 0zm5.521 17.34c-.24.359-.66.48-1.021.24-2.82-1.74-6.36-2.101-10.561-1.141-.418.122-.779-.179-.899-.539-.12-.421.18-.78.54-.9 4.56-1.021 8.52-.6 11.64 1.32.42.18.479.659.301 1.02zm1.44-3.3c-.301.42-.841.6-1.262.3-3.239-1.98-8.159-2.58-11.939-1.38-.479.12-1.02-.12-1.14-.6-.12-.48.12-1.021.6-1.141C9.6 9.9 15 10.561 18.72 12.84c.361.181.54.78.241 1.2zm.12-3.36C15.24 8.4 8.82 8.16 5.16 9.301c-.6.179-1.2-.181-1.38-.721-.18-.601.18-1.2.72-1.381 4.26-1.26 11.28-1.02 15.721 1.621.539.3.719 1.02.419 1.56-.299.421-1.02.599-1.559.3z")
}

private val AppleMusicIcon = icon("apple_music") {
    strokePath("M9 18V5l11-2v13", 2f)
    fillPath(circlePath(6.5f, 18f, 2.5f))
    fillPath(circlePath(17.5f, 16f, 2.5f))
}

private val SoundCloudIcon = icon("soundcloud") {
    strokePath("M2 14v3M4.6 12v5M7.2 10.5V17M9.8 9v8", 1.8f)
    fillPath("M12.4 17V8.6A5.4 5.4 0 0 1 16 8.1A4.2 4.2 0 0 1 20.4 11.5A2.8 2.8 0 0 1 20.2 17z")
}

private val YouTubeMusicIcon = icon("youtube_music") {
    strokePath(circlePath(12f, 12f, 10f), 2f)
    fillPath("M10 8.2v7.6l6-3.8z")
}

private val YouTubeIcon = icon("youtube") {
    fillPath("M23.498 6.186a3.016 3.016 0 0 0-2.122-2.136C19.505 3.545 12 3.545 12 3.545s-7.505 0-9.377.505A3.017 3.017 0 0 0 .502 6.186C0 8.07 0 12 0 12s0 3.93.502 5.814a3.016 3.016 0 0 0 2.122 2.136c1.871.505 9.376.505 9.376.505s7.505 0 9.377-.505a3.015 3.015 0 0 0 2.122-2.136C24 15.93 24 12 24 12s0-3.93-.502-5.814zM9.545 15.568V8.432L15.818 12l-6.273 3.568z")
}

private val FacebookIcon = icon("facebook") {
    fillPath("M24 12.073c0-6.627-5.373-12-12-12s-12 5.373-12 12c0 5.99 4.388 10.954 10.125 11.854v-8.385H7.078v-3.47h3.047V9.43c0-3.007 1.792-4.669 4.533-4.669 1.312 0 2.686.235 2.686.235v2.953H15.83c-1.491 0-1.956.925-1.956 1.874v2.25h3.328l-.532 3.47h-2.796v8.385C19.612 23.027 24 18.062 24 12.073z")
}

private val InstagramIcon = icon("instagram") {
    strokePath("M8 3h8a5 5 0 0 1 5 5v8a5 5 0 0 1-5 5H8a5 5 0 0 1-5-5V8a5 5 0 0 1 5-5z", 2f)
    strokePath(circlePath(12f, 12f, 4.2f), 2f)
    fillPath(circlePath(17.4f, 6.6f, 1.3f))
}

private val TikTokIcon = icon("tiktok") {
    fillPath("M12.525.02c1.31-.02 2.61-.01 3.91-.02.08 1.53.63 3.09 1.75 4.17 1.12 1.11 2.7 1.62 4.24 1.79v4.03c-1.44-.05-2.89-.35-4.2-.97-.57-.26-1.1-.59-1.62-.93-.01 2.92.01 5.84-.02 8.75-.08 1.4-.54 2.79-1.35 3.94-1.31 1.92-3.58 3.17-5.91 3.21-1.43.08-2.86-.31-4.08-1.03-2.02-1.19-3.44-3.37-3.65-5.71-.02-.5-.03-1-.01-1.49.18-1.9 1.12-3.72 2.58-4.96 1.66-1.44 3.98-2.13 6.15-1.72.02 1.48-.04 2.96-.04 4.44-.99-.32-2.15-.23-3.02.37-.63.41-1.11 1.04-1.36 1.75-.21.51-.15 1.07-.14 1.61.24 1.64 1.82 3.02 3.5 2.87 1.12-.01 2.19-.66 2.77-1.61.19-.33.4-.67.41-1.06.1-1.79.06-3.57.07-5.36.01-4.03-.01-8.05.02-12.07z")
}

private val XIcon = icon("x") {
    fillPath("M18.901 1.153h3.68l-8.04 9.19L24 22.846h-7.406l-5.8-7.584-6.638 7.584H.474l8.6-9.83L0 1.154h7.594l5.243 6.932ZM17.61 20.644h2.039L6.486 3.24H4.298Z")
}

val Channel.icon: ImageVector
    get() = when (this) {
        Channel.Spotify -> SpotifyIcon
        Channel.AppleMusic -> AppleMusicIcon
        Channel.SoundCloud -> SoundCloudIcon
        Channel.YouTubeMusic -> YouTubeMusicIcon
        Channel.YouTube -> YouTubeIcon
        Channel.Facebook -> FacebookIcon
        Channel.Instagram -> InstagramIcon
        Channel.TikTok -> TikTokIcon
        Channel.X -> XIcon
    }
