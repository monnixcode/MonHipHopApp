package com.monhiphop.app

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.MarqueeSpacing
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.monhiphop.app.resources.Res
import com.monhiphop.app.resources.logo
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource

// Positions of the sections in the scrolling list
private const val HERO = 0
private const val ARTISTS = 2
private const val ABOUT = 3
private const val CONTACT = 4

private val SidePadding = 20.dp
private val TopBarHeight = 64.dp

@Composable
fun App() {
    MaterialTheme(
        colorScheme = darkColorScheme(
            primary = Palette.Gold,
            background = Palette.Ink,
            surface = Palette.Ink,
            onBackground = Palette.Bone,
            onSurface = Palette.Bone
        )
    ) {
        MonHipHopScreen()
    }
}

@Composable
private fun MonHipHopScreen() {
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    var menuOpen by remember { mutableStateOf(false) }
    val scrolled by remember {
        derivedStateOf { listState.firstVisibleItemIndex > 0 || listState.firstVisibleItemScrollOffset > 40 }
    }
    val goTo: (Int) -> Unit = { index ->
        menuOpen = false
        scope.launch { listState.animateScrollToItem(index) }
    }
    val safe = WindowInsets.safeDrawing.asPaddingValues()

    Box(Modifier.fillMaxSize().background(Palette.Ink)) {

        // Faint logo behind the whole page
        Image(
            painter = painterResource(Res.drawable.logo),
            contentDescription = null,
            colorFilter = InvertFilter,
            alpha = 0.07f,
            contentScale = ContentScale.Fit,
            modifier = Modifier.align(Alignment.Center).fillMaxWidth(0.95f)
        )

        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = safe.calculateBottomPadding())
        ) {
            item { Hero(Modifier.fillParentMaxHeight().padding(top = safe.calculateTopPadding() + TopBarHeight)) }
            item { MarqueeStrip() }
            item { ArtistsSection() }
            item { AboutSection(onContact = { goTo(CONTACT) }) }
            item { ContactSection() }
            item { Footer(onArtists = { goTo(ARTISTS) }, onAbout = { goTo(ABOUT) }) }
        }

        // Full-screen menu (mobile style)
        AnimatedVisibility(
            visible = menuOpen,
            enter = slideInVertically { -it } + fadeIn(),
            exit = slideOutVertically { -it } + fadeOut()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Palette.Ink)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { }
                    .padding(horizontal = SidePadding),
                verticalArrangement = Arrangement.Center
            ) {
                MenuItem("Artists") { goTo(ARTISTS) }
                MenuItem("About") { goTo(ABOUT) }
                MenuItem("Contact", gold = true) { goTo(CONTACT) }
            }
        }

        TopBar(
            scrolled = scrolled || menuOpen,
            menuOpen = menuOpen,
            topInset = safe.calculateTopPadding(),
            onHome = { goTo(HERO) },
            onMenu = { menuOpen = !menuOpen },
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

@Composable
private fun MenuItem(text: String, gold: Boolean = false, onClick: () -> Unit) {
    Text(
        text = text.uppercase(),
        style = display(56.sp),
        color = if (gold) Palette.Gold else Palette.Bone,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 10.dp)
    )
}

@Composable
private fun TopBar(
    scrolled: Boolean,
    menuOpen: Boolean,
    topInset: Dp,
    onHome: () -> Unit,
    onMenu: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(if (scrolled) Palette.Ink.copy(alpha = 0.94f) else Color.Transparent)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = topInset)
                .height(TopBarHeight)
                .padding(horizontal = SidePadding),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onHome
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(Res.drawable.logo),
                    contentDescription = "Mon HipHop logo",
                    colorFilter = InvertFilter,
                    modifier = Modifier.height(30.dp)
                )
                Spacer(Modifier.size(10.dp))
                Text("MON HIPHOP", style = display(24.sp), color = Palette.Bone)
            }
            MenuButton(open = menuOpen, onClick = onMenu)
        }
        if (scrolled) HorizontalDivider(color = Palette.Line)
    }
}

@Composable
private fun MenuButton(open: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(44.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Canvas(Modifier.size(26.dp)) {
            val w = size.width
            val stroke = 2.dp.toPx()
            if (open) {
                drawLine(Palette.Bone, Offset(2f, 2f), Offset(w - 2f, w - 2f), stroke, StrokeCap.Round)
                drawLine(Palette.Bone, Offset(w - 2f, 2f), Offset(2f, w - 2f), stroke, StrokeCap.Round)
            } else {
                listOf(0.2f, 0.5f, 0.8f).forEach { f ->
                    drawLine(Palette.Bone, Offset(0f, w * f), Offset(w, w * f), stroke, StrokeCap.Round)
                }
            }
        }
    }
}

