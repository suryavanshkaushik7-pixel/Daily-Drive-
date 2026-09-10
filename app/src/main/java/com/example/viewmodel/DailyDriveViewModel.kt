package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.ai.ChatMessage
import com.example.data.ai.DailyDriveAiAssistant
import com.example.data.local.*
import com.example.data.model.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

enum class AppScreen {
    HOME,
    EXAMS,
    QUIZ,
    MOCK_TESTS,
    CURRENT_AFFAIRS,
    CAREER,
    PROFILE,
    EXAM_DETAIL,
    ACTIVE_QUIZ,
    ACTIVE_MOCK_TEST,
    MOCK_RESULT,
    AI_ASSISTANT,
    STUDY_PLANNER,
    ELIGIBILITY_CHECKER,
    EXAM_FINDER,
    SEARCH,
    BOOKMARKS_REVISION,
    NOTIFICATIONS
}

enum class LanguageMode {
    ENGLISH,
    HINDI,
    BILINGUAL
}

data class MockTestEvaluation(
    val testTitle: String,
    val examName: String,
    val totalQuestions: Int,
    val attemptedCount: Int,
    val correctCount: Int,
    val incorrectCount: Int,
    val unattemptedCount: Int,
    val score: Double,
    val maxScore: Double,
    val percentage: Double,
    val accuracy: Double,
    val percentile: Double,
    val timeSpentSeconds: Long,
    val weakTopics: List<String>,
    val strongTopics: List<String>,
    val recommendations: String,
    val userAnswers: Map<String, Int>
)

data class EligibilityResult(
    val examName: String,
    val isEligible: Boolean,
    val conditions: List<String>,
    val missingRequirements: List<String>,
    val officialNotificationLink: String
)

class DailyDriveViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ExamDataRepository(application)

    // Current Navigation Screen
    private val _currentScreen = MutableStateFlow(AppScreen.HOME)
    val currentScreen: StateFlow<AppScreen> = _currentScreen.asStateFlow()

    // Navigation history stack for back button
    private val screenBackStack = mutableListOf<AppScreen>()

    // Language Mode
    private val _languageMode = MutableStateFlow(LanguageMode.ENGLISH)
    val languageMode: StateFlow<LanguageMode> = _languageMode.asStateFlow()

    // Selected Exam for detail screen
    private val _selectedExam = MutableStateFlow(PreloadedExamData.sscCgl)
    val selectedExam: StateFlow<ExamInfo> = _selectedExam.asStateFlow()

    // Active Quiz Session
    private val _activeQuizTitle = MutableStateFlow("Daily Challenge Quiz")
    val activeQuizTitle: StateFlow<String> = _activeQuizTitle.asStateFlow()

    private val _quizQuestions = MutableStateFlow<List<Question>>(emptyList())
    val quizQuestions: StateFlow<List<Question>> = _quizQuestions.asStateFlow()

    private val _currentQuizIndex = MutableStateFlow(0)
    val currentQuizIndex: StateFlow<Int> = _currentQuizIndex.asStateFlow()

    private val _quizSelectedAnswers = MutableStateFlow<Map<String, Int>>(emptyMap())
    val quizSelectedAnswers: StateFlow<Map<String, Int>> = _quizSelectedAnswers.asStateFlow()

    // Active Mock Test Session
    private val _activeMockTest = MutableStateFlow<MockTest?>(null)
    val activeMockTest: StateFlow<MockTest?> = _activeMockTest.asStateFlow()

    private val _mockCurrentIndex = MutableStateFlow(0)
    val mockCurrentIndex: StateFlow<Int> = _mockCurrentIndex.asStateFlow()

    private val _mockSelectedAnswers = MutableStateFlow<Map<String, Int>>(emptyMap())
    val mockSelectedAnswers: StateFlow<Map<String, Int>> = _mockSelectedAnswers.asStateFlow()

    private val _mockMarkedForReview = MutableStateFlow<Set<String>>(emptySet())
    val mockMarkedForReview: StateFlow<Set<String>> = _mockMarkedForReview.asStateFlow()

    private val _mockTimeRemainingSeconds = MutableStateFlow(3600L)
    val mockTimeRemainingSeconds: StateFlow<Long> = _mockTimeRemainingSeconds.asStateFlow()

    private var mockTimerJob: Job? = null

    // Completed Mock Test Result
    private val _lastTestEvaluation = MutableStateFlow<MockTestEvaluation?>(null)
    val lastTestEvaluation: StateFlow<MockTestEvaluation?> = _lastTestEvaluation.asStateFlow()

    // AI Study Assistant Chat
    private val _chatMessages = MutableStateFlow<List<ChatMessage>>(
        listOf(
            ChatMessage(
                id = "init",
                sender = "ai",
                text = "Hello! I am Daily Drive AI, your personal competitive exam mentor.\nAsk me anything: concept explanations, why an answer is correct, syllabus breakdowns, 10 practice questions, or career advice!",
                isVerifiedOfficialSource = true
            )
        )
    )
    val chatMessages: StateFlow<List<ChatMessage>> = _chatMessages.asStateFlow()

    private val _isAiThinking = MutableStateFlow(false)
    val isAiThinking: StateFlow<Boolean> = _isAiThinking.asStateFlow()

    // Search Query
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // Eligibility Checker Inputs & State
    val userAge = MutableStateFlow(22)
    val userQualification = MutableStateFlow("Bachelor's Degree")
    val userGraduationSubject = MutableStateFlow("Science / Engineering")
    val userPercentage = MutableStateFlow(65.0)
    val userCategory = MutableStateFlow("General (UR)")
    val userPhysicalFit = MutableStateFlow(true)

    private val _eligibilityResults = MutableStateFlow<List<EligibilityResult>>(emptyList())
    val eligibilityResults: StateFlow<List<EligibilityResult>> = _eligibilityResults.asStateFlow()

    // Room Database Observables
    val bookmarks: StateFlow<List<BookmarkEntity>> = repository.bookmarks
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val mockAttempts: StateFlow<List<MockTestAttemptEntity>> = repository.mockAttempts
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val studyTasks: StateFlow<List<StudyTaskEntity>> = repository.studyTasks
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val userStats: StateFlow<UserStatsEntity> = repository.userStats
        .map { it ?: UserStatsEntity() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UserStatsEntity())

    init {
        // Evaluate default eligibility on startup
        calculateEligibility()
    }

    fun navigateTo(screen: AppScreen) {
        if (_currentScreen.value != screen) {
            screenBackStack.add(_currentScreen.value)
            _currentScreen.value = screen
        }
    }

    fun goBack(): Boolean {
        return if (screenBackStack.isNotEmpty()) {
            _currentScreen.value = screenBackStack.removeAt(screenBackStack.size - 1)
            true
        } else {
            if (_currentScreen.value != AppScreen.HOME) {
                _currentScreen.value = AppScreen.HOME
                true
            } else {
                false
            }
        }
    }

    fun setLanguageMode(mode: LanguageMode) {
        _languageMode.value = mode
    }

    fun selectExam(exam: ExamInfo) {
        _selectedExam.value = exam
        navigateTo(AppScreen.EXAM_DETAIL)
    }

    // --- QUIZ LAUNCH & HANDLING ---
    fun startQuiz(title: String, questions: List<Question>) {
        _activeQuizTitle.value = title
        _quizQuestions.value = questions.ifEmpty { repository.getAllQuestions() }
        _currentQuizIndex.value = 0
        _quizSelectedAnswers.value = emptyMap()
        navigateTo(AppScreen.ACTIVE_QUIZ)
    }

    fun selectQuizAnswer(questionId: String, optionIndex: Int) {
        val current = _quizSelectedAnswers.value.toMutableMap()
        if (!current.containsKey(questionId)) {
            current[questionId] = optionIndex
            _quizSelectedAnswers.value = current

            val q = _quizQuestions.value.find { it.id == questionId }
            val isCorrect = q?.correctOptionIndex == optionIndex
            viewModelScope.launch {
                repository.addXpAndQuestion(isCorrect)
            }
        }
    }

    fun nextQuizQuestion() {
        if (_currentQuizIndex.value < _quizQuestions.value.size - 1) {
            _currentQuizIndex.value += 1
        }
    }

    fun prevQuizQuestion() {
        if (_currentQuizIndex.value > 0) {
            _currentQuizIndex.value -= 1
        }
    }

    // --- MOCK TEST LIFECYCLE ---
    fun startMockTest(mockTest: MockTest) {
        _activeMockTest.value = mockTest
        _mockCurrentIndex.value = 0
        _mockSelectedAnswers.value = emptyMap()
        _mockMarkedForReview.value = emptySet()
        _mockTimeRemainingSeconds.value = (mockTest.durationMinutes * 60).toLong()

        mockTimerJob?.cancel()
        mockTimerJob = viewModelScope.launch {
            while (_mockTimeRemainingSeconds.value > 0) {
                delay(1000)
                _mockTimeRemainingSeconds.value -= 1
            }
            // Auto submit when time runs out
            submitMockTest()
        }

        navigateTo(AppScreen.ACTIVE_MOCK_TEST)
    }

    fun selectMockAnswer(questionId: String, optionIndex: Int) {
        val current = _mockSelectedAnswers.value.toMutableMap()
        current[questionId] = optionIndex
        _mockSelectedAnswers.value = current
    }

    fun toggleMarkForReview(questionId: String) {
        val current = _mockMarkedForReview.value.toMutableSet()
        if (current.contains(questionId)) {
            current.remove(questionId)
        } else {
            current.add(questionId)
        }
        _mockMarkedForReview.value = current
    }

    fun jumpToMockQuestion(index: Int) {
        val questions = _activeMockTest.value?.questions ?: emptyList()
        if (index in questions.indices) {
            _mockCurrentIndex.value = index
        }
    }

    fun submitMockTest() {
        mockTimerJob?.cancel()
        val test = _activeMockTest.value ?: return
        val answers = _mockSelectedAnswers.value
        val questions = test.questions

        var correct = 0
        var incorrect = 0
        var unattempted = 0
        val weakTopics = mutableSetOf<String>()
        val strongTopics = mutableSetOf<String>()

        for (q in questions) {
            val userChoice = answers[q.id]
            if (userChoice == null) {
                unattempted++
            } else if (userChoice == q.correctOptionIndex) {
                correct++
                strongTopics.add(q.topic)
            } else {
                incorrect++
                weakTopics.add(q.topic)
            }
        }

        val totalMarksForCorrect = (test.totalMarks.toDouble() / questions.size)
        val score = (correct * totalMarksForCorrect) - (incorrect * test.negativeMark)
        val maxScore = test.totalMarks.toDouble()
        val percentage = (score / maxScore * 100.0).coerceAtLeast(0.0)
        val accuracy = if (correct + incorrect > 0) (correct.toDouble() / (correct + incorrect) * 100.0) else 0.0
        val percentile = (percentage * 0.95 + 12.0).coerceIn(40.0, 99.8)
        val timeSpent = (test.durationMinutes * 60) - _mockTimeRemainingSeconds.value

        val evaluation = MockTestEvaluation(
            testTitle = test.title,
            examName = test.examName,
            totalQuestions = questions.size,
            attemptedCount = correct + incorrect,
            correctCount = correct,
            incorrectCount = incorrect,
            unattemptedCount = unattempted,
            score = String.format("%.2f", score).toDouble(),
            maxScore = maxScore,
            percentage = String.format("%.1f", percentage).toDouble(),
            accuracy = String.format("%.1f", accuracy).toDouble(),
            percentile = String.format("%.1f", percentile).toDouble(),
            timeSpentSeconds = timeSpent.coerceAtLeast(0L),
            weakTopics = weakTopics.toList(),
            strongTopics = strongTopics.toList(),
            recommendations = if (weakTopics.isNotEmpty()) {
                "Your accuracy in ${weakTopics.take(2).joinToString(", ")} is below threshold. Practice these topics in the PYQ library next."
            } else {
                "Outstanding accuracy! Practice higher difficulty questions to improve speed."
            },
            userAnswers = answers
        )

        _lastTestEvaluation.value = evaluation

        // Persist to Room
        viewModelScope.launch {
            repository.recordTestAttempt(
                MockTestAttemptEntity(
                    testId = test.id,
                    testTitle = test.title,
                    examName = test.examName,
                    score = evaluation.score,
                    maxScore = evaluation.maxScore,
                    percentage = evaluation.percentage,
                    correctCount = evaluation.correctCount,
                    incorrectCount = evaluation.incorrectCount,
                    unattemptedCount = evaluation.unattemptedCount,
                    totalQuestions = evaluation.totalQuestions,
                    timeSpentSeconds = evaluation.timeSpentSeconds,
                    accuracy = evaluation.accuracy,
                    subjectWiseBreakdown = "Correct: $correct, Incorrect: $incorrect",
                    weakTopics = weakTopics.joinToString(", "),
                    strongTopics = strongTopics.joinToString(", ")
                )
            )
        }

        navigateTo(AppScreen.MOCK_RESULT)
    }

    // --- BOOKMARKING ---
    fun toggleBookmark(question: Question) {
        viewModelScope.launch {
            val isAlready = bookmarks.value.any { it.questionId == question.id }
            if (isAlready) {
                repository.removeBookmark(question.id)
            } else {
                repository.toggleBookmark(question)
            }
        }
    }

    // --- STUDY TASKS ---
    fun addStudyTask(title: String, subject: String, minutes: Int, targetQuestions: Int, planType: String) {
        viewModelScope.launch {
            repository.addTask(
                StudyTaskEntity(
                    title = title,
                    subject = subject,
                    targetMinutes = minutes,
                    targetQuestions = targetQuestions,
                    planType = planType
                )
            )
        }
    }

    fun toggleStudyTask(task: StudyTaskEntity) {
        viewModelScope.launch {
            repository.toggleTaskCompletion(task)
        }
    }

    fun deleteStudyTask(taskId: Long) {
        viewModelScope.launch {
            repository.deleteTask(taskId)
        }
    }

    // --- AI ASSISTANT ---
    fun sendAiMessage(promptText: String) {
        if (promptText.isBlank()) return
        val userMsg = ChatMessage(
            id = System.currentTimeMillis().toString(),
            sender = "user",
            text = promptText
        )
        _chatMessages.value = _chatMessages.value + userMsg
        _isAiThinking.value = true

        viewModelScope.launch {
            val responseText = DailyDriveAiAssistant.askAssistant(
                prompt = promptText,
                contextExam = _selectedExam.value.name
            )
            _isAiThinking.value = false
            _chatMessages.value = _chatMessages.value + ChatMessage(
                id = (System.currentTimeMillis() + 1).toString(),
                sender = "ai",
                text = responseText,
                isVerifiedOfficialSource = true,
                relatedExam = _selectedExam.value.name
            )
        }
    }

    // --- ELIGIBILITY CALCULATOR ---
    fun calculateEligibility() {
        val age = userAge.value
        val qual = userQualification.value
        val pct = userPercentage.value
        val isFit = userPhysicalFit.value

        val results = mutableListOf<EligibilityResult>()

        // SSC CGL
        val cglEligible = (age in 18..32) && (qual.contains("Degree") || qual.contains("Graduat"))
        results.add(
            EligibilityResult(
                examName = "SSC CGL",
                isEligible = cglEligible,
                conditions = listOf("Graduation in any discipline", "Age: 18 - 32 years (Relaxations for reserved categories)"),
                missingRequirements = buildList {
                    if (age < 18 || age > 32) add("Age must be between 18 and 32 years (Current: $age)")
                    if (!qual.contains("Degree") && !qual.contains("Graduat")) add("Requires a recognized Bachelor's Degree")
                },
                officialNotificationLink = "https://ssc.gov.in"
            )
        )

        // SBI / IBPS PO
        val bankEligible = (age in 20..30) && (qual.contains("Degree") || qual.contains("Graduat"))
        results.add(
            EligibilityResult(
                examName = "SBI / IBPS PO (Banking)",
                isEligible = bankEligible,
                conditions = listOf("Graduation in any discipline", "Age: 20 - 30 years as per official notification date"),
                missingRequirements = buildList {
                    if (age < 20 || age > 30) add("Age must be between 20 and 30 years (Current: $age)")
                    if (!qual.contains("Degree") && !qual.contains("Graduat")) add("Requires Bachelor's Degree")
                },
                officialNotificationLink = "https://sbi.co.in/careers"
            )
        )

        // UPSC CSE
        val upscEligible = (age in 21..32) && (qual.contains("Degree") || qual.contains("Graduat"))
        results.add(
            EligibilityResult(
                examName = "UPSC Civil Services (IAS/IPS)",
                isEligible = upscEligible,
                conditions = listOf("Degree from recognized University", "Age: 21 - 32 years (35 for OBC, 37 for SC/ST)"),
                missingRequirements = buildList {
                    if (age < 21 || age > 32) add("Age must be 21 to 32 years on 1st August of exam year")
                    if (!qual.contains("Degree") && !qual.contains("Graduat")) add("Requires Graduation")
                },
                officialNotificationLink = "https://upsc.gov.in"
            )
        )

        // UPSC NDA
        val ndaEligible = (age in 16..19)
        results.add(
            EligibilityResult(
                examName = "UPSC NDA & NA (Defence)",
                isEligible = ndaEligible,
                conditions = listOf("Class 12th pass or appearing", "Age: 16.5 - 19.5 years", "Physics & Math for Air Force/Navy"),
                missingRequirements = buildList {
                    if (age < 16 || age > 19) add("Age limit strictly 16.5 to 19.5 years (Current: $age)")
                },
                officialNotificationLink = "https://upsc.gov.in"
            )
        )

        // State Police SI
        val policeEligible = (age in 21..28) && (qual.contains("Degree") || qual.contains("Graduat")) && isFit
        results.add(
            EligibilityResult(
                examName = "State Police Sub-Inspector (SI)",
                isEligible = policeEligible,
                conditions = listOf("Graduation Degree", "Age: 21 - 28 years", "Must fulfill Physical Standards (Height, Chest, Running)"),
                missingRequirements = buildList {
                    if (age < 21 || age > 28) add("Age must be between 21 and 28 years")
                    if (!qual.contains("Degree") && !qual.contains("Graduat")) add("Requires Graduation for SI post")
                    if (!isFit) add("Must meet official physical endurance and measurement standards")
                },
                officialNotificationLink = "https://police.gov.in"
            )
        )

        // CAT (IIMs)
        val catEligible = (qual.contains("Degree") || qual.contains("Graduat")) && pct >= 50.0
        results.add(
            EligibilityResult(
                examName = "CAT (Management / IIMs)",
                isEligible = catEligible,
                conditions = listOf("Bachelor's degree with at least 50% marks (45% for SC/ST/PwD)", "No upper age limit"),
                missingRequirements = buildList {
                    if (!qual.contains("Degree") && !qual.contains("Graduat")) add("Requires Bachelor's Degree")
                    if (pct < 50.0) add("Minimum 50% marks required in graduation (Current: $pct%)")
                },
                officialNotificationLink = "https://iimcat.ac.in"
            )
        )

        _eligibilityResults.value = results
    }

    // --- SEARCH ---
    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun getFilteredExams(): List<ExamInfo> {
        val q = _searchQuery.value.trim().lowercase()
        if (q.isBlank()) return PreloadedExamData.allExams
        return PreloadedExamData.allExams.filter {
            it.name.lowercase().contains(q) ||
            it.category.lowercase().contains(q) ||
            it.subjects.any { s -> s.name.lowercase().contains(q) || s.topics.any { t -> t.name.lowercase().contains(q) } }
        }
    }

    fun getFilteredQuestions(): List<Question> {
        val q = _searchQuery.value.trim().lowercase()
        if (q.isBlank()) return PreloadedExamData.allQuestions
        return PreloadedExamData.allQuestions.filter {
            it.questionTextEn.lowercase().contains(q) ||
            it.questionTextHi.lowercase().contains(q) ||
            it.subject.lowercase().contains(q) ||
            it.topic.lowercase().contains(q) ||
            it.examName.lowercase().contains(q)
        }
    }

    fun getFilteredCurrentAffairs(): List<CurrentAffairItem> {
        val q = _searchQuery.value.trim().lowercase()
        if (q.isBlank()) return PreloadedExamData.currentAffairs
        return PreloadedExamData.currentAffairs.filter {
            it.titleEn.lowercase().contains(q) ||
            it.titleHi.lowercase().contains(q) ||
            it.category.lowercase().contains(q)
        }
    }

    fun getFilteredCareerFaqs(): List<CareerFaq> {
        val q = _searchQuery.value.trim().lowercase()
        if (q.isBlank()) return PreloadedExamData.careerFaqs
        return PreloadedExamData.careerFaqs.filter {
            it.question.lowercase().contains(q) ||
            it.answer.lowercase().contains(q) ||
            it.category.lowercase().contains(q)
        }
    }
}
