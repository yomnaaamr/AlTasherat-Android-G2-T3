//package com.mahmoud.altasherat.common.presentation
//
//import android.content.Context
//import android.graphics.Canvas
//import android.graphics.ColorFilter
//import android.graphics.PixelFormat
//import android.graphics.drawable.Drawable
//import android.view.LayoutInflater
//import android.view.View
//import android.widget.ProgressBar
//import android.widget.TextView
//import com.mahmoud.altasherat.R
//
//class DynamicSplashDrawable(context: Context) : Drawable() {
//
//    private val view: View
//    private val progressBar: ProgressBar
//    private val textView: TextView
//
//    init {
//        view = LayoutInflater.from(context).inflate(R.layout.splash_layout, null)
//        progressBar = view.findViewById(R.id.progressBar)
//        textView = view.findViewById(R.id.tvLoading)
//
//        // Set initial layout parameters
//        view.measure(
//            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
//            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
//        )
//        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
//    }
//
//    override fun draw(canvas: Canvas) {
//        val saveCount = canvas.save()
//
//        // Center the view in the available space
//        val xPos = (bounds.width() - view.measuredWidth) / 2f
//        val yPos = (bounds.height() - view.measuredHeight) / 2f
//
//        canvas.translate(xPos, yPos)
//        view.draw(canvas)
//        canvas.restoreToCount(saveCount)
//    }
//
//    override fun setAlpha(alpha: Int) {
//        view.alpha = alpha / 255f
//    }
//
//    override fun setColorFilter(colorFilter: ColorFilter?) {
//        // Not implemented
//    }
//
//    @Deprecated("Deprecated in Java")
//    override fun getOpacity(): Int = PixelFormat.TRANSLUCENT
//
//    fun updateLoadingText(text: String) {
//        textView.text = text
//        invalidateSelf()
//    }
//
//    fun updateProgress(progress: Int) {
//        progressBar.progress = progress
//        invalidateSelf()
//    }
//}