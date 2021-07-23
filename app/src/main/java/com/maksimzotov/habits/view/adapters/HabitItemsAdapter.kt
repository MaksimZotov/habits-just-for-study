package com.maksimzotov.habits.view.adapters

import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maksimzotov.habits.model.Habit
import com.maksimzotov.habits.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_view.view.*

class HabitItemsAdapter(
    private val onActionListener: OnActionListener
) : RecyclerView.Adapter<HabitItemsAdapter.ViewHolder>() {
    private var habits = listOf<Habit>()
    fun setHabits(habits: List<Habit>, comparator: Comparator<Habit>) {
        this.habits = habits.toMutableList().sortedWith(comparator)
        notifyDataSetChanged()
    }

    interface OnActionListener {
        fun onClick(position: Int)
    }

    class ViewHolder(
        override val containerView: View, val onActionListener: OnActionListener
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer, View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(habit: Habit) {
            containerView.name_in_view_holder.text = habit.name
            containerView.view_holder.background = ColorDrawable(habit.intColor)
        }

        override fun onClick(view: View) {
            onActionListener.onClick(adapterPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            inflater.inflate(R.layout.item_view, parent, false),
            onActionListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(habits[position])
    }

    override fun getItemCount(): Int {
        return habits.size
    }
}