package com.mahmoud.altasherat

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mahmoud.altasherat.common.presentation.base.delegators.MessageType
import com.mahmoud.altasherat.common.presentation.utils.changeLocale
import com.mahmoud.altasherat.common.presentation.utils.toErrorMessage
import com.mahmoud.altasherat.common.util.Constants
import com.mahmoud.altasherat.features.splash.presentation.SplashContract
import com.mahmoud.altasherat.features.splash.presentation.SplashState
import com.mahmoud.altasherat.features.splash.presentation.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var splashScreenView: View
    private lateinit var mainContentView: View
    private lateinit var mainRootView: View

    private val viewModel: SplashViewModel by viewModels()

    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

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

                    else -> {}
                }
            }
        }


        lifecycleScope.launch {
            viewModel.isContentVisible.flowWithLifecycle(lifecycle).collectLatest { isVisible ->
                mainContentView.visibility = if (isVisible) View.VISIBLE else View.GONE
                splashScreenView.visibility = if (isVisible) View.GONE else View.VISIBLE
            }
        }



        lifecycleScope.launch {
            viewModel.events.flowWithLifecycle(lifecycle).collect { splashEvent ->

                when (splashEvent) {
                    is SplashContract.SplashEvent.NavigateToHome -> {
                        navController.navigate(R.id.action_languageFragment_to_home_nav_graph)
                    }

                    is SplashContract.SplashEvent.Error -> {
                        val errorMessage =
                            splashEvent.error.toErrorMessage(this@MainActivity)
                        Log.e("Splash error", errorMessage)
                    }

//                    SplashContract.SplashEvent.NavigateToLanguage -> {
//                        navController.navigate(R.id.action_splashFragment_to_languageFragment)
//                        findNavController().navigate(R.id.action_splashFragment_to_languageFragment)
//                    }

                    SplashContract.SplashEvent.NavigateToAuth -> {
                        navController.navigate(R.id.action_languageFragment_to_authFragment)

                    }
                }
            }
        }


        lifecycleScope.launch {
            viewModel.languageCode.flowWithLifecycle(lifecycle).collect { languageCode ->
                if (languageCode != null) {
                    this@MainActivity.changeLocale(languageCode)
                } else {
                    //  Set a default locale
                    this@MainActivity.changeLocale(Constants.LOCALE_EN)
                }
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


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        bottomNav = findViewById(R.id.bottom_navigation)

        bottomNav.setupWithNavController(navController)


        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.requestsFragment,R.id.dashboardFragment,
                R.id.menuFragment -> {
                    bottomNav.visibility = View.VISIBLE
                }
                else -> bottomNav.visibility = View.GONE
            }
        }


    }


}