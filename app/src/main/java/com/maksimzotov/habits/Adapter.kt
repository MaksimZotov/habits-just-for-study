package com.maksimzotov.habits

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_view.view.*
import java.util.logging.Handler

object Adapter : RecyclerView.Adapter<Adapter.ViewHolder>() {
    val habits = mutableListOf<Habit>()

    init {
        addHabits(20)
    }

    class ViewHolder(
        override val containerView: View
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(habit: Habit) {
            containerView.first_field.text = habit.firstField
            containerView.second_field.text = habit.secondField
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_view, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(habits[position])
    }

    override fun getItemCount(): Int {
        return habits.size
    }

    private fun addHabits(size: Int) {
        for (i in 0 until size) {
            habits.add(Habit().apply {
                firstField = "the first field ${i + 1}"
                secondField = "the second field ${i + 1}"
            })
        }
    }
}