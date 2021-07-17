package com.maksimzotov.habits

import android.graphics.Color

class Habit {
    var name = ""
    var description = ""
    var priority = ""
    var type = ""
    val numberOfPerformedExercises = ""
    var frequency = ""
    var intColor = 0
    val colorRGB: String get() {
        val red = Color.red(intColor)
        val green = Color.green(intColor)
        val blue = Color.blue(intColor)
        return "$red, $green, $blue"
    }
    val colorHSV get() = String.format("#%06X", 0xFFFFFF and intColor)
}