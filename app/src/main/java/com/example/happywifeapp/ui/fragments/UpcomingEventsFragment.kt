package com.example.happywifeapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.happywifeapp.R
import com.example.happywifeapp.databinding.FragmentUpcomingEventsBinding
import com.example.happywifeapp.ui.viewModels.UpcomingEventsViewModel
import com.example.happywifeapp.adapters.UpcomingEventsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UpcomingEventsFragment : Fragment() {

    private var _binding: FragmentUpcomingEventsBinding? = null
    private val binding get() = _binding!!

    private lateinit var upcomingEventsViewModel: UpcomingEventsViewModel
    private val adapter by lazy { UpcomingEventsAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        upcomingEventsViewModel =
            ViewModelProvider(requireActivity()).get(UpcomingEventsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpcomingEventsBinding.inflate(
            inflater, container, false
        )

        setupToolbar()

        binding.recyclerViewUpcomingEventsList.adapter = adapter
        readDatabase()

        binding.fabNextMonthButton.setOnClickListener {
            upcomingEventsViewModel.setupCorrectFab()
            setDataFromDatabase()
        }

        binding.upcomingEventsViewModel = upcomingEventsViewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    private fun readDatabase() {
        lifecycleScope.launch {
            upcomingEventsViewModel.getEventsInThisMonth.observe(viewLifecycleOwner, { database ->
                if (database.isNotEmpty()) {
                    adapter.setData(database)
                    upcomingEventsViewModel.toggleFabButton = false
                }
            })
        }
    }

    private fun setDataFromDatabase() {
        lifecycleScope.launch {
            if (!upcomingEventsViewModel.toggleFabButton) {
                binding.textInFab.text = getString(R.string.see_next_month)
                upcomingEventsViewModel.getEventsInThisMonth.observe(viewLifecycleOwner, { database ->
                    adapter.setData(database)
                })
            } else if (upcomingEventsViewModel.toggleFabButton) {
                binding.textInFab.text = getString(R.string.see_previous_month)
                upcomingEventsViewModel.getEventInNextMonth.observe(viewLifecycleOwner, { event ->
                    adapter.setData(event)
                })
            }
        }
    }

    private fun setupToolbar() {
        val navController = findNavController()
        binding.toolbarUpcomingEventsList.setupWithNavController(navController)
        binding.toolbarUpcomingEventsList.title = getString(R.string.upcoming_events)
        binding.toolbarUpcomingEventsList.setNavigationIcon(R.drawable.back_arrow_icon)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}