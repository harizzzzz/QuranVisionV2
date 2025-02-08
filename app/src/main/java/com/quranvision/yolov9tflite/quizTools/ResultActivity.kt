package com.quranvision.yolov9tflite.quizTools
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.quranvision.yolov9tflite.MainActivity
import com.quranvision.yolov9tflite.R

class ResultActivity:AppCompatActivity() {


        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_result)

            val userName = intent.getStringExtra(Constants.USER_NAME)
            val totalQuestions = intent.getIntExtra(Constants.TOTAL_QUESTIONS, 0)
            val score = intent.getIntExtra(Constants.SCORE, 0)

            val congratulationsTv: TextView = findViewById(R.id.congratulationsTv)
            val scoreTv: TextView = findViewById(R.id.scoreTv)
            val btnRestart: Button = findViewById(R.id.btnRestart)
            val btnBackHome: Button = findViewById(R.id.btnBackHome)

            congratulationsTv.text = "Congratulations, $userName!"
            scoreTv.text = "Your score is $score of $totalQuestions"
            btnRestart.setOnClickListener{
                val intent = Intent(this, QuizActivity::class.java)
                startActivity(intent)
                finish()
            }
            btnBackHome.setOnClickListener{
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

}