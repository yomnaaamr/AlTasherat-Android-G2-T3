package com.mahmoud.altasherat

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.mahmoud.altasherat.common.presentation.toErrorMessage
import com.mahmoud.altasherat.features.splash.presentation.SplashState
import com.mahmoud.altasherat.features.splash.presentation.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var splashScreenView: View
    private lateinit var mainContentView: View
    private lateinit var mainRootView: View

    private val viewModel: SplashViewModel by viewModels()
//    private var preDrawListener: ViewTreeObserver.OnPreDrawListener? = null // Store the listener reference


    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        splashScreenView = findViewById(R.id.splash_layout)
        mainContentView = findViewById(R.id.main)
        mainRootView = findViewById(R.id.main_root)


        lifecycleScope.launch {
            viewModel.state.flowWithLifecycle(lifecycle).collect { state ->
                Log.d("MainActivity", "Current SplashState: $state")
                Toast.makeText(this@MainActivity, "Current SplashState: $state", Toast.LENGTH_SHORT)
                    .show()

//                never forget the is keyword so the state got updated
                when (state) {
                    is SplashState.Loading -> {
                        // No need to set visibility here, it's handled by isContentVisible
                    }

                    is SplashState.Success -> {

                        Handler(Looper.getMainLooper()).postDelayed({
                            viewModel.showContent()
                        }, 0)

                    }

                    is SplashState.Error -> {
                        Toast.makeText(
                            this@MainActivity,
                            state.error.toErrorMessage(this@MainActivity),
                            Toast.LENGTH_LONG
                        ).show()
                        viewModel.showContent() // Show content on error as well
                    }
                }
            }
        }


        lifecycleScope.launch {
            viewModel.isContentVisible.flowWithLifecycle(lifecycle).collectLatest { isVisible ->
                mainContentView.visibility = if (isVisible) View.VISIBLE else View.GONE
                splashScreenView.visibility = if (isVisible) View.GONE else View.VISIBLE
            }
        }


//        preDrawListener = object : ViewTreeObserver.OnPreDrawListener {
//            override fun onPreDraw(): Boolean {
//                if (viewModel.state.value is SplashState.Success) {
//                    Handler(Looper.getMainLooper()).postDelayed({
//                        viewModel.showContent()
//                    }, 1000)
//                    mainRootView.viewTreeObserver.removeOnPreDrawListener(this) // Remove the listener
//                    preDrawListener = null; // nullify the reference
//                    return true
//                } else {
//                    return false
//                }
//            }
//        }
//
//        mainRootView.viewTreeObserver.addOnPreDrawListener(preDrawListener!!) // Add the listener


//        mainRootView.viewTreeObserver.addOnPreDrawListener {
//
//            if (viewModel.state.value is SplashState.Success) {
//                Handler(Looper.getMainLooper()).postDelayed({
//                    viewModel.showContent()
//                }, 1000) // Adjust the delay as needed
//                return@addOnPreDrawListener true
//            }else
//                return@addOnPreDrawListener false
//        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController


    }


}