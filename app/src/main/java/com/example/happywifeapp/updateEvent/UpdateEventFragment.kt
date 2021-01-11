package com.example.happywifeapp.updateEvent

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import com.example.happywifeapp.R
import com.example.happywifeapp.database.EventDatabaseDAO
import com.example.happywifeapp.databinding.FragmentAddNewEventBinding
import com.example.happywifeapp.databinding.FragmentAllEventsListBinding
import com.example.happywifeapp.databinding.FragmentUpcomingEventsBinding
import com.example.happywifeapp.databinding.FragmentUpdateEventBinding
import java.util.*

class UpdateEventFragment : Fragment() {

    private val args by navArgs<UpdateEventFragmentArgs>()
    private lateinit var _binding: FragmentAddNewEventBinding
    private lateinit var database: EventDatabaseDAO
    private var calendar = Calendar.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentUpdateEventBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_update_event, container, false)

        setupToolbar(binding)

        binding.editTextTitleUpdateEvent.setText(args.currentEvent.title)
        binding.editTextDateUpdateEvent.setText(args.currentEvent.date)
        binding.editTextDescriptionUpdateEvent.setText(args.currentEvent.description)
        binding.editTextLocationUpdateEvent.setText(args.currentEvent.location)
        binding.imageViewPlaceImageUpdateEvent.setImageURI(Uri.parse(args.currentEvent.image))


        return binding.root
    }

    private fun setupToolbar(binding: FragmentUpdateEventBinding) {
        val navController = findNavController()
        binding.toolbarUpdateEvent.setupWithNavController(navController)
        binding.toolbarUpdateEvent.title = getString(R.string.update_event)
        binding.toolbarUpdateEvent.setNavigationIcon(R.drawable.back_arrow_icon)
    }

    private fun updateEvent(binding: FragmentUpdateEventBinding){

//        when {
//            binding.editTextTitleUpdateEvent.text.isNullOrEmpty() -> {
//                Toast.makeText(requireContext(), "Please enter title", Toast.LENGTH_SHORT).show()
//            }
//            binding.editTextDescriptionUpdateEvent.text.isNullOrEmpty() -> {
//                Toast.makeText(requireContext(), "Please enter a description", Toast.LENGTH_SHORT)
//                    .show()
//            }
//            binding.editTextLocationUpdateEvent.text.isNullOrEmpty() -> {
//                Toast.makeText(requireContext(), "Please enter a location", Toast.LENGTH_SHORT)
//                    .show()
    }

}