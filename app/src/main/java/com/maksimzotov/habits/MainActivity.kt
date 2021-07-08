package com.maksimzotov.habits

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private val COUNT_INFO = "COUNT_INFO"
    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.next).setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java).apply {
                putExtra(COUNT_INFO, count)
            })
        }

        findViewById<TextView>(R.id.countMainAct).apply {
            text = count.toString()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(COUNT_INFO, count)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        if (savedInstanceState.containsKey(COUNT_INFO)) {
            count = savedInstanceState.getInt(COUNT_INFO)
        }
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        count++
        findViewById<TextView>(R.id.countMainAct).apply {
            text = count.toString()
        }
        super.onConfigurationChanged(newConfig)
    }
}