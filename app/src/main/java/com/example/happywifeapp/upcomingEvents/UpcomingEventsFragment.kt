package com.example.happywifeapp.upcomingEvents

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController

import com.example.happywifeapp.R
import com.example.happywifeapp.allEvents.AllEventsListViewModel
import com.example.happywifeapp.allEvents.AllEventsListViewModelFactory
import com.example.happywifeapp.allEvents.EventsAdapter
import com.example.happywifeapp.database.EventDatabase
import com.example.happywifeapp.database.EventDatabaseDAO
import com.example.happywifeapp.databinding.FragmentAllEventsListBinding
import com.example.happywifeapp.databinding.FragmentUpcomingEventsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers


class UpcomingEventsFragment : Fragment() {

    private lateinit var binding: FragmentUpcomingEventsBinding
    private lateinit var dataSource: EventDatabaseDAO
    var toggleFabButton = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
            binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_upcoming_events, container, false)

        val application = requireNotNull(this.activity).application

        dataSource = EventDatabase.getInstance(application).eventDatabaseDAO()

        val viewModelFactory = UpcomingEventsViewModelFactory(dataSource, application)

        val upcomingEventsViewModel =
                ViewModelProvider(this, viewModelFactory).get(UpcomingEventsViewModel::class.java)

        setupToolbar()

        val adapter = EventsAdapter()
        binding.recyclerViewUpcomingEventsList.adapter = adapter

        upcomingEventsViewModel.getEventsInThisMonth.observe(viewLifecycleOwner, Observer { event ->
            adapter.setData(event)
        })

        binding.fabNextMonthButton.setOnClickListener {
            setupCorrectFab()

            if (!toggleFabButton) {
                upcomingEventsViewModel.getEventsInThisMonth.observe(viewLifecycleOwner, Observer { event ->
                    adapter.setData(event)
                })
            } else if (toggleFabButton){
                upcomingEventsViewModel.getEventInNextMonth.observe(viewLifecycleOwner, Observer { event ->
                    adapter.setData(event)
                })
            }
        }

        binding.upcomingEventsViewModel = upcomingEventsViewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    private fun setupCorrectFab() {
        if (!toggleFabButton) {
            toggleFabButton = true
            binding.textInFab.text = getString(R.string.see_previous_month)
        } else if (toggleFabButton){
            toggleFabButton = false
            binding.textInFab.text = getString(R.string.see_next_month)
        }
    }

    private fun setupToolbar() {
        val navController = findNavController()
        binding.toolbarUpcomingEventsList.setupWithNavController(navController)
        binding.toolbarUpcomingEventsList.title = getString(R.string.upcoming_events)
        binding.toolbarUpcomingEventsList.setNavigationIcon(R.drawable.back_arrow_icon)
    }
}