package com.example.hotornot.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.hotornot.R

private const val ALLOWABLE_TIME_BEFORE_GO_TO_MOTIVATION_SCREEN = 100L

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private var offlineTimeCounter = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
        supportActionBar?.hide()
    }

    override fun onPause() {
        super.onPause()
        offlineTimeCounter = System.currentTimeMillis()
    }

    override fun onResume() {
        super.onResume()
        checkForPauseTime()
    }

    private fun checkForPauseTime() {
        val seconds = System.currentTimeMillis()
        if ((seconds - offlineTimeCounter) > ALLOWABLE_TIME_BEFORE_GO_TO_MOTIVATION_SCREEN) {
            goToMotivationScreen()
        }
    }

    private fun goToMotivationScreen() {
        val navController = Navigation.findNavController(this, R.id.navHostFragment)
        val currentDestination = navController.currentDestination?.id
        if (currentDestination != R.id.registrationFormFragment && currentDestination != R.id.splashScreenFragment)
            navController.navigate(R.id.motivationScreen)
    }
}