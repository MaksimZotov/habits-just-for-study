package com.maksimzotov.habits

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_second.*


class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        save_habit.setOnClickListener {
            saveHabit()
        }

        val drawable = GradientDrawable(
            GradientDrawable.Orientation.BL_TR,
            intArrayOf(Color.RED, Color.GREEN, Color.BLUE, Color.CYAN, Color.MAGENTA)
        )
        drawable.shape = GradientDrawable.RECTANGLE
        drawable.gradientType = GradientDrawable.LINEAR_GRADIENT
        drawable.cornerRadius = 40f
        bg_colors.background = drawable
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