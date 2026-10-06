package com.example.recwell.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recwell.R
import com.example.recwell.repository.RecWellRepository

class AnnouncementsFragment : Fragment(R.layout.fragment_announcements) {

    private val repository = RecWellRepository()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Find RecyclerView from fragment_announcements.xml
        val recyclerView =
            view.findViewById<RecyclerView>(R.id.rvAnnouncements)

        // Get announcement data from repository
        val announcementList = repository.getAnnouncements()

        // Arrange announcement cards vertically
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext())

        // Connect announcement data to RecyclerView
        recyclerView.adapter =
            AnnouncementAdapter(announcementList)
    }
}