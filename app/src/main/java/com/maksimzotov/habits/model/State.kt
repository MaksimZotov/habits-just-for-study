package com.maksimzotov.habits.model

object State {
    val habitsImportant = mutableListOf<Habit>()
    val habitsUnimportant = mutableListOf<Habit>()
    var curHabits: MutableList<Habit> = mutableListOf()
    var curPosition = -1
}