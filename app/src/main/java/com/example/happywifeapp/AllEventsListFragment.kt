package com.example.happywifeapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.happywifeapp.databinding.FragmentAllEventsListBinding
import com.example.happywifeapp.databinding.FragmentUpcomingEventsBinding

class AllEventsListFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentAllEventsListBinding = DataBindingUtil.inflate(
        inflater, R.layout.fragment_all_events_list, container, false)


        return binding.root
    }

}