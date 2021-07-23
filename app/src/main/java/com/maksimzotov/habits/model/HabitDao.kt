package com.maksimzotov.habits.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface HabitDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun add(habit: Habit)

    @Delete
    suspend fun remove(habit: Habit)

    @Update()
    suspend fun update(habit: Habit)

    @Query("SELECT * FROM habit_table ORDER BY id ASC")
    fun getHabits(): LiveData<List<Habit>>
}