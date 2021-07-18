package com.maksimzotov.habits

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_view.view.*

object Adapter : RecyclerView.Adapter<Adapter.ViewHolder>() {
    val habits = mutableListOf<Habit>()
    var curPosition = -1

    interface OnClickListener {
        fun onClick(position: Int)
    }
    var onClickListener: OnClickListener? = null

    class ViewHolder(
        override val containerView: View
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer, View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(habit: Habit) {
            containerView.name_in_view_holder.text = habit.name
            containerView.view_holder.background = ColorDrawable(habit.intColor)
        }

        override fun onClick(view: View) {
            onClickListener?.onClick(adapterPosition)
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
}