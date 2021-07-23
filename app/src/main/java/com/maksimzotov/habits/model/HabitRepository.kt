package com.maksimzotov.habits.model

import androidx.lifecycle.LiveData

class HabitRepository(private val habitDao: HabitDao) {

    val habits: LiveData<List<Habit>> = habitDao.getHabits()

    fun add(habit: Habit) {
        habitDao.add(habit)
    }

    fun remove(habit: Habit) {
        habitDao.remove(habit)
    }

    fun update(habit: Habit) {
        habitDao.update(habit)
    }
}