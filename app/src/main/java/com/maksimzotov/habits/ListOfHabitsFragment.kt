package com.maksimzotov.habits

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ListOfHabitsFragment() : Fragment(), HabitsAdapter.OnClickListener {
    private var habits: MutableList<Habit> = Logic.habits

    private var prevBG: Drawable? = null
    private lateinit var habitsAdapter: HabitsAdapter

    constructor(habits: MutableList<Habit>) : this() {
        this.habits = habits
    }

    override fun onResume() {
        super.onResume()
        val activity = requireActivity()
        (activity
            .getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager)
            .hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
        (activity as DrawerLockModeListener).unlock()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list_of_habits, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        val addHabit = view.findViewById<FloatingActionButton>(R.id.add_habit)

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

        addHabit.setOnClickListener {
            Logic.curPosition = -1
            findNavController().navigate(R.id.habitEditorFragment)
        }

        if (Logic.state == State.ITEM_INSERTED) {
            habitsAdapter.notifyItemInserted(Logic.curPosition)
        } else if (Logic.state == State.ITEM_CHANGED) {
            habitsAdapter.notifyItemChanged(Logic.curPosition)
        }

        Logic.state = State.NOTHING
        Logic.curPosition = -1

        return view
    }

    /*
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity()
        val ordersPagerTabLayout = activity.findViewById<TabLayout>(R.id.orders_pager_tab_layout)
        val ordersPagerViewPager = activity.findViewById<ViewPager2>(R.id.orders_pager_view_pager)
        ordersPagerViewPager.adapter = ViewPagerAdapter(activity as AppCompatActivity)
        TabLayoutMediator(ordersPagerTabLayout, ordersPagerViewPager) { tab, position ->
            tab.text = if (position == 0) {
                "Важные"
            } else {
                "Неважные"
            }
        }
    }
     */

    override fun onClick(position: Int) {
        Logic.curPosition = position
        findNavController().navigate(R.id.habitEditorFragment)
    }
}