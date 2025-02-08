package com.quranvision.yolov9tflite

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat

class OverlayView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private var results = listOf<BoundingBox>()
    private var boxPaint = Paint()
    private var textBackgroundPaint = Paint()
    private var textPaint = Paint()

    private var bounds = Rect()
    private val classColors = mutableMapOf<String, Int>()

    private val predefinedColors = listOf(
        Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.CYAN, Color.MAGENTA
    )
    private var colorIndex = 0

    init {
        initPaints()
    }

    fun clear() {
        results = listOf()
        textPaint.reset()
        textBackgroundPaint.reset()
        boxPaint.reset()
        classColors.clear()
        invalidate()
        initPaints()
    }

    private fun initPaints() {
        textBackgroundPaint.color = Color.BLACK
        textBackgroundPaint.style = Paint.Style.FILL
        textBackgroundPaint.textSize = 50f

        textPaint.color = Color.WHITE
        textPaint.style = Paint.Style.FILL
        textPaint.textSize = 50f

    //    boxPaint.color = ContextCompat.getColor(context!!, R.color.bounding_box_color)
        boxPaint.strokeWidth = 8F
        boxPaint.style = Paint.Style.STROKE
    }

    private fun getClassColor(className: String): Int {
        return when (className) {
            "Idgham" -> Color.RED    // Explicitly set to red
            "Izhar" -> Color.BLUE   // Explicitly set to blue
            else -> classColors.getOrPut(className) {
                val color = predefinedColors[colorIndex % predefinedColors.size]
                colorIndex++
                color
            }
        }
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)

        results.forEach { boundingBox ->
            val left = boundingBox.x1 * width
            val top = boundingBox.y1 * height
            val right = boundingBox.x2 * width
            val bottom = boundingBox.y2 * height

            // Set the paint color for the bounding box
            boxPaint.color = getClassColor(boundingBox.clsName)

            // Draw the bounding box
            canvas.drawRect(left, top, right, bottom, boxPaint)

            // Prepare the text to display (class name and confidence)
            val confidenceText = boundingBox.cnf?.let { String.format("%.2f", it) } ?: ""
            val drawableText = "${boundingBox.clsName} $confidenceText"

            // Calculate the size of the text background
            textBackgroundPaint.getTextBounds(drawableText, 0, drawableText.length, bounds)
            val textWidth = bounds.width()
            val textHeight = bounds.height()

            // Draw the text background rectangle
            canvas.drawRect(
                left,
                top - textHeight - BOUNDING_RECT_TEXT_PADDING, // Above the bounding box
                left + textWidth + BOUNDING_RECT_TEXT_PADDING,
                top,
                textBackgroundPaint
            )

            // Draw the text itself
            canvas.drawText(drawableText, left, top - BOUNDING_RECT_TEXT_PADDING / 2, textPaint)
        }
    }


    fun setResults(boundingBoxes: List<BoundingBox>) {
        results = boundingBoxes
        invalidate()
    }

    companion object {
        private const val BOUNDING_RECT_TEXT_PADDING = 8
    }
}