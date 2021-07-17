package com.maksimzotov.habits

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
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
        //drawable.alpha = 85
        drawable.shape = GradientDrawable.RECTANGLE
        drawable.gradientType = GradientDrawable.LINEAR_GRADIENT
        //drawable.cornerRadius = 40f
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
        } }
    }

    fun saveHabit() {
        Adapter.habits.add(Habit().apply {
            firstField = first_field_edit.text.toString()
            secondField = second_field_edit.text.toString()
        })
        onBackPressed()
        Adapter.notifyItemInserted(Adapter.habits.lastIndex)
    }

    fun createColors() {
        val colorsView = listOf<View>(
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
        )
        val colorsRGB = listOf<Triple<Int, Int, Int>>(

        )
        val colorsHex = listOf<String>(
            "#FF0000",
            "#FF5F00",
            "#FFBF00",
            "#DFFF00",
            "#7FFF00",
            "#1FFF00",
            "#00FF3F",
            "#00FF9F",
            "#00FFFF",
            "#009FFF",
            "#001FFF",
            "#3F00FF",
            "#9F00FF",
            "#FF00FF",
            "#FF009F",
            "#FF003F"
        )
    }
}