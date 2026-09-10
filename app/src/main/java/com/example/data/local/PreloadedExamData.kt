package com.example.data.local

import com.example.data.model.*

object PreloadedExamData {

    // --- EXAMS ---
    val sscCgl = ExamInfo(
        id = "ssc_cgl",
        name = "SSC CGL",
        category = "Government and SSC",
        conductingBody = "Staff Selection Commission (SSC)",
        examLevel = "National",
        eligibilitySummary = "Bachelor's Degree from a recognized university",
        ageLimit = "18 - 32 years (category relaxations apply)",
        qualification = "Graduation in any discipline",
        patternSummary = "Tier 1: 100 MCQs (200 marks, 60 mins). Tier 2: Mathematical Abilities, Reasoning, English, General Awareness & Computer.",
        totalQuestions = 100,
        totalMarks = 200,
        durationMinutes = 60,
        markingScheme = "+2 for correct, -0.50 for incorrect",
        negativeMarking = "0.50 marks per wrong answer in Tier 1",
        nextExamDate = "September - October 2026",
        officialNotificationDate = "June 2026 (Annual Calendar)",
        officialWebsite = "https://ssc.gov.in",
        subjects = listOf(
            SubjectInfo(
                name = "General Intelligence & Reasoning",
                weightagePercent = 25,
                questionCount = 25,
                marks = 50,
                topics = listOf(
                    TopicInfo("Analogy & Classification", "High", "3-4 per shift", 4),
                    TopicInfo("Coding-Decoding & Series", "High", "3-4 per shift", 4),
                    TopicInfo("Syllogism & Venn Diagrams", "Medium", "2 per shift", 2),
                    TopicInfo("Non-Verbal (Paper folding, Mirror image)", "High", "3-4 per shift", 3)
                )
            ),
            SubjectInfo(
                name = "General Awareness",
                weightagePercent = 25,
                questionCount = 25,
                marks = 50,
                topics = listOf(
                    TopicInfo("Current Affairs (Last 8 months)", "High", "5-7 per shift", 6),
                    TopicInfo("Indian Polity & Constitution", "High", "3-4 per shift", 4),
                    TopicInfo("Modern Indian History & Freedom Struggle", "Medium", "2-3 per shift", 3),
                    TopicInfo("Static GK (Art, Culture, Dance, Festivals)", "High", "4-5 per shift", 5)
                )
            ),
            SubjectInfo(
                name = "Quantitative Aptitude",
                weightagePercent = 25,
                questionCount = 25,
                marks = 50,
                topics = listOf(
                    TopicInfo("Arithmetic (Percentages, Ratio, SI-CI)", "High", "7-8 per shift", 8),
                    TopicInfo("Algebra & Number System", "High", "4-5 per shift", 4),
                    TopicInfo("Geometry & Mensuration", "High", "4-5 per shift", 5),
                    TopicInfo("Trigonometry & Heights/Distances", "Medium", "3-4 per shift", 3),
                    TopicInfo("Data Interpretation (D.I.)", "High", "4 per shift", 4)
                )
            ),
            SubjectInfo(
                name = "English Comprehension",
                weightagePercent = 25,
                questionCount = 25,
                marks = 50,
                topics = listOf(
                    TopicInfo("Reading Comprehension / Cloze Test", "High", "5 per shift", 5),
                    TopicInfo("Spotting Errors & Sentence Improvement", "High", "5-6 per shift", 5),
                    TopicInfo("Vocabulary (Synonyms, Antonyms, Idioms)", "High", "6-7 per shift", 7),
                    TopicInfo("Active/Passive & Direct/Indirect", "Medium", "2-3 per shift", 3)
                )
            )
        ),
        previousYearCutoffTrends = listOf(
            CutoffTrend("2024 Tier-1", "UR", "153.25"),
            CutoffTrend("2024 Tier-1", "OBC", "148.50"),
            CutoffTrend("2024 Tier-1", "EWS", "143.00"),
            CutoffTrend("2024 Tier-1", "SC", "126.80"),
            CutoffTrend("2024 Tier-1", "ST", "118.15"),
            CutoffTrend("2023 Tier-1", "UR", "150.04")
        ),
        importantConcepts = listOf(
            "Article 32 & Constitutional Remedies",
            "Trigonometric identities: sin^2 + cos^2 = 1 & Pythagorean triplets",
            "Successive percentage change formula: a + b + (ab/100)",
            "Fundamental Rights vs Directive Principles"
        )
    )

    val sscChsl = ExamInfo(
        id = "ssc_chsl",
        name = "SSC CHSL (10+2)",
        category = "Government and SSC",
        conductingBody = "Staff Selection Commission (SSC)",
        examLevel = "National",
        eligibilitySummary = "12th Standard or equivalent from a recognized board",
        ageLimit = "18 - 27 years",
        qualification = "Higher Secondary (Class 12 pass)",
        patternSummary = "Tier 1: 100 questions (200 marks, 60 mins). Tier 2: Objective & Skill/Typing test.",
        totalQuestions = 100,
        totalMarks = 200,
        durationMinutes = 60,
        markingScheme = "+2 for correct, -0.50 for incorrect",
        negativeMarking = "0.50 marks per wrong answer",
        nextExamDate = "July 2026",
        officialNotificationDate = "April 2026",
        officialWebsite = "https://ssc.gov.in",
        subjects = listOf(
            SubjectInfo("General Intelligence", 25, 25, 50, listOf(TopicInfo("Analogy", "High", "4", 4))),
            SubjectInfo("General Awareness", 25, 25, 50, listOf(TopicInfo("Static GK", "High", "6", 6))),
            SubjectInfo("Quantitative Aptitude", 25, 25, 50, listOf(TopicInfo("Basic Arithmetic & Algebra", "High", "10", 10))),
            SubjectInfo("English Language", 25, 25, 50, listOf(TopicInfo("Grammar & Vocab", "High", "12", 12)))
        ),
        previousYearCutoffTrends = listOf(
            CutoffTrend("2024 Tier-1", "UR", "157.72"),
            CutoffTrend("2024 Tier-1", "OBC", "156.35")
        ),
        importantConcepts = listOf(
            "Subject-Verb Agreement rules",
            "Compound Interest formulas",
            "Indian National Congress sessions & resolutions"
        )
    )

    val sbiPo = ExamInfo(
        id = "sbi_po",
        name = "SBI PO (Probationary Officer)",
        category = "Banking",
        conductingBody = "State Bank of India (SBI)",
        examLevel = "National",
        eligibilitySummary = "Graduation in any discipline from a recognized University",
        ageLimit = "21 - 30 years",
        qualification = "Bachelor's Degree",
        patternSummary = "Prelims: 100 Qs (100 marks, 60 mins sectional timing). Mains: 155 Qs (200 marks) + Descriptive (50 marks). Group Exercise & Interview (50 marks).",
        totalQuestions = 100,
        totalMarks = 100,
        durationMinutes = 60,
        markingScheme = "+1 for correct, -0.25 for incorrect",
        negativeMarking = "0.25 marks per wrong answer",
        nextExamDate = "November - December 2026",
        officialNotificationDate = "September 2026",
        officialWebsite = "https://sbi.co.in/careers",
        subjects = listOf(
            SubjectInfo("English Language", 30, 30, 30, listOf(TopicInfo("Reading Comprehension & Para Jumbles", "High", "12", 12))),
            SubjectInfo("Quantitative Aptitude", 35, 35, 35, listOf(TopicInfo("Data Interpretation (Caselet, Pie, Bar)", "High", "15", 15))),
            SubjectInfo("Reasoning Ability", 35, 35, 35, listOf(TopicInfo("Puzzles & Seating Arrangement", "High", "20", 20)))
        ),
        previousYearCutoffTrends = listOf(
            CutoffTrend("2024 Prelims", "UR", "59.25"),
            CutoffTrend("2024 Prelims", "EWS", "59.25"),
            CutoffTrend("2024 Prelims", "OBC", "58.00")
        ),
        importantConcepts = listOf(
            "Circular & Linear Seating with 2 variables",
            "Missing DI and Arithmetic-based Data Interpretation",
            "Banking Regulation Act 1949 & RBI Monetary Policy tools"
        )
    )

