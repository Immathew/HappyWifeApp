package com.example.happywifeapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.happywifeapp.R
import com.example.happywifeapp.databinding.FragmentTitleBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TitleFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentTitleBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_title, container, false
        )

        binding.llAddNewEvent.setOnClickListener {
            findNavController().navigate(TitleFragmentDirections.actionTitleFragmentToAddNewEventFragment())
        }

        binding.llAllEvents.setOnClickListener {
            findNavController().navigate(TitleFragmentDirections.actionTitleFragmentToAddNewEventFragment())
        }

        binding.llUpcomingEvents.setOnClickListener {
            findNavController().navigate(TitleFragmentDirections.actionTitleFragmentToAddNewEventFragment())
        }

        return binding.root
    }

}