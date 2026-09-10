package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.PreloadedExamData
import com.example.data.model.MockTest
import com.example.ui.theme.*
import com.example.viewmodel.DailyDriveViewModel

@Composable
fun MockTestsScreen(
    viewModel: DailyDriveViewModel,
    modifier: Modifier = Modifier
) {
    val mockTests = PreloadedExamData.mockTests

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Mock Test Banner
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Navy800)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = GoldAccent.copy(alpha = 0.2f)
                    ) {
                        Text(
                            text = "Real Exam Simulation",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = GoldAccent,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "All India Mock Test Series",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "Exact exam interface, real-time timer countdown, negative marking, percentile ranking & granular performance analytics.",
                        fontSize = 12.sp,
                        color = SlateLightBg.copy(alpha = 0.85f),
                        lineHeight = 17.sp
                    )
                }
            }
        }

        item {
            Text(
                text = "Available Test Series",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        items(mockTests) { test ->
            MockTestCard(
                test = test,
                onStart = { viewModel.startMockTest(test) }
            )
        }
    }
}

@Composable
fun MockTestCard(
    test: MockTest,
    onStart: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp)),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = CardDefaults.outlinedCardBorder(),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = if (test.isFullLength) ElectricBlue.copy(alpha = 0.12f) else EmeraldSuccess.copy(alpha = 0.12f)
                    ) {
                        Text(
                            text = if (test.isFullLength) "Full Length Mock" else "Sectional Booster",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (test.isFullLength) ElectricBlue else EmeraldSuccess,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = test.title,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Target: ${test.examName}",
                        fontSize = 12.sp,
                        color = SlateTextSecondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Test Metrics Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                ExamTag(icon = Icons.Default.HelpOutline, text = "${test.totalQuestions} Questions")
                ExamTag(icon = Icons.Default.Grade, text = "${test.totalMarks} Marks")
                ExamTag(icon = Icons.Default.Schedule, text = "${test.durationMinutes} Mins")
                ExamTag(icon = Icons.Default.RemoveCircleOutline, text = "-${test.negativeMark} Neg")
            }

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = SlateBorder)
            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Bilingual: English & हिंदी",
                    fontSize = 11.sp,
                    color = SlateTextMuted
                )

                Button(
                    onClick = onStart,
                    colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp)
                ) {
                    Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Attempt Test", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