    val ibpsPo = ExamInfo(
        id = "ibps_po",
        name = "IBPS PO / MT",
        category = "Banking",
        conductingBody = "Institute of Banking Personnel Selection (IBPS)",
        examLevel = "National",
        eligibilitySummary = "A Degree (Graduation) in any discipline",
        ageLimit = "20 - 30 years",
        qualification = "Graduation Degree",
        patternSummary = "Prelims (100 marks, 60 mins), Mains (200 marks + 25 marks descriptive), Interview (100 marks)",
        totalQuestions = 100,
        totalMarks = 100,
        durationMinutes = 60,
        markingScheme = "+1 for correct, -0.25 for incorrect",
        negativeMarking = "0.25 marks per wrong answer",
        nextExamDate = "October 2026",
        officialNotificationDate = "August 2026",
        officialWebsite = "https://ibps.in",
        subjects = listOf(
            SubjectInfo("Reasoning Ability", 35, 35, 35, listOf(TopicInfo("Syllogisms & Inequalities", "High", "10", 10))),
            SubjectInfo("Quantitative Aptitude", 35, 35, 35, listOf(TopicInfo("Quadratic Equations & DI", "High", "15", 15))),
            SubjectInfo("English Language", 30, 30, 30, listOf(TopicInfo("Error Detection & Fillers", "High", "10", 10)))
        ),
        previousYearCutoffTrends = listOf(
            CutoffTrend("2024 Prelims", "UR", "54.75"),
            CutoffTrend("2024 Prelims", "OBC", "54.75")
        ),
        importantConcepts = listOf("Repo rate, Reverse Repo, CRR, SLR mechanisms", "Input-Output machine reasoning")
    )

    val rrbNtpc = ExamInfo(
        id = "rrb_ntpc",
        name = "RRB NTPC (Graduate & Under-Graduate)",
        category = "Railways",
        conductingBody = "Railway Recruitment Boards (RRBs)",
        examLevel = "National",
        eligibilitySummary = "12th Pass for UG posts, Graduation for Graduate posts",
        ageLimit = "18 - 33 years (relaxations for OBC/SC/ST)",
        qualification = "12th / Degree depending on post",
        patternSummary = "CBT 1: 100 Qs (100 marks, 90 mins). CBT 2: 120 Qs (120 marks, 90 mins). Typing Skill Test / CBAT where applicable.",
        totalQuestions = 100,
        totalMarks = 100,
        durationMinutes = 90,
        markingScheme = "+1 for correct, -1/3 for incorrect",
        negativeMarking = "1/3rd negative marking for each incorrect response",
        nextExamDate = "December 2026",
        officialNotificationDate = "September 2026",
        officialWebsite = "https://indianrailways.gov.in",
        subjects = listOf(
            SubjectInfo("General Awareness", 40, 40, 40, listOf(TopicInfo("Science (Physics, Chem, Bio)", "High", "12", 12))),
            SubjectInfo("Mathematics", 30, 30, 30, listOf(TopicInfo("Speed, Distance, Time & Trains", "High", "6", 6))),
            SubjectInfo("General Intelligence & Reasoning", 30, 30, 30, listOf(TopicInfo("Coding, Blood Relations, Puzzles", "High", "8", 8)))
        ),
        previousYearCutoffTrends = listOf(
            CutoffTrend("CBT-1 Normalized", "UR", "72.40"),
            CutoffTrend("CBT-1 Normalized", "OBC", "68.20")
        ),
        importantConcepts = listOf("Relative speed when trains move in opposite/same direction", "Newton's Laws of Motion & Work-Energy Theorem")
    )

    val rrbGroupD = ExamInfo(
        id = "rrb_group_d",
        name = "RRB Group D (Level 1)",
        category = "Railways",
        conductingBody = "Railway Recruitment Boards (RRBs)",
        examLevel = "National",
        eligibilitySummary = "10th Pass (Matriculation) or ITI from NCVT/SCVT",
        ageLimit = "18 - 33 years",
        qualification = "Class 10th / ITI",
        patternSummary = "CBT: 100 Questions (100 marks, 90 minutes). Physical Efficiency Test (PET) followed by Document Verification.",
        totalQuestions = 100,
        totalMarks = 100,
        durationMinutes = 90,
        markingScheme = "+1 for correct, -1/3 for incorrect",
        negativeMarking = "1/3 mark deducted per wrong answer",
        nextExamDate = "November 2026",
        officialNotificationDate = "August 2026",
        officialWebsite = "https://indianrailways.gov.in",
        subjects = listOf(
            SubjectInfo("General Science", 25, 25, 25, listOf(TopicInfo("Physics & Chemistry fundamentals", "High", "15", 15))),
            SubjectInfo("Mathematics", 25, 25, 25, listOf(TopicInfo("Number System, BODMAS, Decimals", "High", "12", 12))),
            SubjectInfo("General Intelligence & Reasoning", 30, 30, 30, listOf(TopicInfo("Analogies, Series, Directions", "High", "15", 15))),
            SubjectInfo("General Awareness on Current Affairs", 20, 20, 20, listOf(TopicInfo("Sports, Culture, Awards", "Medium", "8", 8)))
        ),
        previousYearCutoffTrends = listOf(
            CutoffTrend("Normalized CBT", "UR", "69.50"),
            CutoffTrend("Normalized CBT", "OBC", "64.10")
        ),
        importantConcepts = listOf("Ohm's Law, Series & Parallel Resistors", "Periodic Table classification")
    )

    val ndaExam = ExamInfo(
        id = "nda",
        name = "UPSC NDA & NA (National Defence Academy)",
        category = "Defence",
        conductingBody = "Union Public Service Commission (UPSC)",
        examLevel = "National",
        eligibilitySummary = "Unmarried male and female candidates who have passed or appearing in 12th standard",
        ageLimit = "16.5 - 19.5 years",
        qualification = "12th Standard (Physics & Math for Air Force/Navy)",
        patternSummary = "Paper 1: Mathematics (300 marks, 150 mins). Paper 2: General Ability Test GAT (600 marks, 150 mins). SSB Interview (900 marks).",
        totalQuestions = 270,
        totalMarks = 900,
        durationMinutes = 300,
        markingScheme = "Math: +2.5 / -0.83; GAT: +4 / -1.33",
        negativeMarking = "One-third negative marking for each wrong answer",
        nextExamDate = "September 2026 (NDA II)",
        officialNotificationDate = "May 2026",
        officialWebsite = "https://upsc.gov.in",
        subjects = listOf(
            SubjectInfo("Mathematics", 33, 120, 300, listOf(TopicInfo("Matrices, Determinants & Calculus", "High", "40", 100))),
            SubjectInfo("English (GAT Part A)", 22, 50, 200, listOf(TopicInfo("Grammar, Idioms, Comprehension", "High", "50", 200))),
            SubjectInfo("General Knowledge (GAT Part B)", 45, 100, 400, listOf(TopicInfo("Physics, Chemistry, History, Geo", "High", "100", 400)))
        ),
        previousYearCutoffTrends = listOf(
            CutoffTrend("Written Exam", "All", "355 / 900"),
            CutoffTrend("Final Recommendation", "All", "710 / 1800")
        ),
        importantConcepts = listOf("Vector dot and cross products", "Differential equations order & degree", "Indian Freedom Movement 1857-1947")
    )

