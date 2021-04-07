package com.example.happywifeapp.allEvents

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.happywifeapp.database.Event
import com.example.happywifeapp.databinding.ItemEventBinding


class EventsAdapter : RecyclerView.Adapter<EventsAdapter.MyViewHolder>() {

    private var data = emptyList<Event>()

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = data[position]



        holder.itemView.setOnClickListener {
            val action = AllEventsListFragmentDirections.actionAllEventsListFragmentToEventDetailsFragment(item)
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
        this.data = event
        notifyDataSetChanged()
    }

    fun editAt(position: Int): NavDirections {
        val item = data[position]
        return AllEventsListFragmentDirections
            .actionAllEventsListFragmentToUpdateEventFragment(item)
    }

    fun removeAt(position: Int): Int {
        val item = data[position]
        return item.eventId
    }

}