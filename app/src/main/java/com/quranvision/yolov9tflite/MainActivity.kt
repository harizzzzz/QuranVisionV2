package com.quranvision.yolov9tflite

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Matrix
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.AspectRatio
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.quranvision.yolov9tflite.Constants.LABELS_PATH
import com.quranvision.yolov9tflite.Constants.MODEL_PATH
import com.quranvision.yolov9tflite.databinding.ActivityMainBinding
import com.quranvision.yolov9tflite.databinding.ActivityDetectionBinding
import com.quranvision.yolov9tflite.learnTools.LearnActivity
import com.quranvision.yolov9tflite.quizTools.QuizActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity:AppCompatActivity(){
    private lateinit var binding:ActivityMainBinding

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        bindListeners()
    }

    private fun bindListeners(){
        binding.apply {
            detectButton.setOnClickListener{
                openDetect()
            }
            quizButton.setOnClickListener{
                openQuiz()
            }
            learnButton.setOnClickListener{
                openLearn()
            }
            aboutButton.setOnClickListener{
                openAbout()
            }
        }
    }

    private fun openDetect(){
        startActivity(Intent(this, DetectionActivity::class.java))

    }

    private fun openQuiz(){
        startActivity(Intent(this, QuizActivity::class.java))

    }
    private fun openLearn(){
        startActivity(Intent(this, LearnActivity::class.java))

    }

    private fun openAbout(){
        startActivity(Intent(this, AboutActivity::class.java))

    }
}

//class MainActivity : AppCompatActivity(), Detector.DetectorListener {
//    private lateinit var binding: ActivityMainBinding
//    private val isFrontCamera = false
//
//    private var preview: Preview? = null
//    private var imageAnalyzer: ImageAnalysis? = null
//    private var camera: Camera? = null
//    private var cameraProvider: ProcessCameraProvider? = null
//
//    private var detector: Detector? = null
//
//    private lateinit var cameraExecutor: ExecutorService
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityMainBinding.inflate(layoutInflater)
//
//        enableEdgeToEdge()
//        setContentView(binding.root)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
//
//        cameraExecutor = Executors.newSingleThreadExecutor()
//
//        cameraExecutor.execute {
//            detector = Detector(baseContext, MODEL_PATH, LABELS_PATH, this) {
//                toast(it)
//            }
//        }
//
//        if (allPermissionsGranted()) {
//            startCamera()
//        } else {
//            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
//        }
//
//        bindListeners()
//    }
//
//    private fun bindListeners() {
//        binding.apply {
//            isGpu.setOnCheckedChangeListener { buttonView, isChecked ->
//                cameraExecutor.submit {
//                    detector?.restart(isGpu = isChecked)
//                }
//                if (isChecked) {
//                    buttonView.setBackgroundColor(ContextCompat.getColor(baseContext, R.color.orange))
//                } else {
//                    buttonView.setBackgroundColor(ContextCompat.getColor(baseContext, R.color.gray))
//                }
//            }
//        }
//    }
//
//    private fun startCamera() {
//        val cameraProviderFuture = ProcessCameraProvider.getInstance(baseContext)
//        cameraProviderFuture.addListener({
//            cameraProvider  = cameraProviderFuture.get()
//            bindCameraUseCases()
//        }, ContextCompat.getMainExecutor(this))
//    }
//
//    private fun bindCameraUseCases() {
//        val cameraProvider = cameraProvider ?: throw IllegalStateException("Camera initialization failed.")
//
//        val rotation = binding.viewFinder.display.rotation
//
//        val cameraSelector = CameraSelector
//            .Builder()
//            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
//            .build()
//
//        preview =  Preview.Builder()
//            .setTargetAspectRatio(AspectRatio.RATIO_4_3)
//            .setTargetRotation(rotation)
//            .build()
//
//        imageAnalyzer = ImageAnalysis.Builder()
//            .setTargetAspectRatio(AspectRatio.RATIO_4_3)
//            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
//            .setTargetRotation(binding.viewFinder.display.rotation)
//            .setOutputImageFormat(ImageAnalysis.OUTPUT_IMAGE_FORMAT_RGBA_8888)
//            .build()
//
//        imageAnalyzer?.setAnalyzer(cameraExecutor) { imageProxy ->
//            val bitmapBuffer =
//                Bitmap.createBitmap(
//                    imageProxy.width,
//                    imageProxy.height,
//                    Bitmap.Config.ARGB_8888
//                )
//            imageProxy.use { bitmapBuffer.copyPixelsFromBuffer(imageProxy.planes[0].buffer) }
//            imageProxy.close()
//
//            val matrix = Matrix().apply {
//                postRotate(imageProxy.imageInfo.rotationDegrees.toFloat())
//
//                if (isFrontCamera) {
//                    postScale(
//                        -1f,
//                        1f,
//                        imageProxy.width.toFloat(),
//                        imageProxy.height.toFloat()
//                    )
//                }
//            }
//
//            val rotatedBitmap = Bitmap.createBitmap(
//                bitmapBuffer, 0, 0, bitmapBuffer.width, bitmapBuffer.height,
//                matrix, true
//            )
//
//            detector?.detect(rotatedBitmap)
//        }
//
//        cameraProvider.unbindAll()
//
//        try {
//            camera = cameraProvider.bindToLifecycle(
//                this,
//                cameraSelector,
//                preview,
//                imageAnalyzer
//            )
//
//            preview?.surfaceProvider = binding.viewFinder.surfaceProvider
//        } catch(exc: Exception) {
//            Log.e(TAG, "Use case binding failed", exc)
//        }
//
//    }
//
//    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
//        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
//    }
//
//    private val requestPermissionLauncher = registerForActivityResult(
//        ActivityResultContracts.RequestMultiplePermissions()) {
//        if (it[Manifest.permission.CAMERA] == true) { startCamera() }
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        detector?.close()
//        cameraExecutor.shutdown()
//    }
//
//    override fun onResume() {
//        super.onResume()
//        if (allPermissionsGranted()){
//            startCamera()
//        } else {
//            requestPermissionLauncher.launch(REQUIRED_PERMISSIONS)
//        }
//    }
//
//    companion object {
//        private const val TAG = "Camera"
//        private const val REQUEST_CODE_PERMISSIONS = 10
//        private val REQUIRED_PERMISSIONS = mutableListOf (
//            Manifest.permission.CAMERA
//        ).toTypedArray()
//    }
//
//    override fun onEmptyDetect() {
//        runOnUiThread {
//            binding.overlay.clear()
//        }
//    }
//
//    override fun onDetect(boundingBoxes: List<BoundingBox>, inferenceTime: Long) {
//        runOnUiThread {
//            binding.inferenceTime.text = "${inferenceTime}ms"
//            binding.overlay.apply {
//                setResults(boundingBoxes)
//                invalidate()
//            }
//        }
//    }
//
//    private fun toast(message: String) {
//        lifecycleScope.launch(Dispatchers.Main) {
//            Toast.makeText(baseContext, message, Toast.LENGTH_LONG).show()
//        }
//    }
//}