    val cdsExam = ExamInfo(
        id = "cds",
        name = "UPSC CDS (Combined Defence Services)",
        category = "Defence",
        conductingBody = "Union Public Service Commission (UPSC)",
        examLevel = "National",
        eligibilitySummary = "Graduation Degree for IMA/OTA, Engineering for Air Force/Naval Academy",
        ageLimit = "19 - 24 years",
        qualification = "Bachelor's Degree",
        patternSummary = "IMA/INA/AFA: English (100), GK (100), Elementary Math (100). OTA: English (100), GK (100). SSB Interview (300/200 marks).",
        totalQuestions = 340,
        totalMarks = 300,
        durationMinutes = 360,
        markingScheme = "+1 / -0.33 per question",
        negativeMarking = "0.33 marks per wrong answer",
        nextExamDate = "September 2026",
        officialNotificationDate = "May 2026",
        officialWebsite = "https://upsc.gov.in",
        subjects = listOf(
            SubjectInfo("English", 33, 120, 100, listOf(TopicInfo("Ordering of Sentences, Antonyms", "High", "40", 33))),
            SubjectInfo("General Knowledge", 33, 120, 100, listOf(TopicInfo("Defence Exercises, Modern History", "High", "40", 33))),
            SubjectInfo("Elementary Mathematics", 33, 100, 100, listOf(TopicInfo("Number Theory, Trigonometry", "High", "40", 33)))
        ),
        previousYearCutoffTrends = listOf(
            CutoffTrend("IMA Written", "UR", "138 / 300"),
            CutoffTrend("OTA Written", "UR", "102 / 200")
        ),
        importantConcepts = listOf("Tri-service joint exercises (Garuda, Malabar, Yudh Abhyas)", "Indian Constitution fundamental duties")
    )

    val upscCse = ExamInfo(
        id = "upsc_cse",
        name = "UPSC Civil Services (IAS / IPS / IFS)",
        category = "Civil Services",
        conductingBody = "Union Public Service Commission (UPSC)",
        examLevel = "National",
        eligibilitySummary = "Graduate in any discipline from a recognized University",
        ageLimit = "21 - 32 years (Relaxation: OBC 3 yrs, SC/ST 5 yrs)",
        qualification = "Graduation Degree",
        patternSummary = "Prelims: GS Paper 1 (200 marks) + CSAT qualifying (200 marks, 33% min). Mains: 9 descriptive papers (1750 marks). Personality Test: 275 marks.",
        totalQuestions = 180,
        totalMarks = 400,
        durationMinutes = 240,
        markingScheme = "GS1: +2 / -0.66; CSAT: +2.5 / -0.83",
        negativeMarking = "One-third negative marking for each incorrect response",
        nextExamDate = "May 2026 (Prelims)",
        officialNotificationDate = "February 2026",
        officialWebsite = "https://upsc.gov.in",
        subjects = listOf(
            SubjectInfo("Indian Polity & Governance", 20, 15, 30, listOf(TopicInfo("Constitutional Framework, Judiciary, Panchayati Raj", "High", "15", 30))),
            SubjectInfo("History & Art and Culture", 20, 15, 30, listOf(TopicInfo("Ancient, Medieval, Modern & Temple Architecture", "High", "15", 30))),
            SubjectInfo("Geography & Environment", 25, 20, 40, listOf(TopicInfo("Climate Change, Biodiversity, Physical Geography", "High", "20", 40))),
            SubjectInfo("Economy & Social Development", 20, 15, 30, listOf(TopicInfo("Macroeconomics, Fiscal Policy, Inflation, Banking", "High", "15", 30))),
            SubjectInfo("CSAT (Paper II Qualifying)", 15, 80, 200, listOf(TopicInfo("Reading Comprehension, Logical Reasoning, Basic Numeracy", "High", "80", 200)))
        ),
        previousYearCutoffTrends = listOf(
            CutoffTrend("2023 Prelims GS-1", "UR", "75.41"),
            CutoffTrend("2023 Prelims GS-1", "EWS", "68.02"),
            CutoffTrend("2023 Prelims GS-1", "OBC", "74.75"),
            CutoffTrend("2023 Prelims GS-1", "SC", "59.25")
        ),
        importantConcepts = listOf(
            "Basic Structure Doctrine (Kesavananda Bharati Case)",
            "Biodiversity Hotspots in India & Ramsar Wetlands",
            "Monetary Policy Committee & CPI inflation targeting"
        )
    )

    val jeeMain = ExamInfo(
        id = "jee_main",
        name = "JEE Main",
        category = "Engineering",
        conductingBody = "National Testing Agency (NTA)",
        examLevel = "National",
        eligibilitySummary = "12th standard pass with Physics, Chemistry and Mathematics",
        ageLimit = "No upper age limit for JEE Main",
        qualification = "Class 12 / Appearing",
        patternSummary = "B.Tech: 90 questions (attempt 75). Physics (25), Chemistry (25), Math (25). 300 marks, 180 mins.",
        totalQuestions = 75,
        totalMarks = 300,
        durationMinutes = 180,
        markingScheme = "+4 for correct, -1 for incorrect in MCQs and numericals",
        negativeMarking = "-1 mark per incorrect answer",
        nextExamDate = "Session 1: Jan 2026, Session 2: Apr 2026",
        officialNotificationDate = "November 2025",
        officialWebsite = "https://jeemain.nta.nic.in",
        subjects = listOf(
            SubjectInfo("Physics", 33, 25, 100, listOf(TopicInfo("Mechanics, Electrodynamics, Modern Physics", "High", "20", 80))),
            SubjectInfo("Chemistry", 33, 25, 100, listOf(TopicInfo("Organic Reaction Mechanisms, Chemical Bonding, Thermodynamics", "High", "20", 80))),
            SubjectInfo("Mathematics", 33, 25, 100, listOf(TopicInfo("Calculus, Coordinate Geometry, Vectors & 3D", "High", "20", 80)))
        ),
        previousYearCutoffTrends = listOf(
            CutoffTrend("Cutoff for JEE Adv", "UR", "93.23 percentile"),
            CutoffTrend("Cutoff for JEE Adv", "OBC-NCL", "79.28 percentile")
        ),
        importantConcepts = listOf("Rotational Dynamics & Moment of Inertia", "Chemical Equilibrium & Le Chatelier Principle")
    )

    val neet = ExamInfo(
        id = "neet_ug",
        name = "NEET (UG) Medical Entrance",
        category = "Medical",
        conductingBody = "National Testing Agency (NTA)",
        examLevel = "National",
        eligibilitySummary = "12th pass with Physics, Chemistry, Biology/Biotechnology and English",
        ageLimit = "Minimum 17 years as on 31st December of the year of admission",
        qualification = "Class 12 with PCB",
        patternSummary = "200 Questions (attempt 180). Physics (45), Chemistry (45), Botany (45), Zoology (45). Total 720 marks, 200 mins.",
        totalQuestions = 180,
        totalMarks = 720,
        durationMinutes = 200,
        markingScheme = "+4 for correct, -1 for incorrect",
        negativeMarking = "1 mark deducted for each incorrect option",
        nextExamDate = "May 2026",
        officialNotificationDate = "February 2026",
        officialWebsite = "https://exams.nta.ac.in/NEET",
        subjects = listOf(
            SubjectInfo("Biology (Botany + Zoology)", 50, 90, 360, listOf(TopicInfo("Genetics & Evolution, Human Physiology, Ecology", "High", "50", 200))),
            SubjectInfo("Physics", 25, 45, 180, listOf(TopicInfo("Current Electricity, Ray Optics, Thermodynamics", "High", "25", 100))),
            SubjectInfo("Chemistry", 25, 45, 180, listOf(TopicInfo("Coordination Compounds, Biomolecules, Electrochemistry", "High", "25", 100)))
        ),
        previousYearCutoffTrends = listOf(
            CutoffTrend("General AIQ Cutoff", "UR", "655 / 720"),
            CutoffTrend("OBC AIQ Cutoff", "OBC", "652 / 720")
        ),
        importantConcepts = listOf("Mendelian Genetics & DNA Replication machinery", "Nephron structure & Urine concentration mechanism")
    )

