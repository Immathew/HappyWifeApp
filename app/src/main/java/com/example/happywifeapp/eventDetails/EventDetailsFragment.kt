package com.example.happywifeapp.eventDetails

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import com.example.happywifeapp.R
import com.example.happywifeapp.databinding.FragmentEventDetailsBinding

class EventDetailsFragment : Fragment() {

    private lateinit var binding: FragmentEventDetailsBinding
    private val args by navArgs<EventDetailsFragmentArgs>()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_event_details, container, false)


        val bitmapFactory = BitmapFactory.decodeFile(args.currentEvent.image)
        val scaleBitmap = Bitmap.createScaledBitmap(bitmapFactory, (bitmapFactory.width *0.9).toInt(), (bitmapFactory.height *0.9).toInt(), true)

        binding.imageEventDetails.setImageBitmap(scaleBitmap)
        binding.eventDetailsDescription.text = args.currentEvent.description
        binding.eventDetailsLocation.text = args.currentEvent.location

        setupToolbar()


        return binding.root
    }

    private fun setupToolbar() {
        val navController = findNavController()
        binding.toolbarEventDetails.setupWithNavController(navController)
        binding.toolbarEventDetails.title = args.currentEvent.title
        binding.toolbarEventDetails.setNavigationIcon(R.drawable.back_arrow_icon)
    }

}