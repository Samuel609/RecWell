package com.example.recwell.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recwell.R
import com.example.recwell.repository.RecWellRepository

class CrowdsFragment : Fragment(R.layout.fragment_crowds) {

    private val repository = RecWellRepository()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Find the RecyclerView from fragment_crowds.xml
        val recyclerView =
            view.findViewById<RecyclerView>(R.id.rvFacilities)

        // Get our mock occupancy data from the repository
        val occupancyList = repository.getOccupancy()

        // Tell RecyclerView how to arrange the cards
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Give the data to our adapter
        recyclerView.adapter = FacilityAdapter(occupancyList)
    }
}