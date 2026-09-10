package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import com.example.data.model.CurrentAffairItem
import com.example.ui.theme.*
import com.example.viewmodel.DailyDriveViewModel
import com.example.viewmodel.LanguageMode

@Composable
fun CurrentAffairsScreen(
    viewModel: DailyDriveViewModel,
    modifier: Modifier = Modifier
) {
    var selectedCategory by remember { mutableStateOf("All") }
    var selectedTimeline by remember { mutableStateOf("Daily") }
    val languageMode by viewModel.languageMode.collectAsState()

    val categories = listOf(
        "All",
        "National",
        "Defence",
        "Banking and Economy",
        "Science and Technology",
        "Environment",
        "Sports and Awards"
    )

    val timelines = listOf("Daily", "Weekly", "Monthly")

    val items = PreloadedExamData.currentAffairs.filter {
        selectedCategory == "All" || it.category.contains(selectedCategory, ignoreCase = true)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Current Affairs Header
        Surface(color = Navy800, modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Current Affairs Digest",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "Bilingual summaries, exam relevance tags & linked revision MCQs",
                    fontSize = 12.sp,
                    color = SlateLightBg.copy(alpha = 0.85f)
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Timeline Filter (Daily / Weekly / Monthly)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Navy900, RoundedCornerShape(8.dp))
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    timelines.forEach { t ->
                        val isSel = t == selectedTimeline
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = if (isSel) ElectricBlue else Color.Transparent,
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(6.dp))
                                .clickable { selectedTimeline = t }
                        ) {
                            Box(
                                modifier = Modifier.padding(vertical = 6.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = t,
                                    fontSize = 12.sp,
                                    fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isSel) Color.White else SlateLightBg.copy(alpha = 0.7f)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Category Filter Chips
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(categories) { cat ->
                        val isSelected = cat == selectedCategory
                        FilterChip(
                            selected = isSelected,
                            onClick = { selectedCategory = cat },
                            label = { Text(cat, fontSize = 11.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = ElectricBlueLight,
                                selectedLabelColor = Navy900,
                                containerColor = Navy700,
                                labelColor = Color.White
                            )
                        )
                    }
                }
            }
        }

        // Current Affairs Cards
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Recent Updates (${items.size})",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Button(
                        onClick = {
                            viewModel.startQuiz(
                                "Current Affairs Rapid Quiz",
                                PreloadedExamData.allQuestions.shuffled()
                            )
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Icon(Icons.Default.Quiz, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Take CA Quiz", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            items(items) { item ->
                CurrentAffairCard(item = item, languageMode = languageMode, onAskAi = {
                    viewModel.sendAiMessage("Explain the exam relevance and static GK facts behind this current affairs event: ${item.titleEn}")
                    viewModel.navigateTo(com.example.viewmodel.AppScreen.AI_ASSISTANT)
                })
            }
        }
    }
}

@Composable
fun CurrentAffairCard(
    item: CurrentAffairItem,
    languageMode: LanguageMode,
    onAskAi: () -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .clickable { isExpanded = !isExpanded },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = CardDefaults.outlinedCardBorder(),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
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
                        text = item.category,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = ElectricBlue,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }

                Text(
                    text = item.date,
                    fontSize = 11.sp,
                    color = SlateTextMuted
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Title in selected language or bilingual
            if (languageMode == LanguageMode.ENGLISH || languageMode == LanguageMode.BILINGUAL) {
                Text(
                    text = item.titleEn,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    lineHeight = 20.sp
                )
            }

            if (languageMode == LanguageMode.HINDI || languageMode == LanguageMode.BILINGUAL) {
                if (languageMode == LanguageMode.BILINGUAL) {
                    Spacer(modifier = Modifier.height(4.dp))
                }
                Text(
                    text = item.titleHi,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Navy900,
                    lineHeight = 20.sp
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Summary
            if (languageMode == LanguageMode.ENGLISH || languageMode == LanguageMode.BILINGUAL) {
                Text(
                    text = item.summaryEn,
                    fontSize = 12.sp,
                    color = SlateTextSecondary,
                    lineHeight = 18.sp
                )
            }

            if (languageMode == LanguageMode.HINDI || languageMode == LanguageMode.BILINGUAL) {
                if (languageMode == LanguageMode.BILINGUAL) {
                    Spacer(modifier = Modifier.height(4.dp))
                }
                Text(
                    text = item.summaryHi,
                    fontSize = 12.sp,
                    color = SlateTextSecondary,
                    lineHeight = 18.sp
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Related Exams Tags
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Exams:", fontSize = 10.sp, color = SlateTextMuted, fontWeight = FontWeight.SemiBold)
                    item.relatedExams.take(3).forEach { ex ->
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = SlateLightBg
                        ) {
                            Text(
                                text = ex,
                                fontSize = 9.sp,
                                color = Navy900,
                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                            )
                        }
                    }
                }

                TextButton(
                    onClick = onAskAi,
                    contentPadding = PaddingValues(horizontal = 4.dp, vertical = 0.dp)
                ) {
                    Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = GoldAccent, modifier = Modifier.size(12.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Deep Dive", fontSize = 11.sp, color = ElectricBlue)
                }
            }
        }
    }
}
