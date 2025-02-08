package com.quranvision.yolov9tflite.learnTools

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.PersistableBundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.quranvision.yolov9tflite.DetectionActivity
import com.quranvision.yolov9tflite.MainActivity
import com.quranvision.yolov9tflite.R
import com.quranvision.yolov9tflite.databinding.ActivityLearnBinding

class LearnActivity:AppCompatActivity() {
    private lateinit var binding: ActivityLearnBinding
    private var audioIzhar: ArrayList<AudioTajweed> = Constants.getAudioIzhar()
    private var currentAudioIndex = 0
    private var imageView: ImageView? =null
    private var descLabel: TextView? =null
    private var titleView:TextView? =null
    private var expView:TextView? =null
    private lateinit var nextButton:ImageButton
    private lateinit var prevButton:ImageButton
    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var playButton:ImageButton
    private lateinit var seekBar:SeekBar
    private lateinit var seekProgress:TextView
    private var mediaPlayer: MediaPlayer? = null
    private val handler = Handler(Looper.getMainLooper())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityLearnBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        //TODO:top label components
        titleView=findViewById(R.id.titleView)
        expView=findViewById(R.id.expView)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
        //TODO:establish component for examples
        descLabel=findViewById(R.id.descLabel)
        imageView=findViewById(R.id.imageView)

        bindListeners()
        val drawerLayout:DrawerLayout = findViewById(R.id.drawerLayout)
        val navView:NavigationView = findViewById(R.id.nav_view)
        val toolBar:Toolbar = findViewById(R.id.my_toolbar)
        setSupportActionBar(toolBar)
        toggle= ActionBarDrawerToggle(this,drawerLayout,toolBar,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //audioplayer_tools
        playButton=findViewById(R.id.playButton)
        seekBar=findViewById(R.id.seekBar)
        seekProgress=findViewById(R.id.seekProgress)
        nextButton=findViewById(R.id.nextButton)
        prevButton=findViewById(R.id.prevButton)

        defaultNav()
//        mediaPlayer = MediaPlayer.create(this, audioIzhar[currentAudioIndex].audio)  // Replace with your .wav file
//        mediaPlayer?.setOnCompletionListener {
//            resetPlayer()
//        }
//
//        seekBar.max = mediaPlayer?.duration ?: 0

        playButton.setOnClickListener {
            if (mediaPlayer?.isPlaying == true) {
                pauseAudio()
            } else {
                playAudio()
            }
        }

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayer?.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        updateSeekBar()

        nextButton.setOnClickListener{
            if(currentAudioIndex==audioIzhar.size -1){
                currentAudioIndex=0
                updateAudio()
            }else{
                if(currentAudioIndex<audioIzhar.size-1){
                    currentAudioIndex++
                    updateAudio()
                }
            }
        }

        prevButton.setOnClickListener{
            if(currentAudioIndex==0){
                currentAudioIndex=audioIzhar.size-1
                updateAudio()
            }else{
                if(currentAudioIndex>0){
                    currentAudioIndex--
                    updateAudio()
                }
            }
        }

        //changeNav
        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.learnIzhar->defaultNav()
                R.id.learnIdgham->IdghamNav()
            }
            drawerLayout.closeDrawers()
            true
        }


    }

    private fun updateAudio(){
        //Render example image
        imageView?.setImageResource(audioIzhar[currentAudioIndex].image)

        //Render example description label
        descLabel?.text=audioIzhar[currentAudioIndex].desc

        //Render audio example
//        mediaPlayer?.release()
//        mediaPlayer = MediaPlayer.create(this,audioIzhar[currentAudioIndex].audio)
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer.create(this, audioIzhar[currentAudioIndex].audio)  // Replace with your .wav file
        mediaPlayer?.setOnCompletionListener {
            resetPlayer()
        }

        seekBar.max = mediaPlayer?.duration ?: 0

    }

    private fun defaultNav(){
        titleView?.text=Constants.titleIzhar
        expView?.text=Constants.expIzhar
        audioIzhar= Constants.getAudioIzhar()
        updateAudio()
    }

    private fun IdghamNav(){
        titleView?.text=Constants.titleIdgham
        expView?.text=Constants.expIdgham
        audioIzhar= Constants.getAudioIdgham()
        updateAudio()
    }



    private fun bindListeners(){
        binding.apply {
            btnHome.setOnClickListener{
                backHome()
            }
        }
    }

    private fun backHome(){
        startActivity(Intent(this, MainActivity::class.java))
    }

    //function_support_audio
    private fun playAudio() {
        mediaPlayer?.start()
        //btnPlayPause.text = "Pause"
        playButton.setImageResource(android.R.drawable.ic_media_pause)
        updateSeekBar()
    }

    private fun pauseAudio() {
        mediaPlayer?.pause()
//        btnPlayPause.text = "Play"
        playButton.setImageResource(android.R.drawable.ic_media_play)

    }

    private fun resetPlayer() {
        mediaPlayer?.seekTo(0)
        seekBar.progress = 0
        seekProgress.text = "00:00"
        pauseAudio()
    }

    private fun updateSeekBar() {
        handler.postDelayed(object : Runnable {
            override fun run() {
                mediaPlayer?.let {
                    if (it.isPlaying) {
                        seekBar.progress = it.currentPosition
                        seekProgress.text = formatTime(it.currentPosition)
                    }
                    handler.postDelayed(this, 500)
                }
            }
        }, 0)
    }

    private fun formatTime(milliseconds: Int): String {
        val seconds = (milliseconds / 1000) % 60
        val minutes = (milliseconds / 1000) / 60
        return String.format("%02d:%02d", minutes, seconds)
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}