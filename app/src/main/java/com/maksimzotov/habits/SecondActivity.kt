package com.maksimzotov.habits

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
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
        val colorsRGB = listOf(
            Triple(255, 0, 0)
        )
        val colorsHex = listOf(
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

    fun translateRGBToHex(rgb: Triple<Int, Int, Int>): String {
        val map10To16 = mapOf(
            0 to '0', 1 to '1', 2 to '2', 3 to '3', 4 to '4', 5 to '5',
            6 to '6', 7 to '7', 8 to '8', 9 to '9', 10 to 'A', 11 to 'B',
            12 to 'C', 13 to 'D', 14 to 'E', 15 to 'F'
        )
        val strBuilder = StringBuilder("#")

        val listOfRedGreenBlue = listOf(rgb.first, rgb.second, rgb.third)

        for (number in listOfRedGreenBlue) {
            val localStrBuilder = StringBuilder()
            var mod = number
            var div = number
            while (mod >= 16) {
                mod = div % 16
                localStrBuilder.append(map10To16[mod])
                div /= 16
            }
            strBuilder.append(localStrBuilder.reversed())
        }

        return strBuilder.toString()
    }
}