    val clat = ExamInfo(
        id = "clat",
        name = "CLAT (Common Law Admission Test)",
        category = "Law",
        conductingBody = "Consortium of National Law Universities (NLUs)",
        examLevel = "National",
        eligibilitySummary = "12th pass with minimum 45% marks (40% for SC/ST)",
        ageLimit = "No upper age limit",
        qualification = "Class 12 or equivalent",
        patternSummary = "120 passage-based MCQs (120 marks, 120 mins). English, Current Affairs, Legal Reasoning, Logical Reasoning, Quantitative Techniques.",
        totalQuestions = 120,
        totalMarks = 120,
        durationMinutes = 120,
        markingScheme = "+1 for correct, -0.25 for incorrect",
        negativeMarking = "0.25 marks deducted per wrong answer",
        nextExamDate = "December 2026",
        officialNotificationDate = "July 2026",
        officialWebsite = "https://consortiumofnlus.ac.in",
        subjects = listOf(
            SubjectInfo("Legal Reasoning", 25, 30, 30, listOf(TopicInfo("Constitutional Law, Law of Torts, Contracts", "High", "30", 30))),
            SubjectInfo("Current Affairs including GK", 25, 30, 30, listOf(TopicInfo("Recent Supreme Court judgments, International Law", "High", "30", 30))),
            SubjectInfo("English Language", 20, 24, 24, listOf(TopicInfo("Reading Comprehension & Critical Analysis", "High", "24", 24))),
            SubjectInfo("Logical Reasoning", 20, 24, 24, listOf(TopicInfo("Critical Arguments, Assumptions, Inferences", "High", "24", 24))),
            SubjectInfo("Quantitative Techniques", 10, 12, 12, listOf(TopicInfo("Data Interpretation & Numerical reasoning", "Medium", "12", 12)))
        ),
        previousYearCutoffTrends = listOf(
            CutoffTrend("NLSIU Bengaluru (Rank 1)", "UR", "102 / 120"),
            CutoffTrend("NALSAR Hyderabad", "UR", "96 / 120")
        ),
        importantConcepts = listOf("Volenti non fit injuria principle in Torts", "Doctrine of Separation of Powers")
    )

    val cuet = ExamInfo(
        id = "cuet_ug",
        name = "CUET (UG) Central Universities Entrance",
        category = "University and Education",
        conductingBody = "National Testing Agency (NTA)",
        examLevel = "National",
        eligibilitySummary = "Candidates who have passed class 12th or equivalent examination",
        ageLimit = "No age limit for CUET UG",
        qualification = "Class 12",
        patternSummary = "Section 1A & 1B: Languages (40/50 Qs). Section 2: Domain Subjects (40/50 Qs). Section 3: General Test (50/60 Qs).",
        totalQuestions = 50,
        totalMarks = 250,
        durationMinutes = 45,
        markingScheme = "+5 for correct, -1 for incorrect",
        negativeMarking = "-1 mark per wrong answer",
        nextExamDate = "May 2026",
        officialNotificationDate = "February 2026",
        officialWebsite = "https://exams.nta.ac.in/CUET-UG",
        subjects = listOf(
            SubjectInfo("General Test", 50, 50, 250, listOf(TopicInfo("General Knowledge, Current Affairs, Mental Ability", "High", "50", 250))),
            SubjectInfo("Domain Specific Subjects", 50, 40, 200, listOf(TopicInfo("Class 12 NCERT Core Topics", "High", "40", 200)))
        ),
        previousYearCutoffTrends = listOf(
            CutoffTrend("DU North Campus B.Com(H)", "UR", "780 / 800")
        ),
        importantConcepts = listOf("NCERT Line by Line concepts", "Data analysis from bar and line charts")
    )

    val ctet = ExamInfo(
        id = "ctet",
        name = "CTET (Central Teacher Eligibility Test)",
        category = "University and Education",
        conductingBody = "Central Board of Secondary Education (CBSE)",
        examLevel = "National",
        eligibilitySummary = "D.El.Ed / B.Ed with minimum qualifying percentage",
        ageLimit = "18+ years (no upper age limit)",
        qualification = "Teacher Training Diploma / Degree",
        patternSummary = "Paper 1 (Class I-V): 150 MCQs, 150 marks, 150 mins. Paper 2 (Class VI-VIII): 150 MCQs, 150 marks, 150 mins.",
        totalQuestions = 150,
        totalMarks = 150,
        durationMinutes = 150,
        markingScheme = "+1 for correct, No negative marking",
        negativeMarking = "No negative marking",
        nextExamDate = "December 2026",
        officialNotificationDate = "September 2026",
        officialWebsite = "https://ctet.nic.in",
        subjects = listOf(
            SubjectInfo("Child Development and Pedagogy", 20, 30, 30, listOf(TopicInfo("Piaget, Vygotsky, Kohlberg theories", "High", "15", 15))),
            SubjectInfo("Mathematics & Pedagogy", 20, 30, 30, listOf(TopicInfo("Geometry, Numbers, Pedagogical issues", "High", "15", 15))),
            SubjectInfo("Environmental Studies (EVS)", 20, 30, 30, listOf(TopicInfo("Family, Food, Shelter, Travel", "High", "15", 15)))
        ),
        previousYearCutoffTrends = listOf(
            CutoffTrend("Qualifying Score (General)", "UR", "90 / 150 (60%)"),
            CutoffTrend("Qualifying Score (Reserved)", "OBC/SC/ST", "82 / 150 (55%)")
        ),
        importantConcepts = listOf("Vygotsky's Zone of Proximal Development (ZPD)", "Piaget's 4 stages of cognitive development")
    )

    val catExam = ExamInfo(
        id = "cat",
        name = "CAT (Common Admission Test - IIMs)",
        category = "Management",
        conductingBody = "Indian Institutes of Management (IIMs)",
        examLevel = "National",
        eligibilitySummary = "Bachelor's Degree with at least 50% marks or equivalent CGPA (45% for SC/ST/PwD)",
        ageLimit = "No age restriction",
        qualification = "Graduation Degree",
        patternSummary = "66 questions (VARC: 24, DILR: 20, QA: 22). 120 minutes with 40 mins sectional timer.",
        totalQuestions = 66,
        totalMarks = 198,
        durationMinutes = 120,
        markingScheme = "+3 for correct, -1 for incorrect in MCQs (0 for TITA)",
        negativeMarking = "-1 mark for wrong MCQ, 0 for Non-MCQ TITA",
        nextExamDate = "November 2026",
        officialNotificationDate = "July 2026",
        officialWebsite = "https://iimcat.ac.in",
        subjects = listOf(
            SubjectInfo("VARC (Verbal & Reading Comp)", 36, 24, 72, listOf(TopicInfo("RC Passages (Philosophy, Science, Economics)", "High", "16", 48))),
            SubjectInfo("DILR (Data Interpretation & LR)", 30, 20, 60, listOf(TopicInfo("Matrix Arrangements, Venn Diagrams, Games", "High", "20", 60))),
            SubjectInfo("QA (Quantitative Aptitude)", 34, 22, 66, listOf(TopicInfo("Arithmetic, Algebra, Geometry", "High", "14", 42)))
        ),
        previousYearCutoffTrends = listOf(
            CutoffTrend("IIM Ahmedabad Call", "UR", "99.5+ percentile"),
            CutoffTrend("IIM Bangalore Call", "UR", "99.2+ percentile")
        ),
        importantConcepts = listOf("Critical reasoning assumptions and flaws", "Maxima-Minima in Set Theory")
    )

