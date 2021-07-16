package com.maksimzotov.habits

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity() : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        add_btn.setOnClickListener {
            HabitState.currentState = State.ADD
            startActivity(Intent(this, SecondActivity::class.java))
        }

        recycler_view.adapter = Adapter

        recycler_view.addItemDecoration(
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        )

        ItemTouchHelper(object : ItemTouchHelper.Callback() {

            override fun isLongPressDragEnabled(): Boolean {
                return true
            }

            override fun isItemViewSwipeEnabled(): Boolean {
                return true
            }

            override fun clearView(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ) {
                super.clearView(recyclerView, viewHolder)
                viewHolder.itemView.setBackgroundColor(resources.getColor(R.color.white))
            }

            override fun onSelectedChanged(
                viewHolder: RecyclerView.ViewHolder?,
                actionState: Int
            ) {
                super.onSelectedChanged(viewHolder, actionState)
                if (
                    actionState == ItemTouchHelper.ACTION_STATE_DRAG ||
                    actionState == ItemTouchHelper.ACTION_STATE_SWIPE
                ) {
                    viewHolder?.itemView?.setBackgroundColor(Color.LTGRAY)
                }
            }

            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                return makeMovementFlags(
                    ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                    ItemTouchHelper.START or ItemTouchHelper.END
                )
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                onItemMove(viewHolder.adapterPosition, target.adapterPosition)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                onItemSwiped(viewHolder.adapterPosition)
            }

        }).attachToRecyclerView(recycler_view)
    }

    private fun onItemMove(from: Int, to: Int) {
        val habitFrom = Adapter.habits.removeAt(from)
        Adapter.habits.add(to, habitFrom)
        Adapter.notifyItemMoved(from, to)
    }

    private fun onItemSwiped(position: Int) {
        Adapter.habits.removeAt(position)
        Adapter.notifyItemRemoved(position)
    }
}