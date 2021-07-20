package com.maksimzotov.habits.view.fragments

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.maksimzotov.habits.*
import com.maksimzotov.habits.view.adapters.ViewPagerListsOfHabitsAdapter
import com.maksimzotov.habits.view.listeners.DrawerLockModeListener
import com.maksimzotov.habits.view.listeners.OnDataSetChangedListener
import com.maksimzotov.habits.viewmodel.ViewPagerListsOfHabitsViewModel
import kotlinx.android.synthetic.main.fragment_view_pager.view.*

class ViewPagerListOfHabitsFragment : Fragment(), OnDataSetChangedListener {

    private val viewModel by viewModels<ViewPagerListsOfHabitsViewModel>()

    lateinit var importantHabitsFragment: ListOfHabitsFragment
    lateinit var unimportantHabitsFragment: ListOfHabitsFragment

    override fun onResume() {
        super.onResume()
        val activity = requireActivity()
        (activity
            .getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager)
            .hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
        (activity as DrawerLockModeListener).unlock()

        update()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_view_pager, container, false)

        view.add_habit.setOnClickListener {
            viewModel.onClickAddHabit()
            findNavController().navigate(R.id.habitEditorFragment)
        }

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!this::importantHabitsFragment.isInitialized) {
            importantHabitsFragment = ListOfHabitsFragment(viewModel.habitsImportant, this)
            unimportantHabitsFragment = ListOfHabitsFragment(viewModel.habitsUnimportant, this)
        }

        view.orders_pager_view_pager.adapter = ViewPagerListsOfHabitsAdapter(
            listOf(
                importantHabitsFragment,
                unimportantHabitsFragment
            ),
            requireActivity().supportFragmentManager,
            lifecycle
        )

        TabLayoutMediator(
            view.orders_pager_tab_layout,
            view.orders_pager_view_pager
        ) { tab, position ->
            tab.text = if (position == 0) {
                "Важные привычки"
            } else {
                "Неважные привычки"
            }
        }.attach()
    }

    override fun update() {
        importantHabitsFragment.update()
        unimportantHabitsFragment.update()
    }
}