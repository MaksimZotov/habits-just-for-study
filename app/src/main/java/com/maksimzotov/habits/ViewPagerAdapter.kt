package com.maksimzotov.habits

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import java.lang.IllegalArgumentException

class ViewPagerAdapter (
    activity: AppCompatActivity
): FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment = when(position) {
        0 -> ListOfHabitsFragment(Logic.habitsImportant)
        1 -> ListOfHabitsFragment(Logic.habitsUnimportant)
        else -> throw IllegalArgumentException()
    }
}