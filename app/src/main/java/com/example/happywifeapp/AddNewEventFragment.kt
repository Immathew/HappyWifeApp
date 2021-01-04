package com.example.happywifeapp

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.happywifeapp.databinding.FragmentAddNewEventBinding
import com.example.happywifeapp.databinding.FragmentUpcomingEventsBinding
import java.text.SimpleDateFormat
import java.util.*


class AddNewEventFragment : Fragment() {

    private lateinit var binding: FragmentAddNewEventBinding
    private var calendar = Calendar.getInstance()
    private lateinit var dateSetListener: DatePickerDialog.OnDateSetListener


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_add_new_event, container, false)

        setupToolbar(binding)

        dateSetListener = DatePickerDialog.OnDateSetListener {
            view, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateDateInView()
        }

        binding.editTextDate.setOnClickListener {
            DatePickerDialog(
                    requireActivity(),
                    dateSetListener,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        return binding.root

    }

    private fun setupToolbar(binding: FragmentAddNewEventBinding) {
        val navController = findNavController()
        binding.toolbarAddNewEvent.setupWithNavController(navController)
        binding.toolbarAddNewEvent.title = getString(R.string.add_new_event)
        binding.toolbarAddNewEvent.setNavigationIcon(R.drawable.back_arrow_icon)
    }

    private fun updateDateInView() {
        val myFormat = "dd.MM.yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
        binding.editTextDate.setText(sdf.format(calendar.time).toString())
    }

}