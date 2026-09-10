package com.example.data.model

data class ExamCategory(
    val id: String,
    val name: String,
    val iconName: String,
    val description: String,
    val exams: List<ExamInfo>
)

data class ExamInfo(
    val id: String,
    val name: String,
    val category: String,
    val conductingBody: String,
    val examLevel: String, // National, State, University
    val eligibilitySummary: String,
    val ageLimit: String,
    val qualification: String,
    val patternSummary: String,
    val totalQuestions: Int,
    val totalMarks: Int,
    val durationMinutes: Int,
    val markingScheme: String,
    val negativeMarking: String,
    val nextExamDate: String,
    val officialNotificationDate: String,
    val officialWebsite: String,
    val subjects: List<SubjectInfo>,
    val previousYearCutoffTrends: List<CutoffTrend>,
    val importantConcepts: List<String>
)

data class SubjectInfo(
    val name: String,
    val weightagePercent: Int,
    val questionCount: Int,
    val marks: Int,
    val topics: List<TopicInfo>
)

data class TopicInfo(
    val name: String,
    val importance: String, // High, Medium, Low
    val pyqFrequency: String,
    val typicalQuestionCount: Int
)

data class CutoffTrend(
    val year: String,
    val category: String, // UR/General, OBC, SC, ST, EWS
    val cutoffScore: String
)

enum class DifficultyLevel(val label: String) {
    EASY("Easy"),
    MODERATE("Moderate"),
    HARD("Hard"),
    EXPERT("Expert")
}

enum class QuestionType(val label: String) {
    PYQ("Previous Year Question"),
    REPEATED_PYQ("Repeated PYQ"),
    IMPORTANT_PYQ("Important PYQ"),
    EXAM_PATTERN("Exam Pattern Based"),
    AI_PRACTICE("AI-Generated Practice")
}

data class Question(
    val id: String,
    val examId: String,
    val examName: String,
    val subject: String,
    val topic: String,
    val questionTextEn: String,
    val questionTextHi: String,
    val optionsEn: List<String>,
    val optionsHi: List<String>,
    val correctOptionIndex: Int,
    val explanationEn: String,
    val explanationHi: String,
    val difficulty: DifficultyLevel,
    val type: QuestionType,
    val year: String? = null,
    val sourceInfo: String? = null
)

data class MockTest(
    val id: String,
    val title: String,
    val examId: String,
    val examName: String,
    val durationMinutes: Int,
    val totalQuestions: Int,
    val totalMarks: Int,
    val negativeMark: Double,
    val isFullLength: Boolean,
    val subject: String? = null,
    val questions: List<Question>
)

data class CurrentAffairItem(
    val id: String,
    val titleEn: String,
    val titleHi: String,
    val summaryEn: String,
    val summaryHi: String,
    val category: String, // National, Defence, Banking, Science & Tech, Economy, etc.
    val date: String,
    val importance: String, // High, Medium
    val relatedExams: List<String>,
    val sampleQuestion: Question? = null
)

data class CareerFaq(
    val id: String,
    val category: String, // After 10th, After 12th, After Graduation, Strategy, Salary
    val question: String,
    val answer: String,
    val keyEligibility: String,
    val recommendedExams: List<String>,
    val salaryRange: String,
    val careerProgression: String
)

data class ExamNotification(
    val id: String,
    val examName: String,
    val title: String,
    val date: String,
    val type: String, // Notification, Admit Card, Result, Application Deadline
    val details: String,
    val officialLink: String,
    val isUrgent: Boolean = false
)
