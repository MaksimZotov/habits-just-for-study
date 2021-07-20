package com.maksimzotov.habits.view.fragments

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.maksimzotov.habits.*
import com.maksimzotov.habits.model.Habit
import com.maksimzotov.habits.view.adapters.HabitItemsAdapter
import com.maksimzotov.habits.view.listeners.OnDataSetChangedListener
import com.maksimzotov.habits.viewmodel.ListOfHabitsViewModel

class ListOfHabitsFragment(
    val habits: MutableList<Habit>,
    val onDataSetChangedListener: OnDataSetChangedListener
) : Fragment(), HabitItemsAdapter.OnClickListener, HabitItemsAdapter.OnDataSetChangedListener {

    private lateinit var viewModel: ListOfHabitsViewModel

    private var prevBG: Drawable? = null
    private lateinit var habitItemsAdapter: HabitItemsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return ListOfHabitsViewModel(habits) as T
            }
        }).get(ListOfHabitsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list_of_habits, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)

        habitItemsAdapter = HabitItemsAdapter(habits, this)

        recyclerView.adapter = habitItemsAdapter

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
                val habitFrom = habitItemsAdapter.habits.removeAt(from)
                habitItemsAdapter.habits.add(to, habitFrom)
                habitItemsAdapter.notifyItemMoved(from, to)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                habitItemsAdapter.habits.removeAt(viewHolder.adapterPosition)
                habitItemsAdapter.notifyItemRemoved(viewHolder.adapterPosition)
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

    override fun onResume() {
        super.onResume()
        onDataSetChangedListener.update()
    }

    override fun onClick(position: Int) {
        viewModel.onClickHabit(position)
        findNavController().navigate(R.id.habitEditorFragment)
    }

    override fun update() {
        if (this::habitItemsAdapter.isInitialized) {
            habitItemsAdapter.notifyDataSetChanged()
        }
    }
}