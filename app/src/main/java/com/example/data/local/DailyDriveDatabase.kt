package com.example.data.local

import android.content.Context
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "bookmarks")
data class BookmarkEntity(
    @PrimaryKey val questionId: String,
    val examName: String,
    val subject: String,
    val topic: String,
    val note: String = "",
    val savedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "mock_test_attempts")
data class MockTestAttemptEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val testId: String,
    val testTitle: String,
    val examName: String,
    val score: Double,
    val maxScore: Double,
    val percentage: Double,
    val correctCount: Int,
    val incorrectCount: Int,
    val unattemptedCount: Int,
    val totalQuestions: Int,
    val timeSpentSeconds: Long,
    val accuracy: Double,
    val subjectWiseBreakdown: String, // Stored as summary string
    val weakTopics: String,
    val strongTopics: String,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "study_tasks")
data class StudyTaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val subject: String,
    val targetMinutes: Int,
    val targetQuestions: Int,
    val isCompleted: Boolean = false,
    val planType: String = "DAILY", // DAILY, WEEKLY, COUNTDOWN
    val dueDate: String = "",
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "user_stats")
data class UserStatsEntity(
    @PrimaryKey val id: Int = 1,
    val studentName: String = "Aspirant",
    val targetExam: String = "SSC CGL",
    val secondaryTarget: String = "UPSC Prelims",
    val educationLevel: String = "Graduate",
    val dailyTargetQuestions: Int = 30,
    val completedQuestionsToday: Int = 12,
    val currentStreak: Int = 7,
    val totalXp: Int = 1450,
    val currentLevel: Int = 5,
    val totalQuestionsAttempted: Int = 428,
    val totalCorrect: Int = 312,
    val preferredLanguage: String = "EN" // EN, HI, BILINGUAL
)

@Dao
interface DailyDriveDao {
    // Bookmarks
    @Query("SELECT * FROM bookmarks ORDER BY savedAt DESC")
    fun getAllBookmarks(): Flow<List<BookmarkEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(bookmark: BookmarkEntity)

    @Query("DELETE FROM bookmarks WHERE questionId = :questionId")
    suspend fun deleteBookmark(questionId: String)

    @Query("SELECT EXISTS(SELECT 1 FROM bookmarks WHERE questionId = :questionId)")
    fun isBookmarked(questionId: String): Flow<Boolean>

    // Mock Test Attempts
    @Query("SELECT * FROM mock_test_attempts ORDER BY timestamp DESC")
    fun getAllAttempts(): Flow<List<MockTestAttemptEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun recordAttempt(attempt: MockTestAttemptEntity)

    // Study Tasks
    @Query("SELECT * FROM study_tasks ORDER BY isCompleted ASC, id DESC")
    fun getAllTasks(): Flow<List<StudyTaskEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: StudyTaskEntity)

    @Update
    suspend fun updateTask(task: StudyTaskEntity)

    @Query("DELETE FROM study_tasks WHERE id = :taskId")
    suspend fun deleteTask(taskId: Long)

    // User Stats
    @Query("SELECT * FROM user_stats WHERE id = 1 LIMIT 1")
    fun getUserStats(): Flow<UserStatsEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUserStats(stats: UserStatsEntity)
}

@Database(
    entities = [
        BookmarkEntity::class,
        MockTestAttemptEntity::class,
        StudyTaskEntity::class,
        UserStatsEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class DailyDriveDatabase : RoomDatabase() {
    abstract fun dao(): DailyDriveDao

    companion object {
        @Volatile
        private var INSTANCE: DailyDriveDatabase? = null

        fun getDatabase(context: Context): DailyDriveDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DailyDriveDatabase::class.java,
                    "daily_drive_db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
