package com.maksimzotov.habits.viewmodel

import androidx.lifecycle.ViewModel
import com.maksimzotov.habits.model.Habit
import com.maksimzotov.habits.model.State

class ListOfHabitsViewModel(): ViewModel() {

    val habits get() = State.habits

    fun onClick(position: Int) {
        State.position = position
    }
}