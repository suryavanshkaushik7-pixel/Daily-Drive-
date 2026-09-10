package com.example.data.ai

import com.example.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

data class ChatMessage(
    val id: String,
    val sender: String, // "user" or "ai"
    val text: String,
    val timestamp: Long = System.currentTimeMillis(),
    val isVerifiedOfficialSource: Boolean = false,
    val relatedExam: String? = null
)

object DailyDriveAiAssistant {

    private val httpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    suspend fun askAssistant(prompt: String, contextExam: String = "Competitive Exams"): String = withContext(Dispatchers.IO) {
        val apiKey = try {
            BuildConfig.GEMINI_API_KEY
        } catch (e: Exception) {
            ""
        }

        // If Gemini API key is configured and valid, use Gemini 3.5 Flash REST API
        if (!apiKey.isNullOrBlank() && apiKey != "MY_GEMINI_API_KEY") {
            try {
                val url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.5-flash:generateContent?key=$apiKey"
                val systemPrompt = "You are 'Daily Drive AI', an empathetic, highly structured mentor for Indian competitive exam aspirants (UPSC, SSC, Banking, Railways, Defence, JEE, NEET, CLAT, etc.). Provide clear, crisp, student-friendly explanations with key points, exam tricks, formulas, or bilingual notes in Hindi/English where helpful. Always distinguish between verified official syllabus/rules and strategic advice."

                val jsonBody = JSONObject().apply {
                    val contents = JSONArray().apply {
                        put(JSONObject().apply {
                            put("role", "user")
                            put("parts", JSONArray().apply {
                                put(JSONObject().put("text", "Context: $contextExam\nStudent Query: $prompt"))
                            })
                        })
                    }
                    put("contents", contents)
                    put("systemInstruction", JSONObject().apply {
                        put("parts", JSONArray().apply {
                            put(JSONObject().put("text", systemPrompt))
                        })
                    })
                }

                val request = Request.Builder()
                    .url(url)
                    .post(jsonBody.toString().toRequestBody("application/json".toMediaType()))
                    .build()

                val response = httpClient.newCall(request).execute()
                if (response.isSuccessful) {
                    val responseStr = response.body?.string() ?: ""
                    val root = JSONObject(responseStr)
                    val candidates = root.optJSONArray("candidates")
                    val firstCandidate = candidates?.optJSONObject(0)
                    val content = firstCandidate?.optJSONObject("content")
                    val parts = content?.optJSONArray("parts")
                    val text = parts?.optJSONObject(0)?.optString("text")
                    if (!text.isNullOrBlank()) {
                        return@withContext text
                    }
                }
            } catch (e: Exception) {
                // Fallback to offline educational intelligence
            }
        }

        // Offline Smart Expert Mentor Response Engine
        getOfflineExpertResponse(prompt, contextExam)
    }

