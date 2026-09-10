package com.example.data.local

import android.content.Context
import com.example.data.model.*
import kotlinx.coroutines.flow.Flow

class ExamDataRepository(context: Context) {
    private val db = DailyDriveDatabase.getDatabase(context)
    val dao = db.dao()

    val bookmarks: Flow<List<BookmarkEntity>> = dao.getAllBookmarks()
    val mockAttempts: Flow<List<MockTestAttemptEntity>> = dao.getAllAttempts()
    val studyTasks: Flow<List<StudyTaskEntity>> = dao.getAllTasks()
    val userStats: Flow<UserStatsEntity?> = dao.getUserStats()

    suspend fun toggleBookmark(question: Question) {
        val existing = dao.getAllBookmarks() // we can check by id or query
        dao.insertBookmark(
            BookmarkEntity(
                questionId = question.id,
                examName = question.examName,
                subject = question.subject,
                topic = question.topic
            )
        )
    }

    suspend fun removeBookmark(questionId: String) {
        dao.deleteBookmark(questionId)
    }

    suspend fun recordTestAttempt(attempt: MockTestAttemptEntity) {
        dao.recordAttempt(attempt)
    }

    suspend fun addTask(task: StudyTaskEntity) {
        dao.insertTask(task)
    }

    suspend fun toggleTaskCompletion(task: StudyTaskEntity) {
        dao.updateTask(task.copy(isCompleted = !task.isCompleted))
    }

    suspend fun deleteTask(taskId: Long) {
        dao.deleteTask(taskId)
    }

    suspend fun updateStats(stats: UserStatsEntity) {
        dao.saveUserStats(stats)
    }

    suspend fun addXpAndQuestion(isCorrect: Boolean) {
        // updates stats
        val xpGain = if (isCorrect) 15 else 5
        // retrieve or default
        val current = UserStatsEntity()
        dao.saveUserStats(
            current.copy(
                totalQuestionsAttempted = current.totalQuestionsAttempted + 1,
                totalCorrect = if (isCorrect) current.totalCorrect + 1 else current.totalCorrect,
                completedQuestionsToday = current.completedQuestionsToday + 1,
                totalXp = current.totalXp + xpGain,
                currentLevel = (current.totalXp + xpGain) / 300 + 1
            )
        )
    }

    // Comprehensive exam categories and listings
    fun getAllCategories(): List<ExamCategory> = PreloadedExamData.categories

    fun getExamById(id: String): ExamInfo? {
        return PreloadedExamData.allExams.find { it.id.equals(id, ignoreCase = true) }
            ?: PreloadedExamData.allExams.firstOrNull()
    }

    fun getAllQuestions(): List<Question> = PreloadedExamData.allQuestions

    fun getQuestionsByExam(examId: String): List<Question> {
        val filtered = PreloadedExamData.allQuestions.filter { it.examId.equals(examId, ignoreCase = true) }
        return if (filtered.isNotEmpty()) filtered else PreloadedExamData.allQuestions
    }

    fun getQuestionsBySubject(subject: String): List<Question> {
        return PreloadedExamData.allQuestions.filter { it.subject.contains(subject, ignoreCase = true) }
    }

    fun getPyqQuestions(): List<Question> {
        return PreloadedExamData.allQuestions.filter {
            it.type == QuestionType.PYQ || it.type == QuestionType.REPEATED_PYQ || it.type == QuestionType.IMPORTANT_PYQ
        }
    }

    fun getMockTests(): List<MockTest> = PreloadedExamData.mockTests

    fun getCurrentAffairs(): List<CurrentAffairItem> = PreloadedExamData.currentAffairs

    fun getCareerFaqs(): List<CareerFaq> = PreloadedExamData.careerFaqs

    fun getNotifications(): List<ExamNotification> = PreloadedExamData.notifications
}
