package com.example.happywifeapp.allEvents


import android.net.Uri
import android.text.Layout
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.happywifeapp.R
import com.example.happywifeapp.database.Event
import com.example.happywifeapp.updateEvent.UpdateEventFragmentDirections
import com.example.happywifeapp.utils.SwipeToEditCallback
import kotlinx.coroutines.withContext


class EventsAdapter(): RecyclerView.Adapter<EventsAdapter.ViewHolder>() {

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
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val eventTitle: TextView = itemView.findViewById(R.id.item_view_title)
        val eventDescription: TextView = itemView.findViewById(R.id.item_view_description)
        val eventDate: TextView = itemView.findViewById(R.id.item_view_date)
        val eventImage: ImageView = itemView.findViewById(R.id.item_view_place_image)
    }

    fun setData(event: List<Event>) {
        this.data = event
        notifyDataSetChanged()
    }

    fun editAt(position: Int): NavDirections {
        val item = data[position]
        val action = AllEventsListFragmentDirections
            .actionAllEventsListFragmentToUpdateEventFragment(item)
        notifyItemChanged(position)
        return action
    }


}