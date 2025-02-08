package com.quranvision.yolov9tflite.quizTools

import com.quranvision.yolov9tflite.R
object Constants {
    val USER_NAME: String = "user_name"
    val TOTAL_QUESTIONS: String = "total_questions"
    val SCORE: String = "score"

    fun getQuestions(): ArrayList<Question> {
        val questionsList = ArrayList<Question>()

        // 1
        val questionOne = Question(
            1,
            "What is the underlined Tajweed rule?",
            R.drawable.quiz1,
            arrayListOf("Idgham Shafawee", "Izhar Shafawee", "Ikhfa' Shafawee"),
            1,
        )
        questionsList.add(questionOne)

        // 2
        val questionTwo = Question(
            2,
            "Select the correct answer for the underlined Tajweed rule",
            R.drawable.quiz2,
            arrayListOf("Izhar Shafawee", "Ikhfa' Shafawee",
                "Idgham Shafawee"),
            2
        )
        questionsList.add(questionTwo)

        // 3
        val questionThree = Question(
            3,
            "Select the correct answer for the underlined Tajweed rule",
            R.drawable.quiz3,
            arrayListOf("Izhar Shafawee", "Idgham Shafawee",
                "Ikhfa' Shafawee"),
            0
        )
        questionsList.add(questionThree)

        // 4
        val questionFour = Question(
            4,
            "Select the correct answer for the underlined Tajweed rule",
            R.drawable.quiz4,
            arrayListOf("Izhar Shafawee", "Idgham Shafawee",
                "Ikhfa' Shafawee"),
            0
        )
        questionsList.add(questionFour)

        // 5
        val questionFive = Question(
            5,
            "Select the correct answer for the underlined Tajweed rule",
            R.drawable.quiz5,
            arrayListOf("Idgham Shafawee", "Ikhfa' Shafawee",
                "Izhar Shafawee"),
            0
        )
        questionsList.add(questionFive)

//        // 6
//        val questionSix = Question(
//            6,
//            "What country does this flag belong to?",
//            R.drawable.ic_flag_of_germany,
//            arrayListOf("Germany", "Georgia",
//                "Greece", "none of these"),
//            0
//        )
//
//        questionsList.add(questionSix)
//
//        // 7
//        val questionSeven = Question(
//            7,
//            "What country does this flag belong to?",
//            R.drawable.ic_flag_of_denmark,
//            arrayListOf("Dominica", "Egypt",
//                "Denmark", "Ethiopia"),
//            2
//        )
//        questionsList.add(questionSeven)
//
//        // 8
//        val questionEight = Question(
//            8,
//            "What country does this flag belong to?",
//            R.drawable.ic_flag_of_india,
//            arrayListOf("Ireland", "Iran",
//                "Hungary", "India"),
//            3
//        )
//        questionsList.add(questionEight)
//
//        // 9
//        val questionNine = Question(
//            9,
//            "What country does this flag belong to?",
//            R.drawable.ic_flag_of_new_zealand,
//            arrayListOf("Australia", "New Zealand",
//                "Tuvalu", "United States of America"),
//            1
//        )
//        questionsList.add(questionNine)
//
//        // 10
//        val questionTen = Question(
//            10,
//            "What country does this flag belong to?",
//            R.drawable.ic_flag_of_kuwait,
//            arrayListOf("Kuwait", "Jordan",
//                "Sudan", "Palestine"),
//            0
//        )
//        questionsList.add(questionTen)

        return questionsList
    }
}