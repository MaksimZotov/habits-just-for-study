package com.maksimzotov.habits

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class HabitEditorFragment : Fragment() {
    private val defaultRGB = "255, 57, 0"
    private val defaultHSV = "#FF3900"

    private val alphaBG = 97
    private val cornerRadiusBG = 20f

    private var position = -1


    private lateinit var name: EditText
    private lateinit var description: EditText
    private lateinit var priority: Spinner
    private lateinit var type: RadioGroup
    private lateinit var numberOfPerformedExercises: EditText
    private lateinit var frequency: EditText
    private lateinit var curBG: View
    private lateinit var curRGB: TextView
    private lateinit var curHSV: TextView
    private lateinit var saveHabit: Button
    private lateinit var bgColors: LinearLayout


    override fun onResume() {
        super.onResume()
        (requireActivity() as DrawerLockModeListener).lock()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_habit_editor, container, false)

        name = view.findViewById(R.id.name)
        description = view.findViewById(R.id.description)
        priority = view.findViewById(R.id.priority)
        type = view.findViewById(R.id.type)
        numberOfPerformedExercises = view.findViewById(R.id.number_of_performed_exercises)
        frequency = view.findViewById(R.id.frequency)
        curBG = view.findViewById(R.id.cur_bg)
        curRGB = view.findViewById(R.id.cur_rgb)
        curHSV = view.findViewById(R.id.cur_hsv)
        saveHabit = view.findViewById(R.id.save_habit)
        bgColors = view.findViewById(R.id.bg_colors)

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
        bgColors.background = colorsBG

        position = Logic.curPosition

        if (position != -1) {
            val habit = Logic.habits[position]
            name.setText(habit.name)
            description.setText(habit.description)
            priority.setSelection(habit.priorityPosition)
            type.check(habit.typeId)
            numberOfPerformedExercises.setText(habit.numberOfPerformedExercises)
            frequency.setText(habit.frequency)
            curBG.setBackgroundColor(habit.intColor)
            curRGB.text = "RGB: ${convertIntColorToRGB(habit.intColor)}"
            curHSV.text = "HSV: ${convertIntColorToHex(habit.intColor)}"
        } else {
            curRGB.text = "RGB: $defaultRGB"
            curHSV.text = "HSV: $defaultHSV"
        }

        bgColors.forEach { it.setOnClickListener {
            curBG.background = it.background
            val intColor = (curBG.background as ColorDrawable).color
            curRGB.text = "RGB: ${convertIntColorToRGB(intColor)}"
            curHSV.text = "HSV: ${convertIntColorToHex(intColor)}"
        } }

        saveHabit.setOnClickListener { saveHabit() }

        return view
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
            Logic.state = State.ITEM_INSERTED
            Logic.curPosition = Logic.habits.lastIndex
        } else {
            bindHabit(Logic.habits[position])
            findNavController().popBackStack()
            Logic.state = State.ITEM_CHANGED
        }
    }

    private fun bindHabit(habit: Habit): Habit {
        habit.name = name.text.toString()
        habit.description = description.text.toString()
        habit.priorityPosition = priority.selectedItemPosition
        habit.typeId = type.checkedRadioButtonId
        habit.numberOfPerformedExercises = numberOfPerformedExercises.text.toString()
        habit.frequency = frequency.text.toString()
        habit.intColor = (curBG.background as ColorDrawable).color
        return habit
    }
}