package com.maksimzotov.habits.viewmodel

import androidx.lifecycle.ViewModel
import com.maksimzotov.habits.model.Habit
import com.maksimzotov.habits.model.State

class ListOfHabitsViewModel(val habits: MutableList<Habit>): ViewModel() {

    fun onClickHabit(position: Int) {
        State.curPosition = position
        State.curHabits = habits
    }
}