    val statePoliceExam = ExamInfo(
        id = "state_police_si",
        name = "State Police Sub-Inspector (SI) & Constable",
        category = "State-level Exams",
        conductingBody = "State Police Recruitment Board",
        examLevel = "State",
        eligibilitySummary = "Graduation for SI, 12th pass for Constable. Physical measurements required.",
        ageLimit = "21 - 28 years (SI), 18 - 25 years (Constable)",
        qualification = "Graduation / 12th standard",
        patternSummary = "Written Exam (General Hindi, Law & Constitution, General Knowledge, Numerical & Mental Ability). Physical Standard Test (PST) & Physical Endurance Test (PET).",
        totalQuestions = 160,
        totalMarks = 400,
        durationMinutes = 120,
        markingScheme = "+2.5 marks per question, -0.50 negative marking",
        negativeMarking = "0.50 negative marking",
        nextExamDate = "October 2026",
        officialNotificationDate = "July 2026",
        officialWebsite = "https://uppbpb.gov.in (and respective states)",
        subjects = listOf(
            SubjectInfo("General Hindi", 25, 40, 100, listOf(TopicInfo("Vyakaran, Muhavare, Sandhi, Samas", "High", "40", 100))),
            SubjectInfo("Basic Law & Constitution & GK", 25, 40, 100, listOf(TopicInfo("IPC, CrPC, Motor Vehicles Act, Indian Constitution", "High", "40", 100))),
            SubjectInfo("Numerical Ability", 25, 40, 100, listOf(TopicInfo("Percentage, Profit-Loss, Time & Work", "High", "40", 100))),
            SubjectInfo("Mental Aptitude & Reasoning", 25, 40, 100, listOf(TopicInfo("Public Interest, Police System, Analogies", "High", "40", 100)))
        ),
        previousYearCutoffTrends = listOf(
            CutoffTrend("Final SI Cutoff", "UR", "316 / 400"),
            CutoffTrend("Final SI Cutoff", "OBC", "305 / 400")
        ),
        importantConcepts = listOf("Fundamental Rights under Articles 14-32", "IPC sections on self-defence and public order")
    )

    val allExams = listOf(
        sscCgl,
        sscChsl,
        sbiPo,
        ibpsPo,
        rrbNtpc,
        rrbGroupD,
        ndaExam,
        cdsExam,
        upscCse,
        jeeMain,
        neet,
        clat,
        cuet,
        ctet,
        catExam,
        statePoliceExam
    )

    val categories = listOf(
        ExamCategory(
            id = "ssc_gov",
            name = "Government and SSC",
            iconName = "account_balance",
            description = "SSC CGL, CHSL, MTS, GD, CPO, JE, Stenographer",
            exams = listOf(sscCgl, sscChsl)
        ),
        ExamCategory(
            id = "banking",
            name = "Banking & Finance",
            iconName = "savings",
            description = "SBI PO, Clerk, IBPS PO, RRB, RBI Grade B, SEBI",
            exams = listOf(sbiPo, ibpsPo)
        ),
        ExamCategory(
            id = "railways",
            name = "Railways (RRB)",
            iconName = "train",
            description = "RRB NTPC, Group D, ALP, Technicians, JE",
            exams = listOf(rrbNtpc, rrbGroupD)
        ),
        ExamCategory(
            id = "defence",
            name = "Defence Services",
            iconName = "shield",
            description = "NDA, CDS, AFCAT, Navy, Air Force, Agniveer",
            exams = listOf(ndaExam, cdsExam)
        ),
        ExamCategory(
            id = "civil_services",
            name = "Civil Services & UPSC",
            iconName = "flag",
            description = "UPSC CSE (IAS/IPS), State PSC, IES",
            exams = listOf(upscCse)
        ),
        ExamCategory(
            id = "engineering",
            name = "Engineering Entrances",
            iconName = "architecture",
            description = "JEE Main, JEE Advanced, GATE, State Engg",
            exams = listOf(jeeMain)
        ),
        ExamCategory(
            id = "medical",
            name = "Medical Entrances",
            iconName = "medical_services",
            description = "NEET (UG), AIIMS, Nursing, State PMT",
            exams = listOf(neet)
        ),
        ExamCategory(
            id = "law",
            name = "Law Entrances",
            iconName = "gavel",
            description = "CLAT, AILET, SLAT, LSAT India",
            exams = listOf(clat)
        ),
        ExamCategory(
            id = "education",
            name = "Teaching & University",
            iconName = "school",
            description = "CUET, CTET, State TETs, UGC NET",
            exams = listOf(cuet, ctet)
        ),
        ExamCategory(
            id = "management",
            name = "Management & MBA",
            iconName = "business_center",
            description = "CAT, XAT, CMAT, MAT, SNAP",
            exams = listOf(catExam)
        ),
        ExamCategory(
            id = "state_level",
            name = "State Government & Police",
            iconName = "local_police",
            description = "State Police SI, Patwari, CET, Clerk",
            exams = listOf(statePoliceExam)
        )
    )

