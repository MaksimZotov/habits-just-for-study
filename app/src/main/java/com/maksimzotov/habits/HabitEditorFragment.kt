package com.maksimzotov.habits

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_habit_editor.view.*
import java.lang.Exception

class HabitEditorFragment : Fragment() {
    private val defaultRGB = "255, 57, 0"
    private val defaultHSV = "#FF3900"

    private val alphaBG = 97
    private val cornerRadiusBG = 20f

    private var position = -1

    override fun onResume() {
        super.onResume()
        (requireActivity() as DrawerLockModeListener).lock()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val curView = inflater.inflate(R.layout.fragment_habit_editor, container, false)

        val colorsBG = GradientDrawable(
            GradientDrawable.Orientation.BL_TR,
            intArrayOf(
                Color.RED,
                Color.YELLOW,
                Color.GREEN,
                Color.CYAN,
                Color.BLUE,
                Color.MAGENTA,
                Color.RED
            )
        )
        colorsBG.alpha = alphaBG
        colorsBG.shape = GradientDrawable.RECTANGLE
        colorsBG.gradientType = GradientDrawable.LINEAR_GRADIENT
        colorsBG.cornerRadius = cornerRadiusBG
        curView.bg_colors.background = colorsBG

        position = Logic.curPosition

        if (position != -1) {
            val habit = Logic.habits[position]
            curView.name.setText(habit.name)
            curView.description.setText(habit.description)
            curView.priority.setSelection(habit.priorityPosition)
            curView.type.check(habit.typeId)
            curView.number_of_performed_exercises.setText(habit.numberOfPerformedExercises)
            curView.frequency.setText(habit.frequency)
            curView.cur_bg.setBackgroundColor(habit.intColor)
            curView.cur_rgb.text = "RGB: ${convertIntColorToRGB(habit.intColor)}"
            curView.cur_hsv.text = "HSV: ${convertIntColorToHex(habit.intColor)}"
        } else {
            curView.cur_rgb.text = "RGB: $defaultRGB"
            curView.cur_hsv.text = "HSV: $defaultHSV"
        }

        curView.bg_colors.forEach { it.setOnClickListener {
            curView.cur_bg.background = it.background
            val intColor = (curView.cur_bg.background as ColorDrawable).color
            curView.cur_rgb.text = "RGB: ${convertIntColorToRGB(intColor)}"
            curView.cur_hsv.text = "HSV: ${convertIntColorToHex(intColor)}"
        } }

        curView.save_habit.setOnClickListener { saveHabit() }

        return curView
    }

    private fun convertIntColorToRGB(intColor: Int): String {
        val red = Color.red(intColor)
        val green = Color.green(intColor)
        val blue = Color.blue(intColor)
        return "$red, $green, $blue"
    }

    private fun convertIntColorToHex(intColor: Int): String {
        return String.format("#%06X", 0xFFFFFF and intColor)
    }

    private fun saveHabit() {
        if (position == -1) {
            Logic.habits.add(bindHabit(Habit()))
            findNavController().popBackStack()
            Logic.itemState = ItemState.ITEM_INSERTED
            Logic.curPosition = Logic.habits.lastIndex
        } else {
            bindHabit(Logic.habits[position])
            findNavController().popBackStack()
            Logic.itemState = ItemState.ITEM_CHANGED
        }
    }

    private fun bindHabit(habit: Habit): Habit {
        val curView = view ?: throw Exception("The view must be initialized")
        habit.name = curView.name.text.toString()
        habit.description = curView.description.text.toString()
        habit.priorityPosition = curView.priority.selectedItemPosition
        habit.typeId = curView.type.checkedRadioButtonId
        habit.numberOfPerformedExercises = curView.number_of_performed_exercises.text.toString()
        habit.frequency = curView.frequency.text.toString()
        habit.intColor = (curView.cur_bg.background as ColorDrawable).color
        return habit
    }
}