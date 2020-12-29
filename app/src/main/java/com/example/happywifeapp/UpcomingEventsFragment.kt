package com.example.happywifeapp

import android.os.Bundle
import android.renderscript.ScriptGroup
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.happywifeapp.databinding.FragmentUpcomingEventsBinding

class UpcomingEventsFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding: FragmentUpcomingEventsBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_upcoming_events, container, false)


        return binding.root
    }

}