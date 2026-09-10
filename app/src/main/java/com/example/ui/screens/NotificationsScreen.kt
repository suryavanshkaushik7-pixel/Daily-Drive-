package com.example.ui.screens

import androidx.compose.foundation.background
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
import com.example.data.model.ExamNotification
import com.example.ui.theme.*
import com.example.viewmodel.DailyDriveViewModel

@Composable
fun NotificationsScreen(
    viewModel: DailyDriveViewModel,
    modifier: Modifier = Modifier
) {
    val notifs = PreloadedExamData.notifications

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Navy800)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Official Exam Notifications & Alerts",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "Verified exam schedules, admit card releases, application deadlines & results",
                        fontSize = 12.sp,
                        color = SlateLightBg.copy(alpha = 0.85f)
                    )
                }
            }
        }

        items(notifs) { notif ->
            NotificationCard(notif = notif)
        }
    }
}

@Composable
fun NotificationCard(notif: ExamNotification) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = CardDefaults.outlinedCardBorder()
    ) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = if (notif.isUrgent) RoseError.copy(alpha = 0.15f) else ElectricBlue.copy(alpha = 0.15f)
                ) {
                    Text(
                        text = notif.type,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (notif.isUrgent) RoseError else ElectricBlue,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }

                Text(
                    text = notif.date,
                    fontSize = 11.sp,
                    color = SlateTextMuted
                )
            }

            Text(
                text = notif.examName,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = ElectricBlue
            )

            Text(
                text = notif.title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = notif.details,
                fontSize = 12.sp,
                color = SlateTextSecondary,
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = "Official Website: ${notif.officialLink}",
                fontSize = 11.sp,
                color = ElectricBlue,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
