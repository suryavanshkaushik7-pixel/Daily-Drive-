package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*
import com.example.viewmodel.AppScreen
import com.example.viewmodel.DailyDriveViewModel

data class BadgeItem(
    val title: String,
    val desc: String,
    val icon: ImageVector,
    val color: Color,
    val isUnlocked: Boolean
)

@Composable
fun ProfileScreen(
    viewModel: DailyDriveViewModel,
    modifier: Modifier = Modifier
) {
    val userStats by viewModel.userStats.collectAsState()
    val bookmarks by viewModel.bookmarks.collectAsState()
    val attempts by viewModel.mockAttempts.collectAsState()

    val badges = listOf(
        BadgeItem("7-Day Streak", "7 continuous study days", Icons.Default.LocalFireDepartment, GoldAccent, true),
        BadgeItem("Quiz Master", "Solved 300+ questions", Icons.Default.EmojiEvents, ElectricBlue, true),
        BadgeItem("Mock Centurion", "Completed 5+ full mocks", Icons.Default.Timer, EmeraldSuccess, attempts.size >= 1),
        BadgeItem("Accuracy Ace", "85%+ accuracy on test", Icons.Default.CheckCircle, Navy800, true),
        BadgeItem("Night Owl", "Study session past 11 PM", Icons.Default.Nightlight, Navy600, true)
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Profile Header Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Navy800),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(CircleShape)
                                .background(GoldAccent.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = userStats.studentName.take(1),
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = GoldAccent
                            )
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = userStats.studentName,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = "Primary Target: ${userStats.targetExam}",
                                fontSize = 12.sp,
                                color = ElectricBlueLight
                            )
                            Text(
                                text = "Secondary: ${userStats.secondaryTarget} • ${userStats.educationLevel}",
                                fontSize = 11.sp,
                                color = SlateLightBg.copy(alpha = 0.8f)
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = GoldAccent.copy(alpha = 0.2f)
                        ) {
                            Column(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text("LEVEL", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = GoldAccent)
                                Text("${userStats.currentLevel}", fontSize = 18.sp, fontWeight = FontWeight.Black, color = GoldAccent)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // XP Progress to next level
                    val xpInCurrentLevel = userStats.totalXp % 300
                    val progressToNext = (xpInCurrentLevel.toFloat() / 300f).coerceIn(0f, 1f)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Level Progress (${userStats.totalXp} Total XP)",
                            fontSize = 11.sp,
                            color = SlateLightBg.copy(alpha = 0.85f)
                        )
                        Text(
                            text = "${300 - xpInCurrentLevel} XP to Level ${userStats.currentLevel + 1}",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = ElectricBlueLight
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                    LinearProgressIndicator(
                        progress = { progressToNext },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp)),
                        color = ElectricBlueLight,
                        trackColor = Navy700
                    )
                }
            }
        }

        // Stats Matrix
        item {
            val accuracy = if (userStats.totalQuestionsAttempted > 0)
                ((userStats.totalCorrect.toDouble() / userStats.totalQuestionsAttempted) * 100.0).toInt()
                else 74

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                StatMiniCard(
                    title = "Daily Streak",
                    value = "${userStats.currentStreak} Days",
                    unit = "Personal Record",
                    icon = Icons.Default.LocalFireDepartment,
                    color = GoldAccent,
                    modifier = Modifier.weight(1f)
                )
                StatMiniCard(
                    title = "Questions",
                    value = "${userStats.totalQuestionsAttempted}",
                    unit = "${userStats.totalCorrect} Correct",
                    icon = Icons.Default.FormatListNumbered,
                    color = ElectricBlue,
                    modifier = Modifier.weight(1f)
                )
                StatMiniCard(
                    title = "Accuracy",
                    value = "$accuracy%",
                    unit = "Target 85%",
                    icon = Icons.Default.TrackChanges,
                    color = EmeraldSuccess,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Badges & Achievements
        item {
            Text(
                text = "Badges & Achievements (${badges.count { it.isUnlocked }} / ${badges.size})",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(8.dp))

            LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                items(badges) { badge ->
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        border = CardDefaults.outlinedCardBorder(),
                        modifier = Modifier.width(130.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(42.dp)
                                    .clip(CircleShape)
                                    .background(badge.color.copy(alpha = 0.15f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(imageVector = badge.icon, contentDescription = null, tint = badge.color, modifier = Modifier.size(22.dp))
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = badge.title, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Navy900)
                            Text(text = badge.desc, fontSize = 10.sp, color = SlateTextMuted, lineHeight = 13.sp, maxLines = 2)
                        }
                    }
                }
            }
        }

        // Quick Navigation Links
        item {
            Text(
                text = "Account & Library Shortcuts",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(8.dp))

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                ProfileLinkRow(
                    icon = Icons.Default.Bookmark,
                    title = "Saved Bookmarks",
                    subtitle = "${bookmarks.size} Questions Bookmarked",
                    onClick = { viewModel.navigateTo(AppScreen.BOOKMARKS_REVISION) }
                )
                ProfileLinkRow(
                    icon = Icons.Default.History,
                    title = "Mock Test History",
                    subtitle = "${attempts.size} Tests Completed",
                    onClick = { viewModel.navigateTo(AppScreen.MOCK_TESTS) }
                )
                ProfileLinkRow(
                    icon = Icons.Default.FactCheck,
                    title = "Eligibility Profile",
                    subtitle = "Age, Qualification & Stream Settings",
                    onClick = { viewModel.navigateTo(AppScreen.ELIGIBILITY_CHECKER) }
                )
                ProfileLinkRow(
                    icon = Icons.Default.AutoAwesome,
                    title = "Daily Drive AI Assistant",
                    subtitle = "Ask Doubts & Concept Explanations",
                    onClick = { viewModel.navigateTo(AppScreen.AI_ASSISTANT) }
                )
            }
        }
    }
}

@Composable
fun ProfileLinkRow(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = CardDefaults.outlinedCardBorder()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(ElectricBlue.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = icon, contentDescription = null, tint = ElectricBlue, modifier = Modifier.size(18.dp))
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurface)
                Text(text = subtitle, fontSize = 11.sp, color = SlateTextSecondary)
            }

            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = SlateTextMuted, modifier = Modifier.size(16.dp))
        }
    }
}
