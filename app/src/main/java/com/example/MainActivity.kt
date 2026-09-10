package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.ui.components.AppBottomBar
import com.example.ui.components.AppTopBar
import com.example.ui.screens.*
import com.example.ui.theme.DailyDriveTheme
import com.example.viewmodel.AppScreen
import com.example.viewmodel.DailyDriveViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: DailyDriveViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DailyDriveTheme {
                val currentScreen by viewModel.currentScreen.collectAsState()
                val languageMode by viewModel.languageMode.collectAsState()

                BackHandler(enabled = currentScreen != AppScreen.HOME) {
                    viewModel.goBack()
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        AppTopBar(
                            currentScreen = currentScreen,
                            languageMode = languageMode,
                            onLanguageToggle = { viewModel.setLanguageMode(it) },
                            onSearchClick = { viewModel.navigateTo(AppScreen.SEARCH) },
                            onAiClick = { viewModel.navigateTo(AppScreen.AI_ASSISTANT) },
                            onNotificationsClick = { viewModel.navigateTo(AppScreen.NOTIFICATIONS) },
                            onBackClick = { viewModel.goBack() },
                            canGoBack = currentScreen != AppScreen.HOME
                        )
                    },
                    bottomBar = {
                        AppBottomBar(
                            currentScreen = currentScreen,
                            onNavigate = { viewModel.navigateTo(it) }
                        )
                    }
                ) { innerPadding ->
                    val screenModifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)

                    when (currentScreen) {
                        AppScreen.HOME -> HomeScreen(viewModel = viewModel, modifier = screenModifier)
                        AppScreen.EXAMS -> ExamsScreen(viewModel = viewModel, modifier = screenModifier)
                        AppScreen.EXAM_DETAIL -> ExamDetailScreen(viewModel = viewModel, modifier = screenModifier)
                        AppScreen.QUIZ -> QuizScreen(viewModel = viewModel, modifier = screenModifier)
                        AppScreen.ACTIVE_QUIZ -> ActiveQuizScreen(viewModel = viewModel, modifier = screenModifier)
                        AppScreen.MOCK_TESTS -> MockTestsScreen(viewModel = viewModel, modifier = screenModifier)
                        AppScreen.ACTIVE_MOCK_TEST -> ActiveMockTestScreen(viewModel = viewModel, modifier = screenModifier)
                        AppScreen.MOCK_RESULT -> MockTestResultScreen(viewModel = viewModel, modifier = screenModifier)
                        AppScreen.CURRENT_AFFAIRS -> CurrentAffairsScreen(viewModel = viewModel, modifier = screenModifier)
                        AppScreen.CAREER -> CareerScreen(viewModel = viewModel, modifier = screenModifier)
                        AppScreen.PROFILE -> ProfileScreen(viewModel = viewModel, modifier = screenModifier)
                        AppScreen.AI_ASSISTANT -> AiAssistantScreen(viewModel = viewModel, modifier = screenModifier)
                        AppScreen.STUDY_PLANNER -> StudyPlannerScreen(viewModel = viewModel, modifier = screenModifier)
                        AppScreen.ELIGIBILITY_CHECKER -> CareerScreen(viewModel = viewModel, modifier = screenModifier)
                        AppScreen.EXAM_FINDER -> CareerScreen(viewModel = viewModel, modifier = screenModifier)
                        AppScreen.SEARCH -> SearchScreen(viewModel = viewModel, modifier = screenModifier)
                        AppScreen.BOOKMARKS_REVISION -> BookmarksRevisionScreen(viewModel = viewModel, modifier = screenModifier)
                        AppScreen.NOTIFICATIONS -> NotificationsScreen(viewModel = viewModel, modifier = screenModifier)
                    }
                }
            }
        }
    }
}

