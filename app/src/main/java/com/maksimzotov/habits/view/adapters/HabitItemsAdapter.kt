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
    val habits: MutableList<Habit>,
    private val onClickListener: OnClickListener
) : RecyclerView.Adapter<HabitItemsAdapter.ViewHolder>() {

    interface OnClickListener {
        fun onClick(position: Int)
    }

    class ViewHolder(
        override val containerView: View, val onClickListener: OnClickListener
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer, View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(habit: Habit) {
            containerView.name_in_view_holder.text = habit.name
            containerView.view_holder.background = ColorDrawable(habit.intColor)
        }

        override fun onClick(view: View) {
            onClickListener.onClick(adapterPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            inflater.inflate(R.layout.item_view, parent, false),
            onClickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(habits[position])
    }

    override fun getItemCount(): Int {
        return habits.size
    }
}