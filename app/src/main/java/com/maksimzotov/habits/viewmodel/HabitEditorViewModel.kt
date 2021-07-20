package com.maksimzotov.habits.viewmodel

import androidx.lifecycle.ViewModel
import com.maksimzotov.habits.model.State

class HabitEditorViewModel : ViewModel() {

    val habitsImportant get() = State.habitsImportant
    val habitsUnimportant get() = State.habitsUnimportant

    val curHabits get() = State.curHabits
    var curPosition get() = State.curPosition
        set(value) {
            State.curPosition = value
        }
}