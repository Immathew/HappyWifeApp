package com.example.happywifeapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.happywifeapp.R
import com.example.happywifeapp.adapters.EventsAdapter
import com.example.happywifeapp.databinding.FragmentAllEventsListBinding
import com.example.happywifeapp.ui.viewModels.AllEventsListViewModel
import com.example.happywifeapp.utils.SwipeToDeleteCallback
import com.example.happywifeapp.utils.SwipeToEditCallback
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AllEventsListFragment : Fragment() {

    private lateinit var allEventsListViewModel: AllEventsListViewModel

    private val mAdapter by lazy { EventsAdapter() }
    private var _binding: FragmentAllEventsListBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        allEventsListViewModel =
            ViewModelProvider(requireActivity()).get(AllEventsListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllEventsListBinding.inflate(
            inflater, container, false
        )

        val uiScope = CoroutineScope(Dispatchers.IO)

        setupToolbar(binding)
        binding.allEventsListViewModel = allEventsListViewModel
        binding.lifecycleOwner = this

        binding.recyclerViewEventList.adapter = mAdapter

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
                    val eventToDelete = allEventsListViewModel.getEvent(selectedEventPosition)
                    allEventsListViewModel.deleteEvent(eventToDelete)
                }
                Toast.makeText(requireContext(), "Event deleted", Toast.LENGTH_SHORT).show()
            }
        }
        val deleteItemTouchHelper = ItemTouchHelper(deleteSwipeHandler)
        deleteItemTouchHelper.attachToRecyclerView(binding.recyclerViewEventList)

        lifecycleScope.launch {
            allEventsListViewModel.readAllData.observe(viewLifecycleOwner, Observer { event ->
                mAdapter.setData(event)

            })
        }

        return binding.root
    }

    private fun setupToolbar(binding: FragmentAllEventsListBinding) {
        val navController = findNavController()
        binding.toolbarAllEventsList.setupWithNavController(navController)
        binding.toolbarAllEventsList.title = getString(R.string.all_events_list)
        binding.toolbarAllEventsList.setNavigationIcon(R.drawable.back_arrow_icon)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}