    // --- QUESTIONS (Bilingual, Verified PYQs & Exam Pattern Based) ---
    val allQuestions = listOf(
        Question(
            id = "q_1",
            examId = "ssc_cgl",
            examName = "SSC CGL",
            subject = "Indian Polity",
            topic = "Fundamental Rights",
            questionTextEn = "Which Article of the Indian Constitution is termed as the 'Heart and Soul of the Constitution' by Dr. B.R. Ambedkar?",
            questionTextHi = "डॉ. बी.आर. अंबेडकर ने भारतीय संविधान के किस अनुच्छेद को 'संविधान का हृदय और आत्मा' कहा था?",
            optionsEn = listOf("Article 19", "Article 21", "Article 32", "Article 14"),
            optionsHi = listOf("अनुच्छेद 19", "अनुच्छेद 21", "अनुच्छेद 32", "अनुच्छेद 14"),
            correctOptionIndex = 2,
            explanationEn = "Dr. B.R. Ambedkar designated Article 32 (Right to Constitutional Remedies) as the heart and soul because it empowers citizens to directly approach the Supreme Court for enforcement of fundamental rights via writs like Habeas Corpus, Mandamus, etc.",
            explanationHi = "डॉ. बी.आर. अंबेडकर ने अनुच्छेद 32 (संवैधानिक उपचारों का अधिकार) को हृदय और आत्मा कहा क्योंकि यह नागरिकों को मौलिक अधिकारों के उल्लंघन पर सीधे सर्वोच्च न्यायालय जाने का अधिकार देता है।",
            difficulty = DifficultyLevel.MODERATE,
            type = QuestionType.REPEATED_PYQ,
            year = "SSC CGL 2023 Tier-1",
            sourceInfo = "Official Paper Shift 2, July 2023"
        ),
        Question(
            id = "q_2",
            examId = "ssc_cgl",
            examName = "SSC CGL",
            subject = "Quantitative Aptitude",
            topic = "Profit and Loss",
            questionTextEn = "A trader marks his goods at 25% above the cost price and allows a discount of 10% on the marked price. What is his overall profit percentage?",
            questionTextHi = "एक व्यापारी अपने माल पर क्रय मूल्य से 25% अधिक मूल्य अंकित करता है और अंकित मूल्य पर 10% की छूट देता है। उसका कुल लाभ प्रतिशत क्या है?",
            optionsEn = listOf("12.5%", "15.0%", "10.0%", "14.5%"),
            optionsHi = listOf("12.5%", "15.0%", "10.0%", "14.5%"),
            correctOptionIndex = 0,
            explanationEn = "Let Cost Price (CP) = 100. Marked Price (MP) = 125. Discount = 10% of 125 = 12.5. Selling Price (SP) = 125 - 12.5 = 112.5. Profit = 112.5 - 100 = 12.5%.",
            explanationHi = "मान लीजिए क्रय मूल्य = 100. अंकित मूल्य = 125. छूट = 125 का 10% = 12.5. विक्रय मूल्य = 112.5. अतः लाभ = 12.5%.",
            difficulty = DifficultyLevel.EASY,
            type = QuestionType.PYQ,
            year = "SSC CGL 2022 Tier-1",
            sourceInfo = "December 2022 Official Paper"
        ),
        Question(
            id = "q_3",
            examId = "sbi_po",
            examName = "SBI PO",
            subject = "Reasoning Ability",
            topic = "Syllogism",
            questionTextEn = "Statements: Only a few books are pens. All pens are markers. Conclusions: I. Some markers being books is a possibility. II. All books can never be pens.",
            questionTextHi = "कथन: केवल कुछ पुस्तकें पेन हैं। सभी पेन मार्कर हैं। निष्कर्ष: I. कुछ मार्करों के पुस्तक होने की संभावना है। II. सभी पुस्तकें कभी पेन नहीं हो सकतीं।",
            optionsEn = listOf("Only I follows", "Only II follows", "Both I and II follow", "Neither follows"),
            optionsHi = listOf("केवल I अनुसरण करता है", "केवल II अनुसरण करता है", "दोनों I और II अनुसरण करते हैं", "कोई अनुसरण नहीं करता"),
            correctOptionIndex = 1,
            explanationEn = "'Only a few books are pens' implies Some books are pens AND Some books are NOT pens. Thus, all books can NEVER be pens (Conclusion II is definitely true). Meanwhile, some markers are already definitely books, so a 'possibility' statement for an already true fact is false in banking syllogism.",
            explanationHi = "'केवल कुछ पुस्तकें पेन हैं' का अर्थ है कि सभी पुस्तकें कभी पेन नहीं हो सकतीं। अतः निष्कर्ष II निश्चित रूप से सत्य है।",
            difficulty = DifficultyLevel.HARD,
            type = QuestionType.PYQ,
            year = "SBI PO Prelims 2023",
            sourceInfo = "Memory Based Official Paper Shift 1"
        ),
        Question(
            id = "q_4",
            examId = "rrb_ntpc",
            examName = "RRB NTPC",
            subject = "General Science",
            topic = "Physics & Motion",
            questionTextEn = "What is the acceleration due to gravity (g) at the center of the Earth?",
            questionTextHi = "पृथ्वी के केंद्र में गुरुत्वाकर्षण के कारण त्वरण (g) का मान क्या होता है?",
            optionsEn = listOf("9.8 m/s²", "4.9 m/s²", "0 m/s²", "Infinite"),
            optionsHi = listOf("9.8 m/s²", "4.9 m/s²", "0 m/s²", "अनंत"),
            correctOptionIndex = 2,
            explanationEn = "At the center of the Earth, the mass of the planet surrounds you equally in all directions, causing gravitational forces to cancel out completely. Hence, g = 0.",
            explanationHi = "पृथ्वी के केंद्र में द्रव्यमान चारों ओर समान रूप से वितरित होता है जिससे कुल गुरुत्वाकर्षण खिंचाव रद्द हो जाता है और g का मान 0 हो जाता है।",
            difficulty = DifficultyLevel.EASY,
            type = QuestionType.REPEATED_PYQ,
            year = "RRB NTPC CBT-1 2021",
            sourceInfo = "Phase 2 Question Paper"
        ),
        Question(
            id = "q_5",
            examId = "nda",
            examName = "UPSC NDA",
            subject = "Mathematics",
            topic = "Trigonometry & Calculus",
            questionTextEn = "What is the value of lim (x -> 0) [sin(5x) / tan(3x)]?",
            questionTextHi = "lim (x -> 0) [sin(5x) / tan(3x)] का मान क्या होगा?",
            optionsEn = listOf("1", "3/5", "5/3", "0"),
            optionsHi = listOf("1", "3/5", "5/3", "0"),
            correctOptionIndex = 2,
            explanationEn = "Using standard limits: lim (sin(5x)/(5x)) * (3x/tan(3x)) * (5/3) as x -> 0 = 1 * 1 * 5/3 = 5/3 (or using L'Hôpital's rule: 5 cos(5x) / (3 sec^2(3x)) at x=0 is 5/3).",
            explanationHi = "मानक सीमा सूत्र से: lim (sin(5x)/5x) * (3x/tan(3x)) * (5/3) = 1 * 1 * 5/3 = 5/3.",
            difficulty = DifficultyLevel.MODERATE,
            type = QuestionType.IMPORTANT_PYQ,
            year = "UPSC NDA 2022",
            sourceInfo = "Mathematics Paper I, Q44"
        ),
        Question(
            id = "q_6",
            examId = "upsc_cse",
            examName = "UPSC Civil Services",
            subject = "Modern Indian History",
            topic = "Freedom Struggle",
            questionTextEn = "The 'Ilbert Bill' controversy during the British Raj in 1883 was related to which of the following issues?",
            questionTextHi = "1883 में ब्रिटिश राज के दौरान 'इल्बर्ट बिल' विवाद निम्नलिखित में से किस मुद्दे से संबंधित था?",
            optionsEn = listOf(
                "Imposition of severe restrictions on the vernacular press",
                "Removal of racial disqualifications with regard to the trial of Europeans by Indian magistrates",
                "Reduction in the maximum age limit for the Indian Civil Services examination",
                "Compulsory military recruitment in the British Indian Army"
            ),
            optionsHi = listOf(
                "देशी भाषा के समाचार पत्रों पर कड़े प्रतिबंध लगाना",
                "भारतीय मजिस्ट्रेटों द्वारा यूरोपीय नागरिकों के मुकदमों की सुनवाई पर लगी नस्लीय अयोग्यता को हटाना",
                "भारतीय सिविल सेवा परीक्षा के लिए अधिकतम आयु सीमा कम करना",
                "ब्रिटिश भारतीय सेना में अनिवार्य सैन्य भर्ती"
            ),
            correctOptionIndex = 1,
            explanationEn = "Introduced during Lord Ripon's tenure, the Ilbert Bill sought to allow senior Indian magistrates to preside over cases involving British subjects in India, sparking fierce racial opposition from Anglo-Indians.",
            explanationHi = "लॉर्ड रिपन के कार्यकाल में पेश किए गए इल्बर्ट बिल का उद्देश्य भारतीय जिला और सत्र न्यायाधीशों को यूरोपीय अपराधियों के मुकदमों की सुनवाई का अधिकार देना था।",
            difficulty = DifficultyLevel.HARD,
            type = QuestionType.PYQ,
            year = "UPSC CSE Prelims 2013 / 2020",
            sourceInfo = "Official UPSC GS-1 Paper"
        ),
        Question(
            id = "q_7",
            examId = "neet_ug",
            examName = "NEET (UG)",
            subject = "Biology",
            topic = "Human Physiology",
            questionTextEn = "Which hormone stimulates the synthesis and secretion of thyroid hormones from the thyroid gland?",
            questionTextHi = "थायराइड ग्रंथि से थायराइड हार्मोन के संश्लेषण और स्राव को कौन सा हार्मोन उत्तेजित करता है?",
            optionsEn = listOf("Oxytocin", "TSH (Thyroid Stimulating Hormone)", "ACTH", "Prolactin"),
            optionsHi = listOf("ऑक्सीटोसिन", "टीएसएच (थायराइड स्टिम्युलेटिंग हार्मोन)", "एसीटीएच", "प्रोलैक्टिन"),
            correctOptionIndex = 1,
            explanationEn = "TSH (Thyrotropin) is secreted by the anterior pituitary gland and stimulates the synthesis and secretion of thyroid hormones (T3 and T4) by the thyroid gland.",
            explanationHi = "टीएसएच (थायराइड स्टिम्युलेटिंग हार्मोन) अग्र पिट्यूटरी ग्रंथि द्वारा स्रावित होता है जो थायराइड ग्रंथि को थायरोक्सिन उत्पादन के लिए उत्तेजित करता है।",
            difficulty = DifficultyLevel.EASY,
            type = QuestionType.EXAM_PATTERN,
            year = "NEET NCERT Pattern",
            sourceInfo = "NCERT Class 11 Chapter 22"
        ),
        Question(
            id = "q_8",
            examId = "clat",
            examName = "CLAT",
            subject = "Legal Reasoning",
            topic = "Law of Torts",
            questionTextEn = "Principle: A person who voluntarily consents to run the risk of harm cannot sue for that harm (Volenti Non Fit Injuria). Fact: Ramesh bought a ticket to a cricket match. A batsman hit a six and the ball struck Ramesh's forehead. Ramesh sued the stadium organizers. Will he succeed?",
            questionTextHi = "सिद्धांत: जो व्यक्ति स्वेच्छा से नुकसान का जोखिम उठाने की सहमति देता है, वह उस नुकसान के लिए वाद दायर नहीं कर सकता। तथ्य: रमेश ने क्रिकेट मैच का टिकट खरीदा। बल्लेबाज ने छक्का मारा और गेंद रमेश के माथे पर लगी। रमेश ने आयोजकों पर मुकदमा किया। क्या वह सफल होगा?",
            optionsEn = listOf(
                "Yes, because organizers owe absolute safety to spectators",
                "No, because buying a ticket to a cricket game carries implied consent to ordinary spectatorship risks",
                "Yes, because the batsman was negligent in hitting the ball outside the boundary",
                "No, only because cricket balls are classified as non-dangerous goods"
            ),
            optionsHi = listOf(
                "हाँ, क्योंकि आयोजकों का दर्शकों की पूर्ण सुरक्षा का दायित्व है",
                "नहीं, क्योंकि क्रिकेट मैच का टिकट खरीदने में खेल के सामान्य जोखिमों की अंतर्निहित सहमति होती है",
                "हाँ, क्योंकि बल्लेबाज ने लापरवाही से गेंद मारी",
                "नहीं, केवल इसलिए कि क्रिकेट की गेंद गैर-खतरनाक है"
            ),
            correctOptionIndex = 1,
            explanationEn = "Under Volenti Non Fit Injuria, spectators at sporting events implicitly accept ordinary risks of the game that happen without recklessness or organizers' negligence (Hall v. Brooklands Auto Racing Club).",
            explanationHi = "स्वेच्छा से जोखिम उठाने (Volenti Non Fit Injuria) के नियम के तहत खेल आयोजनों में दर्शकों द्वारा खेल के स्वाभाविक जोखिमों को स्वीकार माना जाता है।",
            difficulty = DifficultyLevel.MODERATE,
            type = QuestionType.EXAM_PATTERN,
            year = "CLAT Pattern",
            sourceInfo = "Standard Law of Torts Precedent"
        )
    )

