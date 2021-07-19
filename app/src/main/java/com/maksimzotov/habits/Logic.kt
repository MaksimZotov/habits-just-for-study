package com.maksimzotov.habits

object Logic {
    val habits = mutableListOf<Habit>()
    val habitsImportant get() = habits.filter { it.priorityPosition == 0 }.toMutableList()
    val habitsUnimportant get() = habits.filter { it.priorityPosition == 1 }.toMutableList()
    var curPosition = -1
    var itemState: ItemState = ItemState.NOTHING
}

enum class ItemState {
    ITEM_INSERTED, ITEM_CHANGED, NOTHING
}