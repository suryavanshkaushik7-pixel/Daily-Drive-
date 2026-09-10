package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.PreloadedExamData
import com.example.data.model.Question
import com.example.ui.theme.*
import com.example.viewmodel.DailyDriveViewModel

data class QuizTypeItem(
    val id: String,
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val color: Color,
    val filterPredicate: (Question) -> Boolean
)

@Composable
fun QuizScreen(
    viewModel: DailyDriveViewModel,
    modifier: Modifier = Modifier
) {
    val quizTypes = listOf(
        QuizTypeItem(
            id = "daily",
            title = "Daily Live Quiz",
            subtitle = "10 Mixed High-Yield Questions",
            icon = Icons.Default.Today,
            color = ElectricBlue,
            filterPredicate = { true }
        ),
        QuizTypeItem(
            id = "subject_reasoning",
            title = "Reasoning Ability Quiz",
            subtitle = "Puzzles, Syllogism & Logic",
            icon = Icons.Default.Psychology,
            color = EmeraldSuccess,
            filterPredicate = { it.subject.contains("Reasoning", ignoreCase = true) }
        ),
        QuizTypeItem(
            id = "subject_quant",
            title = "Quantitative Aptitude Quiz",
            subtitle = "Arithmetic, Algebra & DI",
            icon = Icons.Default.Calculate,
            color = GoldAccent,
            filterPredicate = { it.subject.contains("Quant", ignoreCase = true) || it.subject.contains("Math", ignoreCase = true) }
        ),
        QuizTypeItem(
            id = "subject_ga",
            title = "General Awareness Quiz",
            subtitle = "Polity, History & Science",
            icon = Icons.Default.Public,
            color = Navy600,
            filterPredicate = { it.subject.contains("Polity", ignoreCase = true) || it.subject.contains("History", ignoreCase = true) || it.subject.contains("Science", ignoreCase = true) }
        ),
        QuizTypeItem(
            id = "pyq_quiz",
            title = "Previous-Year Paper Quiz",
            subtitle = "Real questions from official exams",
            icon = Icons.Default.HistoryEdu,
            color = RoseError,
            filterPredicate = { it.type.name.contains("PYQ") }
        ),
        QuizTypeItem(
            id = "rapid_fire",
            title = "Rapid Speed Quiz",
            subtitle = "Fast 15-second drill",
            icon = Icons.Default.Bolt,
            color = GoldAccent,
            filterPredicate = { true }
        ),
        QuizTypeItem(
            id = "current_affairs_quiz",
            title = "Current Affairs Quiz",
            subtitle = "Latest National & Global Events",
            icon = Icons.Default.Newspaper,
            color = ElectricBlueLight,
            filterPredicate = { true }
        ),
        QuizTypeItem(
            id = "challenge_quiz",
            title = "All India Challenge Quiz",
            subtitle = "Toughest Hard/Expert Level Qs",
            icon = Icons.Default.EmojiEvents,
            color = Navy900,
            filterPredicate = { it.difficulty.name == "HARD" || it.difficulty.name == "EXPERT" }
        )
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Top Header
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Navy800)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Interactive Practice Zone",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "Instant solutions, English/Hindi language toggle & detailed explanations",
                        fontSize = 12.sp,
                        color = SlateLightBg.copy(alpha = 0.85f)
                    )
                }
            }
        }

        item {
            Text(
                text = "Select Quiz Mode",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        items(quizTypes.chunked(2).size) { rowIndex ->
            val chunk = quizTypes.chunked(2)[rowIndex]
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                chunk.forEach { item ->
                    QuizModeCard(
                        item = item,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            val filtered = PreloadedExamData.allQuestions.filter(item.filterPredicate)
                            viewModel.startQuiz(item.title, filtered.ifEmpty { PreloadedExamData.allQuestions })
                        }
                    )
                }
                if (chunk.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun QuizModeCard(
    item: QuizTypeItem,
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
        Column(modifier = Modifier.padding(14.dp)) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(item.color.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = null,
                    tint = item.color,
                    modifier = Modifier.size(22.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = item.title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = item.subtitle,
                fontSize = 11.sp,
                color = SlateTextSecondary,
                lineHeight = 15.sp,
                maxLines = 2
            )
        }
    }
}
