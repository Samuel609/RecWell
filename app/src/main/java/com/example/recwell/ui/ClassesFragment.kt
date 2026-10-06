package com.example.recwell.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recwell.R
import com.example.recwell.repository.RecWellRepository

class ClassesFragment : Fragment(R.layout.fragment_classes) {

    private val repository = RecWellRepository()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Find RecyclerView from fragment_classes.xml
        val recyclerView =
            view.findViewById<RecyclerView>(R.id.rvClasses)

        // Get class data from repository
        val classList = repository.getClasses()

        // Arrange the class cards vertically
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext())

        // Connect class data to the RecyclerView
        recyclerView.adapter =
            ClassAdapter(classList)
    }
}