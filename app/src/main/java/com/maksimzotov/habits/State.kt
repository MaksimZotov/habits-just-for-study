package com.maksimzotov.habits

object HabitState {
    var currentState: State = State.NOTHING
}

enum class State {
    CHANGE(), ADD, NOTHING
}