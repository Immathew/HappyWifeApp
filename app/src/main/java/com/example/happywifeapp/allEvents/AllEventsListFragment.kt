package com.example.happywifeapp.allEvents

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.happywifeapp.R
import com.example.happywifeapp.database.Event
import com.example.happywifeapp.database.EventDatabase
import com.example.happywifeapp.database.EventDatabaseDAO
import com.example.happywifeapp.databinding.FragmentAllEventsListBinding
import com.example.happywifeapp.utils.SwipeToDeleteCallback
import com.example.happywifeapp.utils.SwipeToEditCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AllEventsListFragment : Fragment() {

    private lateinit var dataSource: EventDatabaseDAO

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentAllEventsListBinding = DataBindingUtil.inflate(
        inflater, R.layout.fragment_all_events_list, container, false)

        val application = requireNotNull(this.activity).application

        dataSource = EventDatabase.getInstance(application).eventDatabaseDAO()

        val viewModelFactory = AllEventsListViewModelFactory(dataSource, application)

        val uiScope = CoroutineScope(Dispatchers.IO)

        val allEventsListViewModel =
                ViewModelProvider(this, viewModelFactory).get(AllEventsListViewModel::class.java)

        setupToolbar(binding)

        val adapter = EventsAdapter()
        binding.recyclerViewEventList.adapter = adapter

        //Edit objects
        val editSwipeHandler = object : SwipeToEditCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = binding.recyclerViewEventList.adapter as EventsAdapter

                val navigateToEdit = adapter.editAt(viewHolder.adapterPosition)
                findNavController().navigate(navigateToEdit)
            }
        }
        val editItemTouchHelper = ItemTouchHelper(editSwipeHandler)
        editItemTouchHelper.attachToRecyclerView(binding.recyclerViewEventList)

        //Delete objects
        val deleteSwipeHandler = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = binding.recyclerViewEventList.adapter as EventsAdapter
                val selectedEventPosition = adapter.removeAt(viewHolder.adapterPosition)

                uiScope.launch {
                    val eventToDelete = dataSource.getEvent(selectedEventPosition)
                    delete(eventToDelete)
                }
                Toast.makeText(requireContext(), "Event deleted", Toast.LENGTH_SHORT).show()
            }
        }
        val deleteItemTouchHelper = ItemTouchHelper(deleteSwipeHandler)
        deleteItemTouchHelper.attachToRecyclerView(binding.recyclerViewEventList)


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

    private suspend fun delete(event: Event) {
        withContext(Dispatchers.IO) {
            dataSource.deleteEvent(event)
        }
    }
}