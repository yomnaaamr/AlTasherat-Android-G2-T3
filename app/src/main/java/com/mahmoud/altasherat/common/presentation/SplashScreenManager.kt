package com.mahmoud.altasherat.common.presentation

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.LayerDrawable
import android.view.Gravity
import android.widget.ProgressBar
import android.widget.TextView
import com.mahmoud.altasherat.R
import java.text.SimpleDateFormat
import java.util.*

object SplashScreenManager {

    fun createSplashDrawable(context: Context): LayerDrawable {
//        // Create TextView for date/time
//        val timeTextView = TextView(context).apply {
//            text = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
//                .format(Date())
//            setTextColor(Color.BLACK)
//            textSize = 24f
//            gravity = Gravity.CENTER
//            setPadding(16, 16, 16, 16)
//        }

        // Create ProgressBar
        val progressBar = ProgressBar(context).apply {
            isIndeterminate = true
            minimumWidth = 100
            minimumHeight = 100
        }

        // Create TextView for username
        val userTextView = TextView(context).apply {
            text = context.getString(R.string.altasherat_platform) // You can get this from your user preferences
            setTextColor(Color.BLACK)
            textSize = 18f
            gravity = Gravity.CENTER
            setPadding(16, 16, 16, 16)
        }

        // Convert views to drawables
//        val timeDrawable = ViewToDrawableConverter.convertViewToDrawable(timeTextView)
        val progressDrawable = ViewToDrawableConverter.convertViewToDrawable(progressBar)
        val userDrawable = ViewToDrawableConverter.convertViewToDrawable(userTextView)
        val backgroundDrawable = context.getDrawable(R.drawable.splash_background)
        val icon = context.getDrawable(R.drawable.ic_splash)

        // Create and return the layer list
//        return LayerDrawable(arrayOf(progressDrawable, userDrawable,backgroundDrawable,icon)).apply {
//            // Position the layers - adjust these values as needed
////            setLayerInset(0, 0, 100, 0, 0)  // Time at top
//            setLayerInset(0, 0, 300, 0, 200)  // Progress in middle
//            setLayerInset(1, 0, 500, 0, 0)  // Username at bottom
        return LayerDrawable(arrayOf(backgroundDrawable, icon, userDrawable, progressDrawable)).apply {
            // Position the layers - adjust these values as needed
            // Background fills the whole screen (no insets)
            // Icon in the center
            setLayerGravity(1, Gravity.CENTER)
            // Username below the icon
            setLayerInset(2, 0, context.resources.displayMetrics.heightPixels / 2 + icon!!.intrinsicHeight / 2 + 20, 0, 0) // Adjust 20 for spacing
            setLayerGravity(2, Gravity.CENTER_HORIZONTAL)
            // Progress in the bottom
            setLayerInset(3, 0, 0, 0, 100) // Adjust 100 for spacing from bottom
            setLayerGravity(3, Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM)

        }
    }
}