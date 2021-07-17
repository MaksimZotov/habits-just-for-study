package com.maksimzotov.habits

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.text.Editable
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.set
import kotlinx.android.synthetic.main.activity_second.*


class SecondActivity : AppCompatActivity() {
    var position = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        position = intent.getIntExtra(Adapter.POSITION_KEY, -1)

        save_habit.setOnClickListener {
            saveHabit()
        }

        val drawable = GradientDrawable(
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
        drawable.alpha = 97
        drawable.shape = GradientDrawable.RECTANGLE
        drawable.gradientType = GradientDrawable.LINEAR_GRADIENT
        drawable.cornerRadius = 20f
        bg_colors.background = drawable

        listOf<View>(
            color_0,
            color_1,
            color_2,
            color_3,
            color_4,
            color_5,
            color_6,
            color_7,
            color_8,
            color_9,
            color_10,
            color_11,
            color_12,
            color_13,
            color_14,
            color_15
        ).forEach { it.setOnClickListener {
            cur_bg.background = it.background
            if (cur_bg.background is ColorDrawable) {
                val intColor = (cur_bg.background as ColorDrawable).color
                cur_rgb.text = "RGB: ${convertIntColorToRGB(intColor)}"
                cur_hsv.text = "HSV: ${convertIntColorToHex(intColor)}"
            }
        } }

        if (position != -1) {
            val habit = Adapter.habits[position]
            name.setText(habit.name)
            cur_bg.setBackgroundColor(habit.intColor)
            cur_rgb.text = "RGB: ${convertIntColorToRGB(habit.intColor)}"
            cur_hsv.text = "HSV: ${convertIntColorToHex(habit.intColor)}"
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
            Adapter.habits.add(Habit().apply {
                name = this@SecondActivity.name.text.toString()
                description = this@SecondActivity.description.text.toString()
                if (cur_bg.background is ColorDrawable) {
                    intColor = (cur_bg.background as ColorDrawable).color
                }
            })
            onBackPressed()
            Adapter.notifyItemInserted(Adapter.habits.lastIndex)
        } else {
            Adapter.habits[position].apply {
                name = this@SecondActivity.name.text.toString()
                description = this@SecondActivity.description.text.toString()
                if (cur_bg.background is ColorDrawable) {
                    intColor = (cur_bg.background as ColorDrawable).color
                }
            }
            onBackPressed()
            Adapter.notifyItemChanged(position)
        }
    }
}