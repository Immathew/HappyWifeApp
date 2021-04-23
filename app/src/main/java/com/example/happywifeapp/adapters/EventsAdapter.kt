package com.example.happywifeapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.happywifeapp.database.Event
import com.example.happywifeapp.databinding.ItemEventBinding
import com.example.happywifeapp.ui.fragments.AllEventsListFragmentDirections


class EventsAdapter : RecyclerView.Adapter<EventsAdapter.MyViewHolder>() {

    private var allEventsList = emptyList<Event>()

    override fun getItemCount(): Int {
        return allEventsList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = allEventsList[position]

        holder.itemView.setOnClickListener {
            val action =
                AllEventsListFragmentDirections.actionAllEventsListFragmentToEventDetailsFragment(
                    item
                )
            Navigation.findNavController(holder.itemView).navigate(action)
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

    fun setData(event: List<Event>) {
        this.allEventsList = event
        notifyDataSetChanged()
    }

    fun editAt(position: Int): NavDirections {
        val item = allEventsList[position]
        return AllEventsListFragmentDirections.actionAllEventsListFragmentToUpdateEventFragment(item)
    }

    fun removeAt(position: Int): Int {
        val item = allEventsList[position]
        return item.eventId
    }

}