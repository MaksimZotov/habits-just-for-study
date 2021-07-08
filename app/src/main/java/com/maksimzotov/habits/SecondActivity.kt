package com.maksimzotov.habits

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class SecondActivity : AppCompatActivity() {
    private val COUNT_INFO = "COUNT_INFO"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        findViewById<TextView>(R.id.countSecondAct).apply {
            val count = intent.getIntExtra(COUNT_INFO, 0)
            text = (count * count).toString()
        }
    }
}