package com.maksimzotov.habits.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habit_table")
class Habit(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var number: Int,
    var name: String = "",
    var description: String = "",
    var priorityPosition: Int = -1,
    var typeId: Int = -1,
    var numberOfPerformedExercises: String = "",
    var frequency: String = "",
    var intColor: Int = -50944
)