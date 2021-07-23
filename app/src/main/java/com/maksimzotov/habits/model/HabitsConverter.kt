package com.maksimzotov.habits.model

import androidx.room.Entity
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity
class HabitsConverter {
    val gson: Gson = Gson()
    val typeToken = object : TypeToken<MutableList<Habit>>() {}.type

    @TypeConverter
    fun stringToHabits(data: String): MutableList<Habit> = gson.fromJson(data, typeToken)

    @TypeConverter
    fun habitsToString(someObjects: MutableList<Habit>): String = gson.toJson(someObjects)
}