// ---------------------------------------------------------------- Hero

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun Hero(modifier: Modifier = Modifier) {
    BoxWithConstraints(modifier.fillMaxWidth()) {
        val headline = (maxWidth.value / 6.3f).sp
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = SidePadding)
                .padding(bottom = 48.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(
                "Independent record label · Mon State · Est. 2026",
                color = Palette.Muted,
                fontSize = 13.sp
            )
            Spacer(Modifier.height(20.dp))
            Text("MON STATE", style = display(headline), color = Palette.Bone, maxLines = 1, softWrap = false)
            Text("RAPPERS", style = display(headline), color = Palette.Gold, maxLines = 1, softWrap = false)
            Spacer(Modifier.height(28.dp))
            Text(
                "A home for Mon State rappers. We back independent artists and keep " +
                    "our language, our sound and our stories loud.",
                color = Palette.Muted,
                fontSize = 17.sp,
                lineHeight = 26.sp
            )
            Spacer(Modifier.height(28.dp))
            LinkGroup("Follow Mon HipHop", LabelLinks.visible(FollowOrder), 48.dp, 22.dp)
            Spacer(Modifier.height(20.dp))
            LinkGroup("Listen on", LabelLinks.visible(ListenOrder), 48.dp, 22.dp)
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun LinkGroup(
    title: String?,
    links: List<Pair<Channel, String>>,
    buttonSize: Dp,
    iconSize: Dp
) {
    if (links.isEmpty()) return // same as the website: hide the row when there is no link
    Column {
        if (title != null) {
            Text(title, color = Palette.Muted, fontSize = 13.sp)
            Spacer(Modifier.height(12.dp))
        }
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            links.forEach { (platform, url) -> SocialButton(platform, url, buttonSize, iconSize) }
        }
    }
}

@Composable
private fun SocialButton(platform: Channel, url: String, size: Dp, iconSize: Dp) {
    val uriHandler = LocalUriHandler.current
    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .border(1.dp, Palette.Line, CircleShape)
            .clickable { uriHandler.openUri(url) },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = platform.icon,
            contentDescription = platform.label,
            tint = Palette.Bone,
            modifier = Modifier.size(iconSize)
        )
    }
}

// ---------------------------------------------------------------- Marquee

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun MarqueeStrip() {
    val words = listOf("Mon State", "Our voice", "Our hiphop", "Keep the culture alive")
    val line = buildAnnotatedString {
        repeat(4) {
            words.forEach { w ->
                withStyle(SpanStyle(color = Palette.Muted)) { append(w.uppercase()) }
                withStyle(SpanStyle(color = Palette.Gold)) { append("   ✦   ") }
            }
        }
    }
    Column(Modifier.fillMaxWidth()) {
        HorizontalDivider(color = Palette.Line)
        Text(
            text = line,
            style = display(34.sp),
            maxLines = 1,
            softWrap = false,
            overflow = TextOverflow.Clip,
            modifier = Modifier
                .padding(vertical = 18.dp)
                .basicMarquee(
                    iterations = Int.MAX_VALUE,
                    initialDelayMillis = 0,
                    spacing = MarqueeSpacing(0.dp),
                    velocity = 50.dp
                )
        )
        HorizontalDivider(color = Palette.Line)
    }
}

// ---------------------------------------------------------------- Artists

@Composable
private fun SectionHead(title: String, subtitle: String) {
    Column(Modifier.fillMaxWidth().padding(bottom = 32.dp)) {
        Text(title.uppercase(), style = display(56.sp), color = Palette.Bone)
        Spacer(Modifier.height(10.dp))
        Text(subtitle, color = Palette.Muted, fontSize = 15.sp)
        Spacer(Modifier.height(18.dp))
        HorizontalDivider(color = Palette.Line)
    }
}

@Composable
private fun ArtistsSection() {
    Column(Modifier.fillMaxWidth().padding(horizontal = SidePadding).padding(top = 72.dp, bottom = 56.dp)) {
        SectionHead("Artists", "The roster")
        Artists.forEachIndexed { i, artist ->
            if (i > 0) Spacer(Modifier.height(28.dp))
            ArtistCard(artist)
        }
    }
}

@Composable
private fun ArtistCard(artist: Artist) {
    val profileSize = 100.dp
    Column(
        Modifier
            .fillMaxWidth()
            .background(Palette.Card)
            .border(1.dp, Palette.Line)
    ) {
        // Cover photo + round profile photo overlapping its bottom edge
        Box(Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(artist.cover),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth().aspectRatio(16f / 7f)
            )
            Image(
                painter = painterResource(artist.profile),
                contentDescription = artist.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 24.dp)
                    .offset(y = profileSize / 2)
                    .size(profileSize)
                    .clip(CircleShape)
                    .border(4.dp, Palette.Card, CircleShape)
            )
        }
        Column(Modifier.padding(start = 24.dp, end = 24.dp, top = profileSize / 2 + 14.dp, bottom = 26.dp)) {
            Text(artist.name.uppercase(), style = display(34.sp), color = Palette.Bone)
            Spacer(Modifier.height(6.dp))
            Text(artist.role, color = Palette.Muted, fontSize = 14.sp)
            Spacer(Modifier.height(18.dp))
            LinkGroup(null, artist.links.visible(ArtistOrder), 42.dp, 20.dp)
        }
    }
}

