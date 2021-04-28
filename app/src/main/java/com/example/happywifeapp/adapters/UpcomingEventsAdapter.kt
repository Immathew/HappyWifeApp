package com.example.happywifeapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.happywifeapp.database.Event
import com.example.happywifeapp.databinding.ItemEventBinding
import com.example.happywifeapp.ui.fragments.UpcomingEventsFragmentDirections
import com.example.happywifeapp.utils.CalculateDiffUtil

class UpcomingEventsAdapter : RecyclerView.Adapter<UpcomingEventsAdapter.MyViewHolder>() {

    private var upcomingEvents = emptyList<Event>()

    override fun getItemCount(): Int {
        return upcomingEvents.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = upcomingEvents[position]

        holder.itemView.setOnClickListener {
            val action =
                UpcomingEventsFragmentDirections.actionUpcomingEventsFragmentToEventDetailsFragment(
                    item
                )
            holder.itemView.findNavController().navigate(action)
        }
        holder.bind(item)
    }

    class MyViewHolder(private val binding: ItemEventBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(event: Event) {
            binding.event = event
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemEventBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }

    fun setData(newEvents: List<Event>) {
        val eventsDiffUtil = CalculateDiffUtil(upcomingEvents, newEvents)
        val diffUtilResult = DiffUtil.calculateDiff(eventsDiffUtil)
        upcomingEvents = newEvents
        diffUtilResult.dispatchUpdatesTo(this)

    }

}