package com.example.recwell.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recwell.R
import com.example.recwell.model.Announcement

class AnnouncementAdapter(
    private val announcementList: List<Announcement>
) : RecyclerView.Adapter<AnnouncementAdapter.AnnouncementViewHolder>() {

    class AnnouncementViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val title: TextView =
            itemView.findViewById(R.id.tvAnnouncementTitle)

        val date: TextView =
            itemView.findViewById(R.id.tvAnnouncementDate)

        val message: TextView =
            itemView.findViewById(R.id.tvAnnouncementMessage)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AnnouncementViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_announcement, parent, false)

        return AnnouncementViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: AnnouncementViewHolder,
        position: Int
    ) {

        val announcement = announcementList[position]

        holder.title.text = announcement.title
        holder.date.text = announcement.date
        holder.message.text = announcement.message
    }

    override fun getItemCount(): Int {
        return announcementList.size
    }
}