    private fun getOfflineExpertResponse(prompt: String, exam: String): String {
        val lower = prompt.lowercase()
        return when {
            lower.contains("article 32") || lower.contains("heart and soul") -> {
                """
                📘 **Concept Explanation: Article 32 & Writs**
                
                **Why Dr. B.R. Ambedkar called it Heart and Soul:**
                Article 32 provides the **Right to Constitutional Remedies**. A right without a remedy is meaningless. It empowers citizens to directly approach the Supreme Court when any Fundamental Right (Part III) is violated.
                
                **5 Prerogative Writs under Article 32:**
                1. **Habeas Corpus**: 'To have the body of' — protects against unlawful detention.
                2. **Mandamus**: 'We command' — directs a public official to perform their statutory duty.
                3. **Prohibition**: Issued by higher court to lower court preventing jurisdictional overreach.
                4. **Certiorari**: 'To be certified' — quashes illegal orders of subordinate judicial bodies.
                5. **Quo-Warranto**: 'By what authority' — prevents illegal usurpation of a public office.
                
                💡 **Exam Tip:** Article 226 gives similar (and broader) writ powers to High Courts!
                """.trimIndent()
            }
            lower.contains("ssc cgl") && (lower.contains("pattern") || lower.contains("syllabus") || lower.contains("prepare")) -> {
                """
                🎯 **SSC CGL Comprehensive Blueprint**
                
                **Tier-1 Pattern (Qualifying in nature):**
                - Total Questions: 100 (25 each in Reasoning, GA, Quant, English)
                - Total Marks: 200 | Time: 60 Minutes
                - Negative Marking: 0.50 marks per incorrect response.
                
                **Tier-2 Deciding Strategy:**
                - Section 1: Math (30 Qs) + Reasoning (30 Qs) = 180 marks (1 Hour)
                - Section 2: English (45 Qs) + GA (25 Qs) = 210 marks (1 Hour)
                - Section 3: Computer Knowledge (20 Qs, Qualifying) + Typing Test (15 mins, 27 wpm).
                
                **Weekly Focus Plan:**
                1. Solve 1 Full Mock every 3 days and write down every error.
                2. Revise Arithmetic formulas and Pythagorean triplets daily.
                3. Read 30 mins of current affairs and static GK art/culture notes.
                """.trimIndent()
            }
            lower.contains("banking") || lower.contains("sbi po") || lower.contains("puzzle") -> {
                """
                🏦 **Banking & SBI PO Puzzle Mastery Protocol**
                
                **Key to Banking Reasoning:**
                1. **Case Mapping:** Never solve a 2-variable puzzle in your head. Draw 2-3 parallel case diagrams immediately.
                2. **Direct Information First:** Place fixed positions (e.g., "A sits 3rd to left of B, who faces north").
                3. **Elimination Speed:** As soon as a condition fails in Case 2, discard it without hesitation.
                
                **Quant Focus:**
                - Number Series (Missing + Wrong number)
                - Quadratic Equation comparisons (sign trick: +,+ gives -,- ; -,+ gives +,+)
                - Tabular and Caselet Data Interpretation.
                """.trimIndent()
            }
            lower.contains("hindi") || lower.contains("हिंदी") -> {
                """
                🇮🇳 **डेली ड्राइव एआई अध्ययन गाइड (हिंदी माध्यम)**
                
                **प्रतियोगी परीक्षा में सफलता के 4 स्तंभ:**
                1. **सिलेबस की स्पष्टता:** परीक्षा पैटर्न और पिछले 5 वर्षों के कटऑफ का विश्लेषण करें।
                2. **दैनिक अभ्यास (Daily Practice):** प्रतिदिन 30-40 प्रश्न समय सीमा के साथ हल करें।
                3. **मॉक टेस्ट और गलतियों की डायरी:** प्रत्येक टेस्ट के बाद अपनी गलतियों (Negative Marks) को नोट करें।
                4. **करेंट अफेयर्स व सामान्य ज्ञान:** पिछले 8 महीनों की राष्ट्रीय व अंतरराष्ट्रीय घटनाओं का नियमित रिवीजन करें।
                
                Daily Drive ऐप के 'Quiz' और 'Mock Test' सेक्शन से आप प्रतिदिन हिंदी और अंग्रेजी दोनों भाषाओं में टेस्ट दे सकते हैं!
                """.trimIndent()
            }
            lower.contains("revision plan") || lower.contains("study plan") || lower.contains("schedule") -> {
                """
                📅 **Personalized 4-Phase Revision Strategy**
                
                **Phase 1: Morning Foundation (6:30 AM - 9:30 AM)**
                - High-Cognitive topics: Quantitative Aptitude / Mathematics / Technical domain.
                - Practice 25 new questions with timer.
                
                **Phase 2: Midday Retention (11:00 AM - 1:00 PM)**
                - General Awareness & Current Affairs reading.
                - English Vocabulary, Editorial analysis & Grammar error drills.
                
                **Phase 3: Afternoon Speed Drills (3:00 PM - 5:00 PM)**
                - Logical Reasoning & Puzzles / Coding / Data Interpretation.
                
                **Phase 4: Evening Mock & Analysis (7:30 PM - 9:30 PM)**
                - Take 1 Sectional or Full Mock Test on Daily Drive.
                - Spend at least 30 minutes reading explanations of incorrect questions!
                """.trimIndent()
            }
            else -> {
                """
                💡 **Daily Drive Mentor Guidance for $exam**
                
                Here is a structured analysis for your query:
                
                1. **Core Concept Insight:** Focus on conceptual clarity first before memorizing shortcuts.
                2. **Exam Trend Relevance:** In recent papers, questions emphasize application and data interpretation rather than one-liner factual recall.
                3. **Recommended Next Step:**
                   - Attempt today's Daily Quiz for this topic in the **Quiz section**.
                   - Check the **PYQ Library** to see exact question phrasing from previous exam shifts.
                   - Bookmark any tricky questions for your weekend revision drill.
                
                *Need a deeper breakdown? Ask me: "Explain with examples", "Give 5 practice questions", or "Explain in Hindi"!*
                """.trimIndent()
            }
        }
    }
}
