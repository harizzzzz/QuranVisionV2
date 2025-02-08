package com.quranvision.yolov9tflite

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.quranvision.yolov9tflite.databinding.ActivityAboutBinding
import com.quranvision.yolov9tflite.databinding.ActivityMainBinding

class AboutActivity:AppCompatActivity() {
    private lateinit var binding: ActivityAboutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)

        enableEdgeToEdge()
        setContentView(binding.root)
        bindListeners()
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

    }

    private fun bindListeners(){
        binding.apply {
            btnBackHome.setOnClickListener{
                backHome()
            }
        }
    }

    private fun backHome(){
        startActivity(Intent(this, MainActivity::class.java))
    }
}