package com.maksimzotov.habits.view.fragments

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.maksimzotov.habits.*
import com.maksimzotov.habits.view.adapters.HabitItemsAdapter
import com.maksimzotov.habits.view.listeners.DrawerLockModeListener
import com.maksimzotov.habits.viewmodel.ListOfHabitsViewModel
import kotlinx.android.synthetic.main.fragment_list_of_habits.view.*

class ListOfHabitsFragment()
    : Fragment(), HabitItemsAdapter.OnClickListener {

    private val viewModel by viewModels<ListOfHabitsViewModel>()

    private var prevBG: Drawable? = null
    private lateinit var habitItemsAdapter: HabitItemsAdapter

    override fun onResume() {
        super.onResume()
        (requireActivity() as DrawerLockModeListener).unlock()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list_of_habits, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)

        habitItemsAdapter = HabitItemsAdapter(viewModel.habits, this)

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

        view.add_habit.setOnClickListener { onClick(-1) }

        habitItemsAdapter.notifyDataSetChanged()

        val activity = requireActivity()
        (activity
            .getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager)
            .hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)

        return view
    }

    override fun onClick(position: Int) {
        viewModel.onClick(position)
        findNavController().navigate(R.id.action_listOfHabitsFragment_to_habitEditorFragment)
    }
}