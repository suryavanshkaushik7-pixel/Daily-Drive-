package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import com.example.data.model.CareerFaq
import com.example.ui.theme.*
import com.example.viewmodel.DailyDriveViewModel
import com.example.viewmodel.EligibilityResult

@Composable
fun CareerScreen(
    viewModel: DailyDriveViewModel,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Career Guidance", "Eligibility Calculator", "Find Your Exam")

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Tab Header
        Surface(color = Navy800, modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Career & Exam Intelligence",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "Personalized eligibility calculations, exam finder & career pathways",
                    fontSize = 12.sp,
                    color = SlateLightBg.copy(alpha = 0.85f)
                )

                Spacer(modifier = Modifier.height(12.dp))

                TabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = Navy900,
                    contentColor = ElectricBlueLight,
                    indicator = {}
                ) {
                    tabs.forEachIndexed { index, title ->
                        val isSelected = selectedTab == index
                        Tab(
                            selected = isSelected,
                            onClick = { selectedTab = index },
                            text = {
                                Text(
                                    text = title,
                                    fontSize = 11.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isSelected) ElectricBlueLight else SlateLightBg.copy(alpha = 0.7f)
                                )
                            }
                        )
                    }
                }
            }
        }

        when (selectedTab) {
            0 -> CareerGuidanceTab(viewModel = viewModel)
            1 -> EligibilityCalculatorTab(viewModel = viewModel)
            2 -> ExamFinderTab(viewModel = viewModel)
        }
    }
}

@Composable
fun CareerGuidanceTab(viewModel: DailyDriveViewModel) {
    val faqs = PreloadedExamData.careerFaqs

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text(
                text = "Frequently Asked Career Questions",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        items(faqs) { faq ->
            CareerFaqCard(faq = faq, onAskAi = {
                viewModel.sendAiMessage("Can you give me detailed career guidance and roadmap for: ${faq.question}")
                viewModel.navigateTo(com.example.viewmodel.AppScreen.AI_ASSISTANT)
            })
        }
    }
}

@Composable
fun CareerFaqCard(
    faq: CareerFaq,
    onAskAi: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .clickable { expanded = !expanded },
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
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = ElectricBlue.copy(alpha = 0.12f)
                ) {
                    Text(
                        text = faq.category,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = ElectricBlue,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }

                Icon(
                    imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null,
                    tint = SlateTextMuted
                )
            }

            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = faq.question,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = faq.answer,
                fontSize = 12.sp,
                color = SlateTextSecondary,
                lineHeight = 18.sp
            )

            AnimatedVisibility(visible = expanded) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                        .background(SlateLightBg, RoundedCornerShape(8.dp))
                        .padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text("💰 Salary Band: ${faq.salaryRange}", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = Navy900)
                    Text("📈 Growth: ${faq.careerProgression}", fontSize = 11.sp, color = SlateTextSecondary)
                    Text("📋 Eligibility: ${faq.keyEligibility}", fontSize = 11.sp, color = SlateTextSecondary)

                    Spacer(modifier = Modifier.height(4.dp))
                    TextButton(
                        onClick = onAskAi,
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = GoldAccent, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Ask AI to Personalize Roadmap", fontSize = 11.sp, color = ElectricBlue, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun EligibilityCalculatorTab(viewModel: DailyDriveViewModel) {
    val age by viewModel.userAge.collectAsState()
    val qualification by viewModel.userQualification.collectAsState()
    val percentage by viewModel.userPercentage.collectAsState()
    val category by viewModel.userCategory.collectAsState()
    val physicalFit by viewModel.userPhysicalFit.collectAsState()
    val results by viewModel.eligibilityResults.collectAsState()

    val qualifications = listOf("10th Standard", "12th Standard", "Bachelor's Degree", "Master's Degree")
    val categories = listOf("General (UR)", "OBC-NCL", "SC", "ST", "EWS")

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = CardDefaults.outlinedCardBorder()
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(
                        text = "Enter Your Profile Details",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    // Age Stepper
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Current Age: $age years", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            IconButton(
                                onClick = {
                                    if (age > 16) {
                                        viewModel.userAge.value = age - 1
                                        viewModel.calculateEligibility()
                                    }
                                }
                            ) {
                                Icon(Icons.Default.RemoveCircleOutline, contentDescription = "Decrease Age", tint = ElectricBlue)
                            }
                            IconButton(
                                onClick = {
                                    if (age < 45) {
                                        viewModel.userAge.value = age + 1
                                        viewModel.calculateEligibility()
                                    }
                                }
                            ) {
                                Icon(Icons.Default.AddCircleOutline, contentDescription = "Increase Age", tint = ElectricBlue)
                            }
                        }
                    }

                    // Qualification Selection
                    Text("Highest Education Qualification:", fontSize = 12.sp, color = SlateTextMuted)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        qualifications.forEach { q ->
                            val isSel = q == qualification
                            FilterChip(
                                selected = isSel,
                                onClick = {
                                    viewModel.userQualification.value = q
                                    viewModel.calculateEligibility()
                                },
                                label = { Text(q, fontSize = 10.sp) }
                            )
                        }
                    }

                    // Percentage Slider
                    Text("Graduation / 12th Percentage: ${percentage.toInt()}%", fontSize = 12.sp, fontWeight = FontWeight.Medium)
                    Slider(
                        value = percentage.toFloat(),
                        onValueChange = {
                            viewModel.userPercentage.value = it.toDouble()
                            viewModel.calculateEligibility()
                        },
                        valueRange = 40f..100f
                    )

                    // Category
                    Text("Category Reservation:", fontSize = 12.sp, color = SlateTextMuted)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        categories.forEach { cat ->
                            val isSel = cat == category
                            FilterChip(
                                selected = isSel,
                                onClick = {
                                    viewModel.userCategory.value = cat
                                    viewModel.calculateEligibility()
                                },
                                label = { Text(cat, fontSize = 10.sp) }
                            )
                        }
                    }

                    // Physical Fitness Checkbox (for Police / Defence)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = physicalFit,
                            onCheckedChange = {
                                viewModel.userPhysicalFit.value = it
                                viewModel.calculateEligibility()
                            }
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Meet Physical Standards (Defence / Police requirement)",
                            fontSize = 11.sp,
                            color = SlateTextSecondary
                        )
                    }
                }
            }
        }

        item {
            Text(
                text = "Eligibility Evaluation Report (${results.size} Exam Streams)",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        items(results) { res ->
            EligibilityResultCard(result = res)
        }
    }
}

