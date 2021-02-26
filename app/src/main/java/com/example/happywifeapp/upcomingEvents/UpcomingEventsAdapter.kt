package com.example.happywifeapp.upcomingEvents

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.happywifeapp.R

import com.example.happywifeapp.database.Event

class UpcomingEventsAdapter(): RecyclerView.Adapter<UpcomingEventsAdapter.ViewHolder>() {

    private var data = emptyList<Event>()

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(
                R.layout.item_event, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]

        holder.eventTitle.text = item.title
        holder.eventDescription.text = item.description
        holder.eventDate.text = item.date
        holder.eventImage.setImageURI(Uri.parse(item.image))

        holder.oneEventLayout.setOnClickListener {
                val action = UpcomingEventsFragmentDirections.actionUpcomingEventsFragmentToEventDetailsFragment(item)
                holder.oneEventLayout.findNavController().navigate(action)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val eventTitle: TextView = itemView.findViewById(R.id.item_view_title)
        val eventDescription: TextView = itemView.findViewById(R.id.item_view_description)
        val eventDate: TextView = itemView.findViewById(R.id.item_view_date)
        val eventImage: ImageView = itemView.findViewById(R.id.item_view_place_image)
        val oneEventLayout: View = itemView.findViewById(R.id.one_item_layout)
    }

    fun setData(event: List<Event>) {
        this.data = event
        notifyDataSetChanged()
    }

}