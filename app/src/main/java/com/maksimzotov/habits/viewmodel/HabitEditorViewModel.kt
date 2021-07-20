package com.maksimzotov.habits.viewmodel

import androidx.lifecycle.ViewModel
import com.maksimzotov.habits.model.State

class HabitEditorViewModel : ViewModel() {

    val habits get() = State.habits

    var position get() = State.position
        set(value) {
            State.position = value
        }
}