    // --- MOCK TESTS ---
    val mockTests = listOf(
        MockTest(
            id = "mock_cgl_tier1_all",
            title = "SSC CGL 2026 Tier-1 All India Live Mock Test #1",
            examId = "ssc_cgl",
            examName = "SSC CGL",
            durationMinutes = 60,
            totalQuestions = 25,
            totalMarks = 50,
            negativeMark = 0.50,
            isFullLength = true,
            questions = allQuestions
        ),
        MockTest(
            id = "mock_sbi_po_prelims",
            title = "SBI PO Prelims Speed Buster Test (Reasoning & Quant)",
            examId = "sbi_po",
            examName = "SBI PO",
            durationMinutes = 45,
            totalQuestions = 20,
            totalMarks = 20,
            negativeMark = 0.25,
            isFullLength = false,
            subject = "Reasoning & Quant",
            questions = allQuestions.filter { it.examId == "sbi_po" || it.subject.contains("Quant") || it.subject.contains("Reasoning") }.ifEmpty { allQuestions }
        ),
        MockTest(
            id = "mock_rrb_ntpc_science",
            title = "RRB NTPC CBT-1 High Yield General Science & GK Drill",
            examId = "rrb_ntpc",
            examName = "RRB NTPC",
            durationMinutes = 30,
            totalQuestions = 15,
            totalMarks = 15,
            negativeMark = 0.33,
            isFullLength = false,
            subject = "General Science",
            questions = allQuestions.filter { it.subject.contains("Science") || it.subject.contains("Polity") }.ifEmpty { allQuestions }
        ),
        MockTest(
            id = "mock_upsc_csat_prelims",
            title = "UPSC CSE Prelims GS-1 Full-Length Simulation 2026",
            examId = "upsc_cse",
            examName = "UPSC Civil Services",
            durationMinutes = 120,
            totalQuestions = 30,
            totalMarks = 60,
            negativeMark = 0.66,
            isFullLength = true,
            questions = allQuestions
        )
    )

    // --- CURRENT AFFAIRS (Categorized) ---
    val currentAffairs = listOf(
        CurrentAffairItem(
            id = "ca_1",
            titleEn = "ISRO prepares for Gaganyaan Uncrewed Flight Test with humanoid robot 'Vyommitra'",
            titleHi = "इसरो ने मानवयुक्त रोबोट 'व्योममित्र' के साथ गगनयान मानव-रहित उड़ान परीक्षण की तैयारी की",
            summaryEn = "The Indian Space Research Organisation (ISRO) advanced final tests for the humanoid robot Vyommitra designed to monitor life-support systems, cabin pressure, and switch panels inside the crew module.",
            summaryHi = "भारतीय अंतरिक्ष अनुसंधान संगठन (इसरो) ने गगनयान मिशन के क्रू मॉड्यूल में लाइफ सपोर्ट और केबिन प्रेशर की निगरानी के लिए महिला रोबोट व्योममित्र का सफल परीक्षण किया।",
            category = "Science and Technology",
            date = "September 2026",
            importance = "High",
            relatedExams = listOf("UPSC CSE", "SSC CGL", "CDS", "NDA", "State PSC")
        ),
        CurrentAffairItem(
            id = "ca_2",
            titleEn = "RBI Monetary Policy Committee keeps Repo Rate unchanged to anchor CPI inflation target at 4%",
            titleHi = "आरबीआई मौद्रिक नीति समिति ने मुद्रास्फीति लक्ष्य को बनाए रखने हेतु रेपो दर अपरिवर्तित रखी",
            summaryEn = "The Reserve Bank of India maintained the benchmark policy repo rate, underscoring resilient domestic GDP growth while closely monitoring food price volatility to align headline inflation with target.",
            summaryHi = "भारतीय रिज़र्व बैंक की छह सदस्यीय मौद्रिक नीति समिति ने रेपो रेट को यथावत रखने का निर्णय लिया ताकि विकास को गति देते हुए महंगाई पर नियंत्रण रखा जा सके।",
            category = "Banking and Economy",
            date = "September 2026",
            importance = "High",
            relatedExams = listOf("SBI PO", "IBPS PO", "RBI Grade B", "SSC CGL", "UPSC CSE")
        ),
        CurrentAffairItem(
            id = "ca_3",
            titleEn = "Indian Armed Forces induct indigenous anti-drone laser directed-energy weapon systems",
            titleHi = "भारतीय सशस्त्र बलों ने स्वदेशी एंटी-ड्रोन लेजर हथियार प्रणाली को शामिल किया",
            summaryEn = "DRDO successfully delivered high-powered counter-UAV directed energy systems to the Indian Army and Air Force for perimeter air defense along high-altitude border posts.",
            summaryHi = "रक्षा अनुसंधान एवं विकास संगठन (डीआरडीओ) ने सीमाओं पर निगरानी और सुरक्षा के लिए उच्च शक्ति वाली एंटी-ड्रोन लेजर प्रणाली भारतीय सेना को सौंपी।",
            category = "Defence",
            date = "September 2026",
            importance = "High",
            relatedExams = listOf("NDA", "CDS", "AFCAT", "SSC GD", "State Police")
        ),
        CurrentAffairItem(
            id = "ca_4",
            titleEn = "India expands Ramsar Wetlands Network adding new ecological sites in southern and western states",
            titleHi = "भारत ने रामसर वेटलैंड्स नेटवर्क का विस्तार कर नए पारिस्थितिक स्थलों को शामिल किया",
            summaryEn = "The Ministry of Environment, Forest and Climate Change designated new wetlands of international importance under the Ramsar Convention, reinforcing conservation of migratory waterfowl habitats.",
            summaryHi = "पर्यावरण, वन और जलवायु परिवर्तन मंत्रालय ने रामसर सम्मेलन के तहत अंतरराष्ट्रीय महत्व के नए आर्द्रभूमि स्थलों की घोषणा की।",
            category = "Environment",
            date = "August 2026",
            importance = "High",
            relatedExams = listOf("UPSC CSE", "State PSC", "SSC CGL", "CTET")
        ),
        CurrentAffairItem(
            id = "ca_5",
            titleEn = "National Sports Awards & Major Dhyan Chand Khel Ratna recipients announced for outstanding sporting achievements",
            titleHi = "उत्कृष्ट खेल उपलब्धियों के लिए राष्ट्रीय खेल पुरस्कार और मेजर ध्यानचंद खेल रत्न की घोषणा",
            summaryEn = "The Ministry of Youth Affairs and Sports conferred the prestigious Khel Ratna, Arjuna, and Dronacharya awards for exceptional performances in Olympic disciplines and World Championships.",
            summaryHi = "युवा मामले और खेल मंत्रालय ने विश्व चैंपियनशिप और अंतरराष्ट्रीय प्रतियोगिताओं में देश का नाम रोशन करने वाले एथलीटों को सम्मानित किया।",
            category = "Sports and Awards",
            date = "August 2026",
            importance = "Medium",
            relatedExams = listOf("SSC CHSL", "SSC MTS", "RRB Group D", "State Police")
        )
    )

