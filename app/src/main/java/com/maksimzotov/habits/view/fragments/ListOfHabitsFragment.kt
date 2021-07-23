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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.maksimzotov.habits.*
import com.maksimzotov.habits.model.Habit
import com.maksimzotov.habits.view.adapters.HabitItemsAdapter
import com.maksimzotov.habits.view.listeners.DrawerLockModeListener
import com.maksimzotov.habits.viewmodel.ListOfHabitsViewModel
import kotlinx.android.synthetic.main.fragment_list_of_habits.view.*

class ListOfHabitsFragment()
    : Fragment(), HabitItemsAdapter.OnActionListener {

    private lateinit var viewModel: ListOfHabitsViewModel

    private val comparatorByNumber = object : Comparator<Habit> {
        override fun compare(o1: Habit, o2: Habit): Int {
            return o1.number.compareTo(o2.number)
        }
    }

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

        viewModel = ViewModelProvider(requireActivity()).get(ListOfHabitsViewModel::class.java)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)

        habitItemsAdapter = HabitItemsAdapter(this)
        viewModel.habits.observe(viewLifecycleOwner, { habits ->
            habitItemsAdapter.setHabits(habits, comparatorByNumber)
        })

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
                    0,
                    ItemTouchHelper.START or ItemTouchHelper.END
                )
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                viewModel.remove(viewHolder.adapterPosition)
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

        view.add_habit.setOnClickListener {
            viewModel.onClickAddHabit()
            findNavController().navigate(R.id.action_listOfHabitsFragment_to_habitEditorFragment)
        }

        habitItemsAdapter.notifyDataSetChanged()

        val activity = requireActivity()
        (activity
            .getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager)
            .hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)

        return view
    }

    override fun onClick(position: Int) {
        viewModel.onClickHabit(position)
        findNavController().navigate(R.id.action_listOfHabitsFragment_to_habitEditorFragment)
    }
}