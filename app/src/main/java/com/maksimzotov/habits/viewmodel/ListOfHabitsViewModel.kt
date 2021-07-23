package com.maksimzotov.habits.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.maksimzotov.habits.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListOfHabitsViewModel(application: Application): AndroidViewModel(application) {

    private val repository = HabitRepository(
        HabitDatabase
            .getDatabase(application)
            .habitDao()
    )

    val habits: LiveData<List<Habit>> = repository.habits
    val habitsSize get() = habits.value?.size ?: 0

    var curHabit: Habit? = null

    fun onClickAddHabit() {
        curHabit = null
    }

    fun onClickHabit(position: Int) {
        curHabit = habits.value?.get(position)
    }

    fun add(habit: Habit) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.add(habit)
        }
    }

    fun remove(position: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            habits.value?.get(position)?.let { repository.remove(it) }
        }
    }

    fun update(habit: Habit) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(habit)
        }
    }
}