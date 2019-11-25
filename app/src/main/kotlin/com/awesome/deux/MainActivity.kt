package com.awesome.deux

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * Main Screen. [AppCompatActivity] subclass.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}