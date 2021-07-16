package com.maksimzotov.habits

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        save_habit.setOnClickListener {
            saveHabit()
        }
    }

    fun saveHabit() {
        Adapter.habits.add(Habit().apply {
            firstField = first_field_edit.text.toString()
            secondField = second_field_edit.text.toString()
        })
        onBackPressed()
        Adapter.notifyItemInserted(Adapter.habits.lastIndex)
    }
}