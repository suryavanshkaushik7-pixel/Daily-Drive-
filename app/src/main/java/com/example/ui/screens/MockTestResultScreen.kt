package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.PreloadedExamData
import com.example.ui.theme.*
import com.example.viewmodel.AppScreen
import com.example.viewmodel.DailyDriveViewModel

@Composable
fun MockTestResultScreen(
    viewModel: DailyDriveViewModel,
    modifier: Modifier = Modifier
) {
    val evaluation by viewModel.lastTestEvaluation.collectAsState()

    if (evaluation == null) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No test result available.")
        }
        return
    }

    val res = evaluation!!
    val minutesSpent = res.timeSpentSeconds / 60
    val secondsSpent = res.timeSpentSeconds % 60

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero Scorecard Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Navy900),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = GoldAccent.copy(alpha = 0.2f)
                    ) {
                        Text(
                            text = "All India Mock Test Scorecard",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = GoldAccent,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = res.testTitle,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Score Circle / Pill
                    Row(
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "${res.score}",
                            fontSize = 38.sp,
                            fontWeight = FontWeight.Black,
                            color = ElectricBlueLight
                        )
                        Text(
                            text = " / ${res.maxScore}",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = SlateLightBg.copy(alpha = 0.8f),
                            modifier = Modifier.padding(bottom = 6.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // 3-Metric Bar: Percentage, Accuracy, Percentile
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Navy800, RoundedCornerShape(12.dp))
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        ResultPill(title = "Percentage", value = "${res.percentage}%", color = ElectricBlueLight)
                        ResultPill(title = "Accuracy", value = "${res.accuracy}%", color = EmeraldSuccess)
                        ResultPill(title = "Percentile", value = "${res.percentile}%ile", color = GoldAccent)
                    }
                }
            }
        }

        // Breakdown: Correct / Incorrect / Unattempted
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = CardDefaults.outlinedCardBorder()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Question Breakdown",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        BreakdownBox(
                            count = res.correctCount,
                            label = "Correct",
                            color = EmeraldSuccess,
                            bgColor = EmeraldLight,
                            icon = Icons.Default.CheckCircle,
                            modifier = Modifier.weight(1f)
                        )
                        BreakdownBox(
                            count = res.incorrectCount,
                            label = "Incorrect",
                            color = RoseError,
                            bgColor = RoseLight,
                            icon = Icons.Default.Cancel,
                            modifier = Modifier.weight(1f)
                        )
                        BreakdownBox(
                            count = res.unattemptedCount,
                            label = "Skipped",
                            color = SlateTextMuted,
                            bgColor = SlateLightBg,
                            icon = Icons.Default.HelpOutline,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Time Spent: $minutesSpent min $secondsSpent sec",
                        fontSize = 12.sp,
                        color = SlateTextSecondary
                    )
                }
            }
        }

        // Weak & Strong Topics Analysis
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = CardDefaults.outlinedCardBorder()
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "Topic Insights & Diagnostic",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    if (res.strongTopics.isNotEmpty()) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Icon(Icons.Default.ThumbUp, contentDescription = null, tint = EmeraldSuccess, modifier = Modifier.size(16.dp))
                            Text("Strong Areas:", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = EmeraldSuccess)
                        }
                        Text(
                            text = res.strongTopics.joinToString(", "),
                            fontSize = 12.sp,
                            color = SlateTextSecondary
                        )
                    }

                    if (res.weakTopics.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Icon(Icons.Default.WarningAmber, contentDescription = null, tint = RoseError, modifier = Modifier.size(16.dp))
                            Text("Focus Required (Weak Topics):", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = RoseError)
                        }
                        Text(
                            text = res.weakTopics.joinToString(", "),
                            fontSize = 12.sp,
                            color = SlateTextSecondary
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                    HorizontalDivider(color = SlateBorder)
                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Recommendation:",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Navy900
                    )
                    Text(
                        text = res.recommendations,
                        fontSize = 12.sp,
                        color = SlateTextSecondary,
                        lineHeight = 18.sp
                    )
                }
            }
        }

        // Action Buttons: Review Answers, Practice Weak Topics, Take Next Test
        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Button(
                    onClick = {
                        // Start practice on weak topics
                        viewModel.startQuiz(
                            "Remedial Drill - Weak Topics",
                            PreloadedExamData.allQuestions.filter { res.weakTopics.contains(it.topic) }.ifEmpty { PreloadedExamData.allQuestions }
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(Icons.Default.Psychology, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Practice Weak Topics Drill", fontWeight = FontWeight.Bold)
                }

                OutlinedButton(
                    onClick = { viewModel.navigateTo(AppScreen.MOCK_TESTS) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Back to Mock Test Center")
                }
            }
        }
    }
}

@Composable
fun ResultPill(title: String, value: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = value, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = color)
        Text(text = title, fontSize = 11.sp, color = SlateLightBg.copy(alpha = 0.7f))
    }
}

@Composable
fun BreakdownBox(
    count: Int,
    label: String,
    color: Color,
    bgColor: Color,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = bgColor,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = color, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "$count", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = color)
            Text(text = label, fontSize = 11.sp, color = Navy900)
        }
    }
}
