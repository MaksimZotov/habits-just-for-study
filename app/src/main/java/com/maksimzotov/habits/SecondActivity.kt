package com.maksimzotov.habits

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {
    private val defaultRGB = "255, 57, 0"
    private val defaultHSV = "#FF3900"

    private val alphaBG = 97
    private val cornerRadiusBG = 20f

    private var position = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val colorsBG = GradientDrawable(
            GradientDrawable.Orientation.BL_TR,
            intArrayOf(
                Color.RED,
                Color.YELLOW,
                Color.GREEN,
                Color.CYAN,
                Color.BLUE,
                Color.MAGENTA,
                Color.RED
            )
        )
        colorsBG.alpha = alphaBG
        colorsBG.shape = GradientDrawable.RECTANGLE
        colorsBG.gradientType = GradientDrawable.LINEAR_GRADIENT
        colorsBG.cornerRadius = cornerRadiusBG
        bg_colors.background = colorsBG

        listOf<View>(
            color_0, color_1, color_2, color_3, color_4,
            color_5, color_6, color_7, color_8, color_9,
            color_10, color_11, color_12,
            color_13, color_14, color_15
        ).forEach { it.setOnClickListener {
            cur_bg.background = it.background
            val intColor = (cur_bg.background as ColorDrawable).color
            cur_rgb.text = "RGB: ${convertIntColorToRGB(intColor)}"
            cur_hsv.text = "HSV: ${convertIntColorToHex(intColor)}"
        } }

        position = intent.getIntExtra(Adapter.POSITION_KEY, -1)

        if (position != -1) {
            val habit = Adapter.habits[position]
            name.setText(habit.name)
            description.setText(habit.description)
            priority.setSelection(habit.priorityPosition)
            type.check(habit.typeId)
            number_of_performed_exercises.setText(habit.numberOfPerformedExercises)
            frequency.setText(habit.frequency)
            cur_bg.setBackgroundColor(habit.intColor)
            cur_rgb.text = "RGB: ${convertIntColorToRGB(habit.intColor)}"
            cur_hsv.text = "HSV: ${convertIntColorToHex(habit.intColor)}"
        } else {
            cur_rgb.text = "RGB: $defaultRGB"
            cur_hsv.text = "HSV: $defaultHSV"
        }

        save_habit.setOnClickListener {
            saveHabit()
        }
    }

    private fun convertIntColorToRGB(intColor: Int): String {
        val red = Color.red(intColor)
        val green = Color.green(intColor)
        val blue = Color.blue(intColor)
        return "$red, $green, $blue"
    }

    private fun convertIntColorToHex(intColor: Int): String {
        return String.format("#%06X", 0xFFFFFF and intColor)
    }

    private fun saveHabit() {
        if (position == -1) {
            Adapter.habits.add(bindHabit(Habit()))
            onBackPressed()
            Adapter.notifyItemInserted(Adapter.habits.lastIndex)
        } else {
            bindHabit(Adapter.habits[position])
            onBackPressed()
            Adapter.notifyItemChanged(position)
        }
    }

    private fun bindHabit(habit: Habit): Habit {
        habit.name = name.text.toString()
        habit.description = description.text.toString()
        habit.intColor = (cur_bg.background as ColorDrawable).color
        habit.priorityPosition = priority.selectedItemPosition
        habit.typeId = type.checkedRadioButtonId
        habit.numberOfPerformedExercises = number_of_performed_exercises.text.toString()
        habit.frequency = frequency.text.toString()
        return habit
    }
}