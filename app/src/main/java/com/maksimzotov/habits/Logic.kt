package com.maksimzotov.habits

object Logic {
    val habitsImportant = mutableListOf<Habit>()
    val habitsUnimportant = mutableListOf<Habit>()
    var curHabits: MutableList<Habit> = mutableListOf()
    var curPosition = -1
}