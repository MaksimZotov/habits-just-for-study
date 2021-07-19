package com.maksimzotov.habits

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class ListOfHabitsFragment(
    val habits: MutableList<Habit>
) : Fragment(), HabitsAdapter.OnClickListener, HabitsAdapter.OnDataSetChangedListener {

    private var prevBG: Drawable? = null
    private lateinit var habitsAdapter: HabitsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list_of_habits, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)

        habitsAdapter = HabitsAdapter(habits, this)

        recyclerView.adapter = habitsAdapter

        recyclerView.addItemDecoration(
            DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL)
        )

        ItemTouchHelper(object : ItemTouchHelper.Callback() {
            override fun isLongPressDragEnabled() = true
            override fun isItemViewSwipeEnabled() = true

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
                val from = viewHolder.adapterPosition
                val to = target.adapterPosition
                val habitFrom = habitsAdapter.habits.removeAt(from)
                habitsAdapter.habits.add(to, habitFrom)
                habitsAdapter.notifyItemMoved(from, to)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                habitsAdapter.habits.removeAt(viewHolder.adapterPosition)
                habitsAdapter.notifyItemRemoved(viewHolder.adapterPosition)
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
                    prevBG = viewHolder?.itemView?.background
                    viewHolder?.itemView?.background = ColorDrawable(Color.LTGRAY)
                }
            }

            override fun clearView(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ) {
                super.clearView(recyclerView, viewHolder)
                viewHolder.itemView.background = prevBG
            }

        }).attachToRecyclerView(recyclerView)

        return view
    }

    override fun onClick(position: Int) {
        Logic.curPosition = position
        findNavController().navigate(R.id.habitEditorFragment)
    }

    override fun update() {
        if (this::habitsAdapter.isInitialized) habitsAdapter.notifyDataSetChanged()
    }
}