// ---------------------------------------------------------------- About

@Composable
private fun AboutSection(onContact: () -> Unit) {
    Column(Modifier.fillMaxWidth().padding(horizontal = SidePadding).padding(vertical = 56.dp)) {
        Text("REAL HIPHOP.\nREAL STORIES.", style = display(56.sp), color = Palette.Bone)
        Spacer(Modifier.height(28.dp))
        Text(
            "Mon HipHop is an independent platform dedicated to Mon State " +
                "rappers, music, creativity and cultural expression. We give " +
                "artists a place to share their voice, their culture and their stories.",
            color = Palette.Muted,
            fontSize = 18.sp,
            lineHeight = 28.sp
        )
        Spacer(Modifier.height(28.dp))
        AppButton("Get in touch", solid = false, onClick = onContact)
    }
}

@Composable
private fun AppButton(text: String, solid: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .then(if (solid) Modifier.background(Palette.Gold) else Modifier.border(1.dp, Palette.Bone))
            .clickable(onClick = onClick)
            .padding(horizontal = 30.dp, vertical = 16.dp)
    ) {
        Text(
            text,
            color = if (solid) Palette.Ink else Palette.Bone,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
    }
}

// ---------------------------------------------------------------- Contact

@Composable
private fun ContactSection() {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }

    Column(Modifier.fillMaxWidth().padding(horizontal = SidePadding).padding(vertical = 56.dp)) {
        Text("WORK WITH\nUS", style = display(56.sp), color = Palette.Bone)
        Spacer(Modifier.height(20.dp))
        Text("Artist, producer or partner? Send us a message.", color = Palette.Muted, fontSize = 17.sp)
        Spacer(Modifier.height(24.dp))
        FormField(name, { name = it }, "Your name")
        FormField(email, { email = it }, "Email", KeyboardType.Email)
        FormField(message, { message = it }, "Message", minLines = 4)
        Spacer(Modifier.height(20.dp))
        AppButton("Send message", solid = true) {
            note = if (name.isBlank() || email.isBlank() || message.isBlank()) {
                "Please fill in all fields."
            } else {
                // Demo only, like the website. Connect this to a real form service / backend to send messages.
                name = ""; email = ""; message = ""
                "Thanks, your message was received."
            }
        }
        Spacer(Modifier.height(12.dp))
        Text(note, color = Palette.Gold, fontSize = 14.sp, modifier = Modifier.height(22.dp))
    }
}

@Composable
private fun FormField(
    value: String,
    onChange: (String) -> Unit,
    hint: String,
    keyboard: KeyboardType = KeyboardType.Text,
    minLines: Int = 1
) {
    TextField(
        value = value,
        onValueChange = onChange,
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(hint, color = Palette.Muted) },
        keyboardOptions = KeyboardOptions(keyboardType = keyboard),
        singleLine = minLines == 1,
        minLines = minLines,
        colors = TextFieldDefaults.colors(
            focusedTextColor = Palette.Bone,
            unfocusedTextColor = Palette.Bone,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            cursorColor = Palette.Gold,
            focusedIndicatorColor = Palette.Gold,
            unfocusedIndicatorColor = Palette.Line
        )
    )
}

// ---------------------------------------------------------------- Footer

@Composable
private fun Footer(onArtists: () -> Unit, onAbout: () -> Unit) {
    Column(Modifier.fillMaxWidth()) {
        HorizontalDivider(color = Palette.Line)
        BoxWithConstraints(Modifier.fillMaxWidth().padding(horizontal = SidePadding).padding(top = 40.dp)) {
            Text(
                "MON HIPHOP",
                style = display((maxWidth.value / 6.6f).sp).copy(
                    color = Palette.Bone.copy(alpha = 0.25f),
                    drawStyle = Stroke(width = 2f)
                ),
                maxLines = 1,
                softWrap = false
            )
        }
        Column(Modifier.padding(horizontal = SidePadding).padding(top = 28.dp, bottom = 32.dp)) {
            Text(
                "© MON HIPHOP. All rights reserved. Keep the culture alive.",
                color = Palette.Muted,
                fontSize = 13.sp
            )
            Spacer(Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                Text("Artists", color = Palette.Muted, fontSize = 13.sp, modifier = Modifier.clickable(onClick = onArtists))
                Text("About", color = Palette.Muted, fontSize = 13.sp, modifier = Modifier.clickable(onClick = onAbout))
            }
        }
    }
}
