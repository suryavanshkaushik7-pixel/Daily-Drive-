package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.local.PreloadedExamData
import com.example.data.model.ExamInfo
import com.example.ui.theme.*
import com.example.viewmodel.AppScreen
import com.example.viewmodel.DailyDriveViewModel

@Composable
fun HomeScreen(
    viewModel: DailyDriveViewModel,
    modifier: Modifier = Modifier
) {
    val userStats by viewModel.userStats.collectAsState()
    val studyTasks by viewModel.studyTasks.collectAsState()
    val completedTasks = studyTasks.count { it.isCompleted }
    val totalTasks = studyTasks.size.coerceAtLeast(1)
    val taskProgress = (completedTasks.toFloat() / totalTasks.toFloat()).coerceIn(0f, 1f)

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Hero Banner with Motivational Greeting & Streak
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp)),
                colors = CardDefaults.cardColors(containerColor = Navy800),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Image(
                        painter = painterResource(id = R.drawable.img_study_hero),
                        contentDescription = "Hero illustration",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(140.dp)
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(140.dp)
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(Color.Transparent, Navy900.copy(alpha = 0.95f))
                                )
                            )
                    )
                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(14.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = GoldAccent.copy(alpha = 0.2f),
                                border = null
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.LocalFireDepartment,
                                        contentDescription = "Streak",
                                        tint = GoldAccent,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Text(
                                        text = "${userStats.currentStreak} Days Streak",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = GoldAccent
                                    )
                                }
                            }

                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = ElectricBlueLight.copy(alpha = 0.2f)
                            ) {
                                Text(
                                    text = "Target: ${userStats.targetExam}",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = ElectricBlueLight,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Welcome back, ${userStats.studentName}!",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "Daily consistency beats occasional intensity. Keep the drive alive!",
                            fontSize = 12.sp,
                            color = SlateLightBg.copy(alpha = 0.85f)
                        )
                    }
                }
            }
        }

        // 2. Quick Action Grid: Daily Quiz, Daily Current Affairs, Recommended Mock Test, Continue Practice
        item {
            Text(
                text = "Fast Track Preparation",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                QuickActionCard(
                    title = "Daily Quiz",
                    subtitle = "10 Qs • Today's Mix",
                    tag = "Live",
                    tagColor = EmeraldSuccess,
                    icon = Icons.Default.Quiz,
                    iconTint = ElectricBlueLight,
                    modifier = Modifier.weight(1f),
                    onClick = {
                        viewModel.startQuiz("Daily Quiz - All India Mix", PreloadedExamData.allQuestions)
                    }
                )
                QuickActionCard(
                    title = "Current Affairs",
                    subtitle = "Sept 2026 Digest",
                    tag = "Updated",
                    tagColor = GoldAccent,
                    icon = Icons.Default.Newspaper,
                    iconTint = GoldAccent,
                    modifier = Modifier.weight(1f),
                    onClick = { viewModel.navigateTo(AppScreen.CURRENT_AFFAIRS) }
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                QuickActionCard(
                    title = "Mock Test",
                    subtitle = "SSC CGL Tier-1 #1",
                    tag = "All India",
                    tagColor = ElectricBlue,
                    icon = Icons.Default.Timer,
                    iconTint = EmeraldSuccess,
                    modifier = Modifier.weight(1f),
                    onClick = {
                        val mock = PreloadedExamData.mockTests.first()
                        viewModel.startMockTest(mock)
                    }
                )
                QuickActionCard(
                    title = "Continue Practice",
                    subtitle = "PYQs & Weak Areas",
                    tag = "Adaptive",
                    tagColor = Navy600,
                    icon = Icons.Default.Psychology,
                    iconTint = GoldAccent,
                    modifier = Modifier.weight(1f),
                    onClick = {
                        viewModel.startQuiz("Adaptive Practice - PYQs", PreloadedExamData.allQuestions.shuffled())
                    }
                )
            }
        }

        // 3. Today's Study Target Progress & Study Planner Link
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = CardDefaults.outlinedCardBorder()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.TrackChanges,
                                contentDescription = "Target",
                                tint = ElectricBlue
                            )
                            Text(
                                text = "Today's Study Target",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        TextButton(
                            onClick = { viewModel.navigateTo(AppScreen.STUDY_PLANNER) },
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 0.dp)
                        ) {
                            Text("Planner", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = "Open Planner",
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    val qCompleted = userStats.completedQuestionsToday
                    val qTarget = userStats.dailyTargetQuestions
                    val qProgress = (qCompleted.toFloat() / qTarget.toFloat()).coerceIn(0f, 1f)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "$qCompleted / $qTarget Questions Solved",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = SlateTextSecondary
                        )
                        Text(
                            text = "${(qProgress * 100).toInt()}% Done",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = ElectricBlue
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                    LinearProgressIndicator(
                        progress = { qProgress },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = ElectricBlue,
                        trackColor = SlateBorder
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = if (qCompleted >= qTarget) "🎉 Target completed! Bonus XP unlocked." else "Solve ${qTarget - qCompleted} more questions today to keep your streak!",
                        fontSize = 12.sp,
                        color = if (qCompleted >= qTarget) EmeraldSuccess else SlateTextMuted
                    )
                }
            }
        }

        // 4. Exam Countdown Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Navy900),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.HourglassTop,
                                contentDescription = "Countdown",
                                tint = GoldAccent,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Exam Countdown",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = GoldAccent
                            )
                        }
                        Text(
                            text = "${userStats.targetExam} Tier-1",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "Tentative: Sept - Oct 2026",
                            fontSize = 11.sp,
                            color = SlateTextMuted
                        )
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(Navy700)
                            .padding(horizontal = 14.dp, vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "38",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Black,
                                color = ElectricBlueLight
                            )
                            Text(
                                text = "Days Left",
                                fontSize = 10.sp,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }

        // 5. Performance Summary
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Performance Summary",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                TextButton(
                    onClick = { viewModel.navigateTo(AppScreen.PROFILE) },
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text("View Full Analytics", fontSize = 13.sp)
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                val accuracy = if (userStats.totalQuestionsAttempted > 0)
                    ((userStats.totalCorrect.toDouble() / userStats.totalQuestionsAttempted) * 100.0).toInt()
                    else 74

                StatMiniCard(
                    title = "Attempted",
                    value = "${userStats.totalQuestionsAttempted}",
                    unit = "Questions",
                    icon = Icons.Default.FormatListNumbered,
                    color = ElectricBlue,
                    modifier = Modifier.weight(1f)
                )
                StatMiniCard(
                    title = "Accuracy",
                    value = "$accuracy%",
                    unit = "Overall",
                    icon = Icons.Default.CheckCircle,
                    color = EmeraldSuccess,
                    modifier = Modifier.weight(1f)
                )
                StatMiniCard(
                    title = "XP Earned",
                    value = "${userStats.totalXp}",
                    unit = "Level ${userStats.currentLevel}",
                    icon = Icons.Default.EmojiEvents,
                    color = GoldAccent,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // 6. Weak Topics Alert & Smart Practice
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = RoseLight.copy(alpha = 0.5f)),
                border = CardDefaults.outlinedCardBorder()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(38.dp)
                                .clip(CircleShape)
                                .background(RoseError.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.WarningAmber,
                                contentDescription = "Weak Areas",
                                tint = RoseError
                            )
                        }
                        Column {
                            Text(
                                text = "Weak Topic Detected",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Navy900
                            )
                            Text(
                                text = "Syllogism & Triangles accuracy is 58%. Smart practice recommended.",
                                fontSize = 12.sp,
                                color = SlateTextSecondary
                            )
                        }
                    }

                    Button(
                        onClick = {
                            viewModel.startQuiz(
                                "Weak Topics Drill: Reasoning & Geometry",
                                PreloadedExamData.allQuestions.filter { it.subject.contains("Reasoning") || it.subject.contains("Quantitative") }
                            )
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = RoseError),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Text("Practice", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // 7. Core Ecosystem Shortcuts: Eligibility Calculator, Exam Finder, Career Guide, AI Mentor
        item {
            Text(
                text = "Ecosystem Tools",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                ToolIconCard(
                    title = "Eligibility Checker",
                    subtitle = "Instant Age & Criteria",
                    icon = Icons.Default.FactCheck,
                    color = ElectricBlue,
                    modifier = Modifier.weight(1f),
                    onClick = { viewModel.navigateTo(AppScreen.ELIGIBILITY_CHECKER) }
                )
                ToolIconCard(
                    title = "Find Your Exam",
                    subtitle = "Personalized Guidance",
                    icon = Icons.Default.Explore,
                    color = GoldAccent,
                    modifier = Modifier.weight(1f),
                    onClick = { viewModel.navigateTo(AppScreen.EXAM_FINDER) }
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                ToolIconCard(
                    title = "Daily Drive AI",
                    subtitle = "Ask Doubts & Syllabus",
                    icon = Icons.Default.AutoAwesome,
                    color = EmeraldSuccess,
                    modifier = Modifier.weight(1f),
                    onClick = { viewModel.navigateTo(AppScreen.AI_ASSISTANT) }
                )
                ToolIconCard(
                    title = "PYQ Library",
                    subtitle = "100% Authentic Papers",
                    icon = Icons.Default.HistoryEdu,
                    color = Navy600,
                    modifier = Modifier.weight(1f),
                    onClick = {
                        viewModel.startQuiz("Previous-Year Questions Drill", PreloadedExamData.allQuestions.filter { it.type.name.contains("PYQ") })
                    }
                )
            }
        }

        // 8. Upcoming Exams & Notifications
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Upcoming Exams & Alerts",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                TextButton(
                    onClick = { viewModel.navigateTo(AppScreen.NOTIFICATIONS) },
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text("All Alerts", fontSize = 13.sp)
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                PreloadedExamData.notifications.take(3).forEach { notif ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        border = CardDefaults.outlinedCardBorder()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (notif.isUrgent) RoseError.copy(alpha = 0.15f) else ElectricBlueLight.copy(alpha = 0.15f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = if (notif.isUrgent) Icons.Default.NotificationImportant else Icons.Default.NotificationsNone,
                                    contentDescription = null,
                                    tint = if (notif.isUrgent) RoseError else ElectricBlue,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = notif.examName,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = ElectricBlue
                                )
                                Text(
                                    text = notif.title,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    maxLines = 1
                                )
                                Text(
                                    text = notif.date,
                                    fontSize = 11.sp,
                                    color = SlateTextMuted
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun QuickActionCard(
    title: String,
    subtitle: String,
    tag: String,
    tagColor: Color,
    icon: ImageVector,
    iconTint: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = CardDefaults.outlinedCardBorder(),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Navy800),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = iconTint,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = tagColor.copy(alpha = 0.15f)
                ) {
                    Text(
                        text = tag,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = tagColor,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = subtitle,
                fontSize = 11.sp,
                color = SlateTextSecondary,
                maxLines = 1
            )
        }
    }
}

@Composable
fun StatMiniCard(
    title: String,
    value: String,
    unit: String,
    icon: ImageVector,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = CardDefaults.outlinedCardBorder()
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = value,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = title,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = SlateTextSecondary
            )
            Text(
                text = unit,
                fontSize = 10.sp,
                color = SlateTextMuted
            )
        }
    }
}

@Composable
fun ToolIconCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    color: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
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
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(CircleShape)
                    .background(color.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(20.dp)
                )
            }
            Column {
                Text(
                    text = title,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = subtitle,
                    fontSize = 11.sp,
                    color = SlateTextSecondary
                )
            }
        }
    }
}
