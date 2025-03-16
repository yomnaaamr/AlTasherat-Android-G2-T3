package com.mahmoud.altasherat.common.presentation

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.graphics.createBitmap
import androidx.core.graphics.drawable.toDrawable

object ViewToDrawableConverter {

    fun convertViewToDrawable(view: View): Drawable {
        // Measure and layout the view if needed
        if (view.measuredWidth == 0 || view.measuredHeight == 0) {
            view.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            )
            view.layout(0, 0, view.measuredWidth, view.measuredHeight)
        }

        // Create a bitmap of the view
        val bitmap = createBitmap(view.measuredWidth, view.measuredHeight)

        // Draw the view onto the bitmap
        val canvas = Canvas(bitmap)
        view.draw(canvas)

        // Convert bitmap to drawable
        return bitmap.toDrawable(view.resources)
    }
}