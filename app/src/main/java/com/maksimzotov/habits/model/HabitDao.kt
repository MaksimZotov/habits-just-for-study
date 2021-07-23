package com.maksimzotov.habits.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface HabitDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun add(habit: Habit)

    @Delete
    fun remove(habit: Habit)

    @Update()
    fun update(habit: Habit)

    @Query("SELECT * FROM habit_table ORDER BY id ASC")
    fun getHabits(): LiveData<List<Habit>>
}