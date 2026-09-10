package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.PreloadedExamData
import com.example.data.model.CutoffTrend
import com.example.data.model.ExamInfo
import com.example.data.model.SubjectInfo
import com.example.ui.theme.*
import com.example.viewmodel.AppScreen
import com.example.viewmodel.DailyDriveViewModel

@Composable
fun ExamDetailScreen(
    viewModel: DailyDriveViewModel,
    modifier: Modifier = Modifier
) {
    val exam by viewModel.selectedExam.collectAsState()
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Overview & Pattern", "Syllabus & Weightage", "Cutoff Trends", "Important Concepts")

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Exam Hero Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Navy800),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = ElectricBlueLight.copy(alpha = 0.2f)
                    ) {
                        Text(
                            text = "${exam.category} • ${exam.examLevel} Level",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = ElectricBlueLight,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = exam.name,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "Conducted by: ${exam.conductingBody}",
                        fontSize = 13.sp,
                        color = SlateLightBg.copy(alpha = 0.85f)
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Primary Action Buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = {
                                val mock = PreloadedExamData.mockTests.find { it.examId == exam.id }
                                    ?: PreloadedExamData.mockTests.first()
                                viewModel.startMockTest(mock)
                            },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Full Mock Test", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }

                        OutlinedButton(
                            onClick = {
                                val pyqs = PreloadedExamData.allQuestions.filter { it.examId == exam.id || it.type.name.contains("PYQ") }
                                viewModel.startQuiz("${exam.name} - Previous Year Questions", pyqs)
                            },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                            border = ButtonDefaults.outlinedButtonBorder.copy(brush = androidx.compose.ui.graphics.SolidColor(ElectricBlueLight)),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Icon(Icons.Default.HistoryEdu, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("PYQ Practice", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Ask AI about this exam button
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Navy700,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                    ) {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = GoldAccent, modifier = Modifier.size(18.dp))
                                Text("Ask Daily Drive AI about ${exam.name}", fontSize = 12.sp, color = Color.White)
                            }
                            TextButton(
                                onClick = {
                                    viewModel.sendAiMessage("Explain the complete exam pattern, marking scheme, and 3-month preparation strategy for ${exam.name}.")
                                    viewModel.navigateTo(AppScreen.AI_ASSISTANT)
                                },
                                contentPadding = PaddingValues(0.dp)
                            ) {
                                Text("Ask AI", fontSize = 12.sp, color = GoldAccent, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }

        // Sub Tabs (Overview, Syllabus, Cutoffs, Concepts)
        item {
            ScrollableTabRow(
                selectedTabIndex = selectedTab,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = ElectricBlue,
                edgePadding = 0.dp
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = {
                            Text(
                                text = title,
                                fontSize = 13.sp,
                                fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    )
                }
            }
        }

        // Tab Content
        when (selectedTab) {
            0 -> {
                // Overview & Pattern
                item {
                    PatternOverviewCard(exam = exam)
                }
            }
            1 -> {
                // Syllabus & Subject-Wise Topics
                items(exam.subjects) { subject ->
                    SubjectSyllabusCard(subject = subject)
                }
            }
            2 -> {
                // Cutoff Trends
                item {
                    CutoffTrendsCard(trends = exam.previousYearCutoffTrends)
                }
            }
            3 -> {
                // Important Concepts & Weightage
                item {
                    ConceptsCard(concepts = exam.importantConcepts)
                }
            }
        }
    }
}

@Composable
fun PatternOverviewCard(exam: ExamInfo) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = CardDefaults.outlinedCardBorder()
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(
                text = "Official Exam Pattern Summary",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = exam.patternSummary,
                fontSize = 13.sp,
                color = SlateTextSecondary,
                lineHeight = 20.sp
            )

            HorizontalDivider(color = SlateBorder)

            InfoRow(label = "Total Questions", value = "${exam.totalQuestions} Questions")
            InfoRow(label = "Total Marks", value = "${exam.totalMarks} Marks")
            InfoRow(label = "Duration", value = "${exam.durationMinutes} Minutes")
            InfoRow(label = "Marking Scheme", value = exam.markingScheme)
            InfoRow(label = "Negative Marking", value = exam.negativeMarking)
            InfoRow(label = "Eligibility Qualification", value = exam.qualification)
            InfoRow(label = "Age Limit", value = exam.ageLimit)
            InfoRow(label = "Official Notification", value = exam.officialNotificationDate)
            InfoRow(label = "Official Portal", value = exam.officialWebsite)
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            color = SlateTextMuted,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1.5f)
        )
    }
}

@Composable
fun SubjectSyllabusCard(subject: SubjectInfo) {
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
                Text(
                    text = subject.name,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = ElectricBlue.copy(alpha = 0.12f)
                ) {
                    Text(
                        text = "Weightage: ${subject.weightagePercent}% (${subject.marks} Marks)",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = ElectricBlue,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Key High-Yield Topics & Shift Frequencies:",
                fontSize = 12.sp,
                color = SlateTextMuted,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(6.dp))

            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                subject.topics.forEach { topic ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(SlateLightBg, RoundedCornerShape(8.dp))
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = topic.name,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Navy900
                            )
                            Text(
                                text = "Frequency: ${topic.pyqFrequency}",
                                fontSize = 11.sp,
                                color = SlateTextSecondary
                            )
                        }
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = when (topic.importance) {
                                "High" -> RoseError.copy(alpha = 0.15f)
                                "Medium" -> GoldAccent.copy(alpha = 0.2f)
                                else -> EmeraldSuccess.copy(alpha = 0.15f)
                            }
                        ) {
                            Text(
                                text = "${topic.importance} Yield",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = when (topic.importance) {
                                    "High" -> RoseError
                                    "Medium" -> GoldAccent
                                    else -> EmeraldSuccess
                                },
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CutoffTrendsCard(trends: List<CutoffTrend>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = CardDefaults.outlinedCardBorder()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Verified Cutoff Trends (Official Data)",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "Cutoff scores vary based on shift difficulty and vacancies.",
                fontSize = 11.sp,
                color = SlateTextMuted
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Table Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Navy800, RoundedCornerShape(6.dp))
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Exam Stage / Year", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Text("Category", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Text("Cutoff Score", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = GoldAccent)
            }

            Spacer(modifier = Modifier.height(4.dp))

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                trends.forEach { trend ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(SlateLightBg, RoundedCornerShape(6.dp))
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(trend.year, fontSize = 12.sp, color = Navy900, fontWeight = FontWeight.Medium)
                        Text(trend.category, fontSize = 12.sp, color = SlateTextSecondary)
                        Text(trend.cutoffScore, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Navy900)
                    }
                }
            }
        }
    }
}

@Composable
fun ConceptsCard(concepts: List<String>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = CardDefaults.outlinedCardBorder()
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(
                text = "Frequently Tested Concepts",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "Mastering these core principles guarantees 20-30% of standard paper questions:",
                fontSize = 12.sp,
                color = SlateTextSecondary
            )

            concepts.forEach { concept ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(SlateLightBg, RoundedCornerShape(8.dp))
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = EmeraldSuccess, modifier = Modifier.size(16.dp))
                    Text(text = concept, fontSize = 13.sp, color = Navy900, fontWeight = FontWeight.Medium)
                }
            }
        }
    }
}
