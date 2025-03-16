package com.mahmoud.altasherat

import android.app.Application
import android.content.Context
import android.graphics.drawable.Drawable
import com.mahmoud.altasherat.common.presentation.SplashScreenManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApp: Application(){


    override fun onCreate() {
        super.onCreate()
//        initializeSplashDrawable(this)
    }

//    companion object {
//        private var splashDrawable: Drawable? = null
//
//        fun initializeSplashDrawable(context: Context) {
//            if (splashDrawable == null) {
//                splashDrawable = SplashScreenManager.createSplashDrawable(context)
//            }
//        }
//
//        fun getSplashDrawable(context: Context): Drawable {
//            if (splashDrawable == null) {
//                initializeSplashDrawable(context)
//            }
//            return splashDrawable!!
//        }
//    }
}