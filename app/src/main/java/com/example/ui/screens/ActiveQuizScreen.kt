package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.example.data.model.DifficultyLevel
import com.example.data.model.Question
import com.example.data.model.QuestionType
import com.example.ui.theme.*
import com.example.viewmodel.AppScreen
import com.example.viewmodel.DailyDriveViewModel
import com.example.viewmodel.LanguageMode

@Composable
fun ActiveQuizScreen(
    viewModel: DailyDriveViewModel,
    modifier: Modifier = Modifier
) {
    val quizTitle by viewModel.activeQuizTitle.collectAsState()
    val questions by viewModel.quizQuestions.collectAsState()
    val currentIndex by viewModel.currentQuizIndex.collectAsState()
    val selectedAnswers by viewModel.quizSelectedAnswers.collectAsState()
    val languageMode by viewModel.languageMode.collectAsState()
    val bookmarks by viewModel.bookmarks.collectAsState()

    if (questions.isEmpty()) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No questions found for this quiz session.")
        }
        return
    }

    val currentQuestion = questions.getOrNull(currentIndex) ?: questions.first()
    val userSelectedOption = selectedAnswers[currentQuestion.id]
    val isAnswered = userSelectedOption != null
    val isBookmarked = bookmarks.any { it.questionId == currentQuestion.id }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Quiz Header with Title & Question Progress
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
                        Column {
                            Text(
                                text = quizTitle,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Navy900
                            )
                            Text(
                                text = "${currentQuestion.examName} • ${currentQuestion.subject}",
                                fontSize = 12.sp,
                                color = ElectricBlue
                            )
                        }

                        IconButton(onClick = { viewModel.toggleBookmark(currentQuestion) }) {
                            Icon(
                                imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                contentDescription = "Bookmark",
                                tint = if (isBookmarked) GoldAccent else SlateTextMuted
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Question ${currentIndex + 1} of ${questions.size}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = SlateTextSecondary
                        )
                        Text(
                            text = currentQuestion.topic,
                            fontSize = 11.sp,
                            color = SlateTextMuted
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    LinearProgressIndicator(
                        progress = { (currentIndex + 1).toFloat() / questions.size.toFloat() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp)),
                        color = ElectricBlue,
                        trackColor = SlateBorder
                    )
                }
            }
        }

        // Question Metadata Tags (Difficulty & Type)
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Difficulty Badge
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = when (currentQuestion.difficulty) {
                        DifficultyLevel.EASY -> EmeraldSuccess.copy(alpha = 0.15f)
                        DifficultyLevel.MODERATE -> GoldAccent.copy(alpha = 0.2f)
                        DifficultyLevel.HARD -> RoseError.copy(alpha = 0.15f)
                        DifficultyLevel.EXPERT -> Navy900.copy(alpha = 0.15f)
                    }
                ) {
                    Text(
                        text = currentQuestion.difficulty.label,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = when (currentQuestion.difficulty) {
                            DifficultyLevel.EASY -> EmeraldSuccess
                            DifficultyLevel.MODERATE -> GoldAccent
                            DifficultyLevel.HARD -> RoseError
                            DifficultyLevel.EXPERT -> Navy900
                        },
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }

                // Type Badge (PYQ / Exam Pattern)
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = ElectricBlue.copy(alpha = 0.12f)
                ) {
                    Text(
                        text = currentQuestion.type.label,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = ElectricBlue,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }

                if (!currentQuestion.year.isNullOrBlank()) {
                    Text(
                        text = currentQuestion.year,
                        fontSize = 11.sp,
                        color = SlateTextMuted
                    )
                }
            }
        }

        // Question Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = CardDefaults.outlinedCardBorder()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // Show English text if English or Bilingual
                    if (languageMode == LanguageMode.ENGLISH || languageMode == LanguageMode.BILINGUAL) {
                        Text(
                            text = currentQuestion.questionTextEn,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface,
                            lineHeight = 22.sp
                        )
                    }

                    // Show Hindi text if Hindi or Bilingual
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

        // Options List (4 Options)
        item {
            Text(
                text = "Select Correct Option:",
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
            val isCorrect = index == currentQuestion.correctOptionIndex

            val (bgColor, borderColor, textColor) = when {
                !isAnswered -> Triple(MaterialTheme.colorScheme.surface, SlateBorder, MaterialTheme.colorScheme.onSurface)
                isSelected && isCorrect -> Triple(EmeraldLight, EmeraldSuccess, EmeraldDark)
                isSelected && !isCorrect -> Triple(RoseLight, RoseError, RoseDark)
                isCorrect -> Triple(EmeraldLight.copy(alpha = 0.5f), EmeraldSuccess, EmeraldDark)
                else -> Triple(MaterialTheme.colorScheme.surface, SlateBorder, SlateTextMuted)
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .clickable(enabled = !isAnswered) {
                        viewModel.selectQuizAnswer(currentQuestion.id, index)
                    }
                    .border(
                        width = if (isSelected || (isAnswered && isCorrect)) 2.dp else 1.dp,
                        color = borderColor,
                        shape = RoundedCornerShape(12.dp)
                    ),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = bgColor)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(borderColor.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        val optionLabel = ('A' + index).toString()
                        Text(
                            text = optionLabel,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = textColor
                        )
                    }

                    Text(
                        text = optionText,
                        fontSize = 14.sp,
                        fontWeight = if (isSelected || (isAnswered && isCorrect)) FontWeight.Bold else FontWeight.Normal,
                        color = textColor,
                        modifier = Modifier.weight(1f)
                    )

                    if (isAnswered) {
                        if (isCorrect) {
                            Icon(Icons.Default.CheckCircle, contentDescription = "Correct", tint = EmeraldSuccess, modifier = Modifier.size(20.dp))
                        } else if (isSelected) {
                            Icon(Icons.Default.Cancel, contentDescription = "Incorrect", tint = RoseError, modifier = Modifier.size(20.dp))
                        }
                    }
                }
            }
        }

        // Detailed Explanation Card (Revealed after user answers)
        if (isAnswered) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Navy800.copy(alpha = 0.05f)),
                    border = CardDefaults.outlinedCardBorder()
                ) {
                    Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(Icons.Default.Lightbulb, contentDescription = null, tint = GoldAccent, modifier = Modifier.size(18.dp))
                                Text(
                                    text = "Explanation & Key Concept",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Navy900
                                )
                            }

                            // Ask AI Shortcut
                            TextButton(
                                onClick = {
                                    viewModel.sendAiMessage("Explain this question in detail with background context and shortcut tricks: ${currentQuestion.questionTextEn}")
                                    viewModel.navigateTo(AppScreen.AI_ASSISTANT)
                                },
                                contentPadding = PaddingValues(0.dp)
                            ) {
                                Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = GoldAccent, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Ask AI Tutor", fontSize = 12.sp, color = ElectricBlue, fontWeight = FontWeight.Bold)
                            }
                        }

                        if (languageMode == LanguageMode.ENGLISH || languageMode == LanguageMode.BILINGUAL) {
                            Text(
                                text = currentQuestion.explanationEn,
                                fontSize = 13.sp,
                                color = SlateTextSecondary,
                                lineHeight = 19.sp
                            )
                        }

                        if (languageMode == LanguageMode.HINDI || languageMode == LanguageMode.BILINGUAL) {
                            if (languageMode == LanguageMode.BILINGUAL) {
                                Spacer(modifier = Modifier.height(4.dp))
                            }
                            Text(
                                text = currentQuestion.explanationHi,
                                fontSize = 13.sp,
                                color = SlateTextSecondary,
                                lineHeight = 19.sp
                            )
                        }

                        if (!currentQuestion.sourceInfo.isNullOrBlank()) {
                            Text(
                                text = "Source: ${currentQuestion.sourceInfo}",
                                fontSize = 11.sp,
                                color = SlateTextMuted,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
        }

        // Navigation Controls (Prev & Next)
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(
                    onClick = { viewModel.prevQuizQuestion() },
                    enabled = currentIndex > 0,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Previous")
                }

                Button(
                    onClick = {
                        if (currentIndex < questions.size - 1) {
                            viewModel.nextQuizQuestion()
                        } else {
                            // Quiz Completed
                            viewModel.navigateTo(AppScreen.QUIZ)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(if (currentIndex < questions.size - 1) "Next Question" else "Finish Quiz", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp))
                }
            }
        }
    }
}
