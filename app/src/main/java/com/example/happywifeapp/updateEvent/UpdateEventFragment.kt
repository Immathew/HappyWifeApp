package com.example.happywifeapp.updateEvent

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.happywifeapp.R
import com.example.happywifeapp.databinding.FragmentUpcomingEventsBinding
import com.example.happywifeapp.databinding.FragmentUpdateEventBinding

class UpdateEventFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        val binding: FragmentUpdateEventBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_update_event, container, false)





        return binding.root
    }

}