package com.monhiphop.app

import com.monhiphop.app.resources.*
import org.jetbrains.compose.resources.DrawableResource

enum class Channel(val label: String) {
    Spotify("Spotify"),
    AppleMusic("Apple Music"),
    SoundCloud("SoundCloud"),
    YouTubeMusic("YouTube Music"),
    YouTube("YouTube"),
    Facebook("Facebook"),
    Instagram("Instagram"),
    TikTok("TikTok"),
    X("X")
}

data class Artist(
    val name: String,
    val role: String,
    val cover: DrawableResource,
    val profile: DrawableResource,
    val links: Map<Channel, String>
)

/** Keeps only the platforms that have a real link, in the order given. */
fun Map<Channel, String>.visible(order: List<Channel>): List<Pair<Channel, String>> =
    order.mapNotNull { p -> this[p]?.takeIf { it.isNotBlank() && it != "#" }?.let { p to it } }

val FollowOrder = listOf(Channel.YouTube, Channel.Facebook, Channel.Instagram, Channel.X, Channel.TikTok)
val ListenOrder = listOf(Channel.Spotify, Channel.AppleMusic, Channel.SoundCloud, Channel.YouTubeMusic)
val ArtistOrder = listOf(Channel.Spotify, Channel.SoundCloud, Channel.YouTube, Channel.Facebook, Channel.Instagram, Channel.TikTok)

/** Official Mon HipHop channels (edit the links here). */
val LabelLinks: Map<Channel, String> = mapOf(
    Channel.YouTube to "https://youtube.com/@monhiphop?si=I8sAI1r5LVYtQNVO",
    Channel.Facebook to "https://www.facebook.com/share/1KEp6zJRcE/",
    Channel.Instagram to "https://www.instagram.com/monhiphop_?xtok=eTNvazJlcXM5Y3Zz",
    Channel.X to "https://x.com/monhiphop_",
    Channel.TikTok to "https://www.tiktok.com/@monhiphop?_r=1&_t=ZS-9AqIS1BIjYY",
    Channel.Spotify to "https://open.spotify.com/artist/4JTkFROtJ0VLuuODg8UNN9?si=JduJbhBHSTOySAxaIUAAeg&utm_source=copy-link",
    Channel.AppleMusic to "https://music.apple.com/us/artist/mon-hiphop-underground/6809851342",
    Channel.SoundCloud to "https://on.soundcloud.com/CR2xsIri4piWNIMrMD",
    Channel.YouTubeMusic to "https://music.youtube.com/@monhiphop?si=IpmlMVtqlcr6i8yr"
)

val Artists = listOf(
    Artist(
        name = "Suriya",
        role = "Rapper · Mon State",
        cover = Res.drawable.suriya_cover,
        profile = Res.drawable.suriya,
        links = mapOf(
            Channel.Spotify to "https://open.spotify.com/artist/3yskKASDsHFESxrKb1gWAF?si=kO61__Z2QW-3-CpbvIGBGQ&utm_source=copy-link",
            Channel.SoundCloud to "https://on.soundcloud.com/DR5R6ubGmIibFOLKoi",
            Channel.YouTube to "https://youtube.com/@suriyashit?si=77wHcbIwo4GKCq2M",
            Channel.Facebook to "https://www.facebook.com/share/1CpqaySiXS/",
            Channel.Instagram to "https://www.instagram.com/1suriyaaa?obrf=MTQxMmE5YXhzazRoaw==",
            Channel.TikTok to "https://www.tiktok.com/@suriyashit?_r=1&_t=ZS-9Aq0EqBcXP0"
        )
    ),
    Artist(
        name = "Nonkizz",
        role = "Rapper · Mon State",
        cover = Res.drawable.nonkizz_cover,
        profile = Res.drawable.nonkizz,
        links = mapOf(
            Channel.Spotify to "https://open.spotify.com/artist/3WGBwvd4bSDftBRaR78tIO?si=f-Mmgkd2TfKnbLXgDX8MGA&utm_source=copy-link",
            Channel.SoundCloud to "https://on.soundcloud.com/BbujFz4NcoFKqJHxvA",
            Channel.YouTube to "https://youtube.com/@nonkizz?si=FxhnlZXJ4Q8SFFbD",
            Channel.Facebook to "https://www.facebook.com/share/14tT5aGHzrJ/",
            Channel.Instagram to "https://www.instagram.com/non_kizzy_official?vrfl=ZTJvMG9sMzNseHdp",
            Channel.TikTok to "https://www.tiktok.com/@nonkizz3?_r=1&_t=ZS-9AqI3M3JtIu"
        )
    ),
    Artist(
        name = "MonState Daddy",
        role = "Rapper · Mon State",
        cover = Res.drawable.monstatedaddy_cover,
        profile = Res.drawable.monstatedaddy,
        links = mapOf(
            Channel.YouTube to "https://youtube.com/@2013dady?si=ZLdX_9aSDKOCbUkT",
            Channel.Facebook to "https://www.facebook.com/share/1CLF4QQpd2/",
            Channel.TikTok to "https://www.tiktok.com/@monstate.tv?_r=1&_t=ZS-9AqJrRpHcUY"
        )
    )
)