    // --- CAREER GUIDANCE FAQ ---
    val careerFaqs = listOf(
        CareerFaq(
            id = "car_1",
            category = "After Class 12",
            question = "Which government and competitive exams can I appear for right after Class 12?",
            answer = "Right after Class 12, you are eligible for: 1) UPSC NDA (Army/Navy/Air Force officer entry), 2) SSC CHSL (LDC, Junior Secretariat Assistant), 3) SSC GD Constable & SSC Stenographer, 4) RRB ALP & Technician / Group D, 5) CUET UG (Top central university admissions), 6) CLAT (5-year Integrated BA/BBA LLB in NLUs), 7) JEE Main / NEET UG for professional technical/medical streams, 8) State Police Constable exams.",
            keyEligibility = "12th pass (Minimum percentage varies from passing marks to 50% depending on exam)",
            recommendedExams = listOf("NDA", "SSC CHSL", "SSC GD", "CUET", "CLAT"),
            salaryRange = "Rs 25,000 - Rs 85,000/month (Cadre dependent)",
            careerProgression = "Constable -> Head Constable -> ASI -> SI (or Officer Cadre directly via NDA)"
        ),
        CareerFaq(
            id = "car_2",
            category = "After Graduation",
            question = "Which top officer exams should I prepare for after completing my Bachelor's degree?",
            answer = "With any recognized Bachelor's degree, you can target: 1) UPSC Civil Services (IAS, IPS, IFS, IRS) - prestige and administrative policy leadership; 2) SSC CGL (Income Tax Inspector, ASO in MEA/CSS, Excise Inspector); 3) Banking: SBI PO, IBPS PO, RBI Grade B; 4) Defence: UPSC CDS, AFCAT; 5) State PSC Group-A / SDM / DSP examinations.",
            keyEligibility = "Graduation in any discipline from recognized university. Age typically 20-32 years.",
            recommendedExams = listOf("UPSC CSE", "SSC CGL", "SBI PO", "RBI Grade B", "CDS"),
            salaryRange = "Rs 56,100 to Rs 1,80,000/month (Pay Level 7 to 10+)",
            careerProgression = "Assistant Section Officer -> Section Officer -> Under Secretary -> Deputy Secretary (or SDM -> ADM -> DM in IAS)"
        ),
        CareerFaq(
            id = "car_3",
            category = "Banking vs SSC",
            question = "How do I choose between SSC CGL and Banking (SBI/IBPS PO)?",
            answer = "Choose SSC CGL if you prefer desk or inspector roles with stable 9-to-5 working hours, public administration, and no aggressive quarterly targets. Choose Banking if you prefer rapid predictable exam cycles (completed in 6-8 months), fast promotion tracks to AGM/GM, and high commercial exposure. Banking quant focuses heavily on high-speed Data Interpretation and Reasoning Puzzles, whereas SSC focuses on Advance Math (Trig, Geometry, Algebra) and General Awareness.",
            keyEligibility = "Graduation Degree for both.",
            recommendedExams = listOf("SSC CGL", "SBI PO", "IBPS PO"),
            salaryRange = "Rs 65,000 - Rs 95,000/month",
            careerProgression = "Scale 1 Officer -> Scale 2 Manager -> Scale 3 Chief Manager -> AGM"
        ),
        CareerFaq(
            id = "car_4",
            category = "After Class 10",
            question = "What competitive examinations are open right after Class 10 (Matriculation)?",
            answer = "After Class 10, students can apply for: 1) SSC MTS (Multi-Tasking Staff & Havaldar), 2) SSC GD Constable (CRPF, BSF, CISF, ITBP), 3) RRB Group D / Track Maintainer, 4) Indian Army Agniveer General Duty, 5) Indian Navy MR (Matric Recruit), 6) Coast Guard Navik DB, 7) State ITI & Polytechnic entrance examinations.",
            keyEligibility = "10th standard pass. Age: 17.5 to 25 years.",
            recommendedExams = listOf("SSC MTS", "SSC GD", "RRB Group D", "Indian Army Agniveer"),
            salaryRange = "Rs 21,700 - Rs 35,000/month",
            careerProgression = "MTS -> LDC -> UDC (via departmental exams)"
        )
    )

    // --- OFFICIAL NOTIFICATIONS ---
    val notifications = listOf(
        ExamNotification(
            id = "notif_1",
            examName = "SSC CGL 2026",
            title = "Tier-1 Examination City Slip & Schedule Released",
            date = "Updated 2 days ago",
            type = "Admit Card",
            details = "Staff Selection Commission has uploaded city intimation slips for Northern, Eastern, and Western Regions. Check your exam center and shift timing.",
            officialLink = "https://ssc.gov.in",
            isUrgent = true
        ),
        ExamNotification(
            id = "notif_2",
            examName = "SBI PO 2026",
            title = "Official Recruitment Notification for 2,000+ Probationary Officers",
            date = "Updated this week",
            type = "Notification",
            details = "State Bank of India invites online applications for Probationary Officer vacancies. Online registration window is open on bank careers portal.",
            officialLink = "https://sbi.co.in/careers",
            isUrgent = false
        ),
        ExamNotification(
            id = "notif_3",
            examName = "RRB NTPC 2026",
            title = "Notice on Application Status & Scrutiny of Candidates",
            date = "Updated 5 days ago",
            type = "Application Deadline",
            details = "Railway Recruitment Boards have published scrutiny status for Non-Technical Popular Categories applications. Verify photograph and signature acceptance.",
            officialLink = "https://indianrailways.gov.in",
            isUrgent = false
        ),
        ExamNotification(
            id = "notif_4",
            examName = "UPSC Civil Services",
            title = "Annual Examination Calendar & CSE Prelims 2026 Date Confirmation",
            date = "Updated 1 week ago",
            type = "Exam Notification",
            details = "UPSC confirms Civil Services Prelims date. Candidates are advised to review updated syllabus clarifications regarding CSAT qualifying criteria.",
            officialLink = "https://upsc.gov.in",
            isUrgent = false
        )
    )
}
