package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.*
import com.example.viewmodel.AppScreen
import com.example.viewmodel.LanguageMode

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    currentScreen: AppScreen,
    languageMode: LanguageMode,
    onLanguageToggle: (LanguageMode) -> Unit,
    onSearchClick: () -> Unit,
    onAiClick: () -> Unit,
    onNotificationsClick: () -> Unit,
    onBackClick: () -> Unit,
    canGoBack: Boolean
) {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (canGoBack && currentScreen != AppScreen.HOME) {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Navy800)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.img_daily_drive_logo),
                            contentDescription = "Daily Drive Logo",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }

                Column {
                    Text(
                        text = when (currentScreen) {
                            AppScreen.HOME -> "Daily Drive"
                            AppScreen.EXAMS -> "Competitive Exams"
                            AppScreen.EXAM_DETAIL -> "Exam Preparation"
                            AppScreen.QUIZ -> "Quiz Zone"
                            AppScreen.ACTIVE_QUIZ -> "Practice Quiz"
                            AppScreen.MOCK_TESTS -> "Mock Test Center"
                            AppScreen.ACTIVE_MOCK_TEST -> "Live Test Exam"
                            AppScreen.MOCK_RESULT -> "Performance Analysis"
                            AppScreen.CURRENT_AFFAIRS -> "Current Affairs"
                            AppScreen.CAREER -> "Career & Eligibility"
                            AppScreen.PROFILE -> "Student Profile"
                            AppScreen.AI_ASSISTANT -> "Daily Drive AI"
                            AppScreen.STUDY_PLANNER -> "Study Planner"
                            AppScreen.ELIGIBILITY_CHECKER -> "Eligibility Checker"
                            AppScreen.EXAM_FINDER -> "Find Your Exam"
                            AppScreen.SEARCH -> "Search Platform"
                            AppScreen.BOOKMARKS_REVISION -> "Bookmarks & Revision"
                            AppScreen.NOTIFICATIONS -> "Exam Notifications"
                        },
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary,
                        maxLines = 1
                    )
                    if (currentScreen == AppScreen.HOME) {
                        Text(
                            text = "All-In-One Exam Companion",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
                        )
                    }
                }
            }
        },
        actions = {
            // Language mode toggle chip
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.15f),
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .clickable {
                        val nextMode = when (languageMode) {
                            LanguageMode.ENGLISH -> LanguageMode.HINDI
                            LanguageMode.HINDI -> LanguageMode.BILINGUAL
                            LanguageMode.BILINGUAL -> LanguageMode.ENGLISH
                        }
                        onLanguageToggle(nextMode)
                    }
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Translate,
                        contentDescription = "Language",
                        modifier = Modifier.size(14.dp),
                        tint = GoldAccent
                    )
                    Text(
                        text = when (languageMode) {
                            LanguageMode.ENGLISH -> "EN"
                            LanguageMode.HINDI -> "हिंदी"
                            LanguageMode.BILINGUAL -> "En/हिं"
                        },
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }

            Spacer(modifier = Modifier.width(4.dp))

            // AI Shortcut
            IconButton(onClick = onAiClick) {
                Icon(
                    imageVector = Icons.Default.AutoAwesome,
                    contentDescription = "AI Assistant",
                    tint = GoldAccent
                )
            }

            // Search
            IconButton(onClick = onSearchClick) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }

            // Notifications
            IconButton(onClick = onNotificationsClick) {
                BadgedBox(
                    badge = {
                        Badge(containerColor = RoseError) {
                            Text("3", fontSize = 9.sp)
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Notifications",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Navy800,
            titleContentColor = Color.White
        )
    )
}

data class NavItem(
    val screen: AppScreen,
    val label: String,
    val icon: ImageVector
)

@Composable
fun AppBottomBar(
    currentScreen: AppScreen,
    onNavigate: (AppScreen) -> Unit
) {
    // Hide bottom bar during active full-screen tests or quiz
    val shouldShow = currentScreen != AppScreen.ACTIVE_MOCK_TEST &&
                     currentScreen != AppScreen.ACTIVE_QUIZ

    AnimatedVisibility(visible = shouldShow) {
        NavigationBar(
            containerColor = Navy900,
            tonalElevation = 8.dp,
            windowInsets = WindowInsets.navigationBars
        ) {
            val items = listOf(
                NavItem(AppScreen.HOME, "Home", Icons.Default.Home),
                NavItem(AppScreen.EXAMS, "Exams", Icons.Default.MenuBook),
                NavItem(AppScreen.QUIZ, "Quiz", Icons.Default.Quiz),
                NavItem(AppScreen.MOCK_TESTS, "Mock", Icons.Default.Timer),
                NavItem(AppScreen.CURRENT_AFFAIRS, "CA", Icons.Default.Newspaper),
                NavItem(AppScreen.CAREER, "Career", Icons.Default.Work),
                NavItem(AppScreen.PROFILE, "Profile", Icons.Default.Person)
            )

            items.forEach { item ->
                val selected = currentScreen == item.screen
                NavigationBarItem(
                    selected = selected,
                    onClick = { onNavigate(item.screen) },
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.label,
                            modifier = Modifier.size(22.dp)
                        )
                    },
                    label = {
                        Text(
                            text = item.label,
                            fontSize = 10.sp,
                            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                            maxLines = 1
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Navy900,
                        selectedTextColor = ElectricBlueLight,
                        indicatorColor = ElectricBlueLight,
                        unselectedIconColor = SlateTextMuted,
                        unselectedTextColor = SlateTextMuted
                    )
                )
            }
        }
    }
}
