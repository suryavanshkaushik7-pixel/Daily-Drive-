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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.StudyTaskEntity
import com.example.ui.theme.*
import com.example.viewmodel.DailyDriveViewModel

@Composable
fun StudyPlannerScreen(
    viewModel: DailyDriveViewModel,
    modifier: Modifier = Modifier
) {
    val tasks by viewModel.studyTasks.collectAsState()
    var showAddTaskDialog by remember { mutableStateOf(false) }

    var newTaskTitle by remember { mutableStateOf("") }
    var newTaskSubject by remember { mutableStateOf("Quantitative Aptitude") }
    var newTaskMinutes by remember { mutableStateOf("60") }
    var newTaskQuestions by remember { mutableStateOf("25") }

    val completedCount = tasks.count { it.isCompleted }
    val totalCount = tasks.size.coerceAtLeast(1)
    val progress = (completedCount.toFloat() / totalCount.toFloat()).coerceIn(0f, 1f)

    // Pre-populate standard target tasks if empty
    LaunchedEffect(tasks.isEmpty()) {
        if (tasks.isEmpty()) {
            viewModel.addStudyTask("Solve 25 Quantitative Aptitude PYQs", "Quantitative Aptitude", 45, 25, "DAILY")
            viewModel.addStudyTask("Review Current Affairs September Digest", "General Awareness", 30, 15, "DAILY")
            viewModel.addStudyTask("Practice 3 Seating Arrangement Puzzles", "Reasoning Ability", 40, 15, "DAILY")
            viewModel.addStudyTask("Attempt 1 Full Mock Test & Error Log", "All Subjects", 60, 50, "WEEKLY")
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddTaskDialog = true },
                containerColor = ElectricBlue,
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Task")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Planner Summary Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Navy800)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "Smart Study Planner",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Text(
                                    text = "$completedCount of ${tasks.size} Daily Targets Completed",
                                    fontSize = 12.sp,
                                    color = SlateLightBg.copy(alpha = 0.8f)
                                )
                            }
                            Text(
                                text = "${(progress * 100).toInt()}%",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Black,
                                color = GoldAccent
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        LinearProgressIndicator(
                            progress = { progress },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(RoundedCornerShape(4.dp)),
                            color = ElectricBlueLight,
                            trackColor = Navy700
                        )
                    }
                }
            }

            item {
                Text(
                    text = "Scheduled Targets & Drills",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            items(tasks) { task ->
                StudyTaskRow(
                    task = task,
                    onToggle = { viewModel.toggleStudyTask(task) },
                    onDelete = { viewModel.deleteStudyTask(task.id) }
                )
            }
        }
    }

    if (showAddTaskDialog) {
        AlertDialog(
            onDismissRequest = { showAddTaskDialog = false },
            title = { Text("Add Study Target") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(
                        value = newTaskTitle,
                        onValueChange = { newTaskTitle = it },
                        label = { Text("Task / Topic Name") },
                        placeholder = { Text("e.g. Solve 20 Trigonometry Qs") }
                    )
                    OutlinedTextField(
                        value = newTaskSubject,
                        onValueChange = { newTaskSubject = it },
                        label = { Text("Subject") }
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = newTaskMinutes,
                            onValueChange = { newTaskMinutes = it },
                            label = { Text("Minutes") },
                            modifier = Modifier.weight(1f)
                        )
                        OutlinedTextField(
                            value = newTaskQuestions,
                            onValueChange = { newTaskQuestions = it },
                            label = { Text("Questions") },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (newTaskTitle.isNotBlank()) {
                            viewModel.addStudyTask(
                                title = newTaskTitle,
                                subject = newTaskSubject,
                                minutes = newTaskMinutes.toIntOrNull() ?: 30,
                                targetQuestions = newTaskQuestions.toIntOrNull() ?: 15,
                                planType = "DAILY"
                            )
                            newTaskTitle = ""
                            showAddTaskDialog = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue)
                ) {
                    Text("Add Target")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddTaskDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun StudyTaskRow(
    task: StudyTaskEntity,
    onToggle: () -> Unit,
    onDelete: () -> Unit
) {
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
            Checkbox(
                checked = task.isCompleted,
                onCheckedChange = { onToggle() },
                colors = CheckboxDefaults.colors(checkedColor = EmeraldSuccess)
            )

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = task.title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (task.isCompleted) SlateTextMuted else MaterialTheme.colorScheme.onSurface,
                    style = if (task.isCompleted) androidx.compose.ui.text.TextStyle(textDecoration = androidx.compose.ui.text.style.TextDecoration.LineThrough) else androidx.compose.ui.text.TextStyle()
                )
                Text(
                    text = "${task.subject} • ${task.targetMinutes} mins • ${task.targetQuestions} Qs",
                    fontSize = 11.sp,
                    color = SlateTextSecondary
                )
            }

            IconButton(onClick = onDelete) {
                Icon(Icons.Default.DeleteOutline, contentDescription = "Delete", tint = SlateTextMuted, modifier = Modifier.size(18.dp))
            }
        }
    }
}
