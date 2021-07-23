package com.maksimzotov.habits.model

import androidx.lifecycle.LiveData

class HabitRepository(private val habitDao: HabitDao) {

    val habits: LiveData<List<Habit>> get() = habitDao.getHabits()

    suspend fun add(habit: Habit) {
        habitDao.add(habit)
    }

    suspend fun remove(habit: Habit) {
        habitDao.remove(habit)
    }

    suspend fun update(habit: Habit) {
        habitDao.update(habit)
    }
}