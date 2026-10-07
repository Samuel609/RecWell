package com.example.recwell.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recwell.R
import com.example.recwell.api.RetrofitClient
import com.example.recwell.model.ClassItem
import com.google.android.material.button.MaterialButtonToggleGroup
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClassesFragment :
    Fragment(R.layout.fragment_classes) {

    private lateinit var recyclerView:
            RecyclerView

    private lateinit var adapter:
            ClassAdapter

    private lateinit var searchBox:
            EditText

    private lateinit var resultsText:
            TextView

    private var allClasses:
            List<ClassItem> =
        emptyList()

    private var selectedCategory =
        "All"

    private var selectedSkill =
        "All"

    private var searchQuery =
        ""

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {

        super.onViewCreated(
            view,
            savedInstanceState
        )

        recyclerView =
            view.findViewById(
                R.id.rvClasses
            )

        searchBox =
            view.findViewById(
                R.id.etClassSearch
            )

        resultsText =
            view.findViewById(
                R.id.tvClassResults
            )

        val categoryToggle =
            view.findViewById<
                    MaterialButtonToggleGroup
                    >(
                R.id.categoryToggleGroup
            )

        val skillToggle =
            view.findViewById<
                    MaterialButtonToggleGroup
                    >(
                R.id.skillToggleGroup
            )

        // RecyclerView
        recyclerView.layoutManager =
            LinearLayoutManager(
                requireContext()
            )

        adapter =
            ClassAdapter(
                emptyList()
            )

        recyclerView.adapter =
            adapter

        // =========================================
        // SEARCH
        // =========================================

        searchBox.addTextChangedListener(
            object : TextWatcher {

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {

                    searchQuery =
                        s?.toString()
                            ?.trim()
                            ?: ""

                    applyFilters()
                }

                override fun afterTextChanged(
                    s: Editable?
                ) {
                }
            }
        )

        // =========================================
        // CATEGORY FILTER
        // =========================================

        categoryToggle
            .addOnButtonCheckedListener {
                    _,
                    checkedId,
                    isChecked ->

                if (!isChecked) {
                    return@addOnButtonCheckedListener
                }

                selectedCategory =
                    when (checkedId) {

                        R.id.btnCategoryCardio ->
                            "Cardio"

                        R.id.btnCategoryStrength ->
                            "Strength"

                        R.id.btnCategoryYoga ->
                            "Yoga & Mind"

                        else ->
                            "All"
                    }

                applyFilters()
            }

        // =========================================
        // SKILL FILTER
        // =========================================

        skillToggle
            .addOnButtonCheckedListener {
                    _,
                    checkedId,
                    isChecked ->

                if (!isChecked) {
                    return@addOnButtonCheckedListener
                }

                selectedSkill =
                    when (checkedId) {

                        R.id.btnSkillAllLevels ->
                            "All Levels"

                        R.id.btnSkillIntermediate ->
                            "Intermediate"

                        R.id.btnSkillAdvanced ->
                            "Advanced"

                        else ->
                            "All"
                    }

                applyFilters()
            }

        loadClasses()
    }

    // =============================================
    // LOAD CLASSES FROM MOCKFLY
    // =============================================

    private fun loadClasses() {

        resultsText.text =
            "Loading classes..."

        RetrofitClient
            .apiService
            .getClasses()
            .enqueue(
                object :
                    Callback<List<ClassItem>> {

                    override fun onResponse(
                        call:
                        Call<List<ClassItem>>,
                        response:
                        Response<List<ClassItem>>
                    ) {

                        if (
                            response.isSuccessful
                        ) {

                            allClasses =
                                response.body()
                                    ?: emptyList()

                            applyFilters()

                            Log.d(
                                "ClassesFragment",
                                "Loaded ${allClasses.size} classes"
                            )

                        } else {

                            resultsText.text =
                                "Unable to load classes"

                            Toast.makeText(
                                requireContext(),
                                "Failed to load classes",
                                Toast.LENGTH_SHORT
                            ).show()

                            Log.e(
                                "ClassesFragment",
                                "Classes API error: ${response.code()}"
                            )
                        }
                    }

                    override fun onFailure(
                        call:
                        Call<List<ClassItem>>,
                        t: Throwable
                    ) {

                        resultsText.text =
                            "Unable to load classes"

                        Toast.makeText(
                            requireContext(),
                            "Classes network error",
                            Toast.LENGTH_SHORT
                        ).show()

                        Log.e(
                            "ClassesFragment",
                            "Classes network error",
                            t
                        )
                    }
                }
            )
    }

    // =============================================
    // FILTER CLASSES
    // =============================================

    private fun applyFilters() {

        val filteredClasses =
            allClasses.filter { classItem ->

                val matchesCategory =
                    selectedCategory == "All" ||
                            classItem.category ==
                            selectedCategory

                val matchesSkill =
                    selectedSkill == "All" ||
                            classItem.skillLevel ==
                            selectedSkill

                val matchesSearch =
                    searchQuery.isBlank() ||
                            classItem.name.contains(
                                searchQuery,
                                ignoreCase = true
                            ) ||
                            classItem.instructor.contains(
                                searchQuery,
                                ignoreCase = true
                            ) ||
                            classItem.facility.contains(
                                searchQuery,
                                ignoreCase = true
                            )

                matchesCategory &&
                        matchesSkill &&
                        matchesSearch
            }

        adapter.updateClasses(
            filteredClasses
        )

        resultsText.text =
            when (
                filteredClasses.size
            ) {

                0 ->
                    "No classes found"

                1 ->
                    "1 class available"

                else ->
                    "${filteredClasses.size} classes available"
            }
    }
}