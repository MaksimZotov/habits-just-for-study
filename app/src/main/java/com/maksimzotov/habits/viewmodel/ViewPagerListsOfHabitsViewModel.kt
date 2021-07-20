package com.maksimzotov.habits.viewmodel
import androidx.lifecycle.ViewModel
import com.maksimzotov.habits.model.State

class ViewPagerListsOfHabitsViewModel : ViewModel() {

    val habitsImportant get() = State.habitsImportant
    val habitsUnimportant get() = State.habitsUnimportant

    fun onClickAddHabit() {
        State.curPosition = -1
    }
}