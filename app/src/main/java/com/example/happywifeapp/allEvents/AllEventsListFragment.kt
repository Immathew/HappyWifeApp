package com.example.happywifeapp.allEvents

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
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.happywifeapp.R
import com.example.happywifeapp.database.EventDatabase
import com.example.happywifeapp.databinding.FragmentAllEventsListBinding
import com.example.happywifeapp.utils.SwipeToEditCallback

class AllEventsListFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentAllEventsListBinding = DataBindingUtil.inflate(
        inflater, R.layout.fragment_all_events_list, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = EventDatabase.getInstance(application).eventDatabaseDAO()

        val viewModelFactory = AllEventsListViewModelFactory(dataSource, application)

        val allEventsListViewModel =
                ViewModelProvider(this, viewModelFactory).get(AllEventsListViewModel::class.java)

        setupToolbar(binding)

        val adapter = EventsAdapter()
        binding.recyclerViewEventList.adapter = adapter

        val swipeHandler = object : SwipeToEditCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = binding.recyclerViewEventList.adapter as EventsAdapter

                val navigateToEdit = adapter.editAt(viewHolder.adapterPosition)
                findNavController().navigate(navigateToEdit)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.recyclerViewEventList)


        allEventsListViewModel.readAllData.observe(viewLifecycleOwner, Observer { event ->
            adapter.setData(event)
        })

        binding.allEventsListViewModel = allEventsListViewModel
        binding.lifecycleOwner = this


        return binding.root
    }

    private fun setupToolbar(binding: FragmentAllEventsListBinding) {
        val navController = findNavController()
        binding.toolbarAllEventsList.setupWithNavController(navController)
        binding.toolbarAllEventsList.title = getString(R.string.all_events_list)
        binding.toolbarAllEventsList.setNavigationIcon(R.drawable.back_arrow_icon)
    }
}