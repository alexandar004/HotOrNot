package com.example.hotornot.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.hotornot.R
import com.example.hotornot.databinding.ActivityMainBinding
import com.example.hotornot.ui.fragment.BaseFragment
import com.google.android.material.appbar.MaterialToolbar
import kotlin.system.exitProcess

private const val ALLOWABLE_TIME_BEFORE_GO_TO_MOTIVATION_SCREEN = 100000L

class MainActivity : AppCompatActivity(), BaseFragment.ActivityListener {

    lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private var offlineTimeCounter = System.currentTimeMillis()

    lateinit var toolbar: MaterialToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController
        toolbar = binding.toolbar
        setupApplicationToolbar()
    }

    private fun setupApplicationToolbar() {
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        setSupportActionBar(toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)
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

    override fun setToolbar() {
        setToolbarArrowBackVisibility()
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id != R.id.mainScreenFragment) {
                toolbar.visibility = View.GONE
                toolbar.menu.clear()
            } else {
                toolbar.visibility = View.VISIBLE
            }
        }
    }

    private fun setToolbarArrowBackVisibility() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.registrationFormFragment,
                R.id.mainScreenFragment,
                R.id.profileScreenFragment,
                -> {
                    binding.toolbar.navigationIcon = null
                }
            }
        }
    }

    override fun onBackPressed() {
        if (Navigation.findNavController(
                this,
                R.id.navHostFragment
            ).currentDestination?.id == R.id.noNetworkConnectionScreen
        ) {
            finish()
            exitProcess(0)
        } else {
            super.onBackPressed()
        }
    }
}