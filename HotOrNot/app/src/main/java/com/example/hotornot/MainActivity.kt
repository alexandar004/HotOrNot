package com.example.hotornot

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var splashScreen: SplashScreen

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        splashScreen = SplashScreen()
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainer, splashScreen)
            .commit()
    }
}