@Composable
fun EligibilityResultCard(result: EligibilityResult) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = CardDefaults.outlinedCardBorder()
    ) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = result.examName,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = if (result.isEligible) EmeraldSuccess.copy(alpha = 0.15f) else RoseError.copy(alpha = 0.15f)
                ) {
                    Text(
                        text = if (result.isEligible) "ELIGIBLE ✓" else "NOT ELIGIBLE ✕",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (result.isEligible) EmeraldSuccess else RoseError,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
            }

            result.conditions.forEach { cond ->
                Text("• $cond", fontSize = 11.sp, color = SlateTextSecondary)
            }

            if (!result.isEligible && result.missingRequirements.isNotEmpty()) {
                Text(
                    text = "Missing Requirement: ${result.missingRequirements.joinToString("; ")}",
                    fontSize = 11.sp,
                    color = RoseError,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
fun ExamFinderTab(viewModel: DailyDriveViewModel) {
    var preferredField by remember { mutableStateOf("Administration / Government") }
    val fields = listOf(
        "Administration / Government",
        "Banking & Finance",
        "Defence & Uniform",
        "Technical / Engineering",
        "Teaching & Academia",
        "Legal & Judiciary"
    )

    val matchedExams = when (preferredField) {
        "Administration / Government" -> listOf("SSC CGL", "UPSC CSE", "SSC CHSL", "State PSC")
        "Banking & Finance" -> listOf("SBI PO", "IBPS PO", "RBI Grade B", "NABARD")
        "Defence & Uniform" -> listOf("UPSC NDA", "UPSC CDS", "State Police SI", "AFCAT")
        "Technical / Engineering" -> listOf("JEE Main", "GATE", "SSC JE", "RRB JE")
        "Teaching & Academia" -> listOf("CTET", "UGC NET", "State TET")
        else -> listOf("CLAT", "AILET", "Judicial Services")
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = CardDefaults.outlinedCardBorder()
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "Find Your Ideal Competitive Exam",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Select your preferred sector of interest to view curated matches:",
                        fontSize = 12.sp,
                        color = SlateTextSecondary
                    )

                    fields.forEach { f ->
                        val isSelected = f == preferredField
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isSelected) ElectricBlue.copy(alpha = 0.1f) else SlateLightBg)
                                .clickable { preferredField = f }
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = isSelected,
                                onClick = { preferredField = f },
                                colors = RadioButtonDefaults.colors(selectedColor = ElectricBlue)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = f,
                                fontSize = 13.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) ElectricBlue else Navy900
                            )
                        }
                    }
                }
            }
        }

        item {
            Text(
                text = "Recommended Exams for $preferredField",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        items(matchedExams) { exName ->
            val examInfo = PreloadedExamData.allExams.find { it.name.contains(exName, ignoreCase = true) }
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = CardDefaults.outlinedCardBorder()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = exName, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Navy900)
                        Text(text = examInfo?.conductingBody ?: "National Body", fontSize = 12.sp, color = SlateTextSecondary)
                    }

                    Button(
                        onClick = {
                            if (examInfo != null) {
                                viewModel.selectExam(examInfo)
                            } else {
                                viewModel.selectExam(PreloadedExamData.sscCgl)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text("View Syllabus", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
