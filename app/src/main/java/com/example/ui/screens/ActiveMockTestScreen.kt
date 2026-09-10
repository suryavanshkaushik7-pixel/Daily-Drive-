package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
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
import com.example.ui.theme.*
import com.example.viewmodel.DailyDriveViewModel
import com.example.viewmodel.LanguageMode

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActiveMockTestScreen(
    viewModel: DailyDriveViewModel,
    modifier: Modifier = Modifier
) {
    val activeTest by viewModel.activeMockTest.collectAsState()
    val currentIndex by viewModel.mockCurrentIndex.collectAsState()
    val selectedAnswers by viewModel.mockSelectedAnswers.collectAsState()
    val markedForReview by viewModel.mockMarkedForReview.collectAsState()
    val timeRemainingSeconds by viewModel.mockTimeRemainingSeconds.collectAsState()
    val languageMode by viewModel.languageMode.collectAsState()

    var showPaletteSheet by remember { mutableStateOf(false) }
    var showSubmitDialog by remember { mutableStateOf(false) }

    if (activeTest == null) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No active test found.")
        }
        return
    }

    val test = activeTest!!
    val questions = test.questions
    val currentQuestion = questions.getOrNull(currentIndex) ?: questions.first()
    val userSelectedOption = selectedAnswers[currentQuestion.id]
    val isMarked = markedForReview.contains(currentQuestion.id)

    // Formatted live countdown timer
    val minutes = timeRemainingSeconds / 60
    val seconds = timeRemainingSeconds % 60
    val timerText = String.format("%02d:%02d", minutes, seconds)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Mock Test Top Status Bar
        Surface(
            color = Navy900,
            tonalElevation = 4.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = test.examName,
                        fontSize = 12.sp,
                        color = ElectricBlueLight,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Q ${currentIndex + 1} of ${questions.size}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                // Live Timer Badge
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = if (timeRemainingSeconds < 300) RoseError else Navy700,
                    border = null
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Timer,
                            contentDescription = "Timer",
                            tint = if (timeRemainingSeconds < 300) Color.White else GoldAccent,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = timerText,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (timeRemainingSeconds < 300) Color.White else GoldAccent
                        )
                    }
                }

                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    // Question Palette button
                    IconButton(
                        onClick = { showPaletteSheet = true },
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Navy700)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Apps,
                            contentDescription = "Palette",
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    // Submit Test Button
                    Button(
                        onClick = { showSubmitDialog = true },
                        colors = ButtonDefaults.buttonColors(containerColor = EmeraldSuccess),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text("Submit", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // Question Details & Body
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Subject & Marking Scheme Bar
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = ElectricBlue.copy(alpha = 0.12f)
                    ) {
                        Text(
                            text = "${currentQuestion.subject} • ${currentQuestion.topic}",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = ElectricBlue,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }

                    Text(
                        text = "+${String.format("%.1f", test.totalMarks.toDouble() / questions.size)} / -${test.negativeMark}",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = SlateTextSecondary
                    )
                }
            }

            // Question Text Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = CardDefaults.outlinedCardBorder()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        if (languageMode == LanguageMode.ENGLISH || languageMode == LanguageMode.BILINGUAL) {
                            Text(
                                text = currentQuestion.questionTextEn,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurface,
                                lineHeight = 22.sp
                            )
                        }

                        if (languageMode == LanguageMode.HINDI || languageMode == LanguageMode.BILINGUAL) {
                            if (languageMode == LanguageMode.BILINGUAL) {
                                Spacer(modifier = Modifier.height(8.dp))
                                HorizontalDivider(color = SlateBorder.copy(alpha = 0.5f))
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                            Text(
                                text = currentQuestion.questionTextHi,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Navy900,
                                lineHeight = 22.sp
                            )
                        }
                    }
                }
            }

            // Options List
            item {
                Text(
                    text = "Options:",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = SlateTextSecondary
                )
            }

            val optionsToDisplay = if (languageMode == LanguageMode.HINDI) {
                currentQuestion.optionsHi
            } else {
                currentQuestion.optionsEn
            }

            items(optionsToDisplay.size) { index ->
                val optionText = optionsToDisplay[index]
                val isSelected = userSelectedOption == index

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .clickable {
                            viewModel.selectMockAnswer(currentQuestion.id, index)
                        }
                        .border(
                            width = if (isSelected) 2.dp else 1.dp,
                            color = if (isSelected) ElectricBlue else SlateBorder,
                            shape = RoundedCornerShape(12.dp)
                        ),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isSelected) ElectricBlue.copy(alpha = 0.08f) else MaterialTheme.colorScheme.surface
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        RadioButton(
                            selected = isSelected,
                            onClick = { viewModel.selectMockAnswer(currentQuestion.id, index) },
                            colors = RadioButtonDefaults.colors(selectedColor = ElectricBlue)
                        )
                        Text(
                            text = optionText,
                            fontSize = 14.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = if (isSelected) ElectricBlue else MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }

        // Bottom Controls Bar: Mark for Review, Clear Response, Previous, Next
        Surface(
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 8.dp,
            border = CardDefaults.outlinedCardBorder()
        ) {
            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Mark for Review toggle
                    OutlinedButton(
                        onClick = { viewModel.toggleMarkForReview(currentQuestion.id) },
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = if (isMarked) GoldAccent else SlateTextSecondary
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(
                            imageVector = if (isMarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(if (isMarked) "Marked" else "Mark for Review", fontSize = 11.sp)
                    }

                    // Navigation (Prev & Next)
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedButton(
                            onClick = { viewModel.jumpToMockQuestion(currentIndex - 1) },
                            enabled = currentIndex > 0,
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, modifier = Modifier.size(14.dp))
                        }

                        Button(
                            onClick = {
                                if (currentIndex < questions.size - 1) {
                                    viewModel.jumpToMockQuestion(currentIndex + 1)
                                } else {
                                    showSubmitDialog = true
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(if (currentIndex < questions.size - 1) "Save & Next" else "Submit", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(14.dp))
                        }
                    }
                }
            }
        }
    }

    // Question Navigation Palette Bottom Sheet
    if (showPaletteSheet) {
        ModalBottomSheet(
            onDismissRequest = { showPaletteSheet = false },
            containerColor = MaterialTheme.colorScheme.surface
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Question Palette",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Legend
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    LegendItem(color = EmeraldSuccess, text = "Attempted")
                    LegendItem(color = SlateBorder, text = "Unattempted")
                    LegendItem(color = GoldAccent, text = "Review")
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Question Numbers Grid
                LazyVerticalGrid(
                    columns = GridCells.Fixed(5),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.heightIn(max = 280.dp)
                ) {
                    items(questions.size) { idx ->
                        val q = questions[idx]
                        val isAnswered = selectedAnswers.containsKey(q.id)
                        val isRev = markedForReview.contains(q.id)
                        val isCurrent = idx == currentIndex

                        val paletteColor = when {
                            isRev -> GoldAccent
                            isAnswered -> EmeraldSuccess
                            else -> SlateLightBg
                        }

                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(paletteColor)
                                .border(
                                    width = if (isCurrent) 2.dp else 1.dp,
                                    color = if (isCurrent) ElectricBlue else SlateBorder,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .clickable {
                                    viewModel.jumpToMockQuestion(idx)
                                    showPaletteSheet = false
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "${idx + 1}",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isAnswered || isRev) Color.White else Navy900
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        showPaletteSheet = false
                        showSubmitDialog = true
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = EmeraldSuccess),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Submit Mock Test", fontWeight = FontWeight.Bold)
                }
            }
        }
    }

    // Submit Confirmation Dialog
    if (showSubmitDialog) {
        val attemptedCount = selectedAnswers.size
        val unattemptedCount = questions.size - attemptedCount
        val reviewCount = markedForReview.size

        AlertDialog(
            onDismissRequest = { showSubmitDialog = false },
            title = { Text("Submit Mock Test?") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text("Are you sure you want to finish this test?")
                    Text("• Attempted: $attemptedCount questions", color = EmeraldSuccess, fontWeight = FontWeight.Bold)
                    Text("• Unattempted: $unattemptedCount questions", color = SlateTextSecondary)
                    Text("• Marked for Review: $reviewCount questions", color = GoldAccent)
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        showSubmitDialog = false
                        viewModel.submitMockTest()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = EmeraldSuccess)
                ) {
                    Text("Confirm Submit")
                }
            },
            dismissButton = {
                TextButton(onClick = { showSubmitDialog = false }) {
                    Text("Resume Test")
                }
            }
        )
    }
}

@Composable
fun LegendItem(color: Color, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .clip(CircleShape)
                .background(color)
        )
        Text(text = text, fontSize = 11.sp, color = SlateTextSecondary)
    }
}
