package com.example.recwell.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recwell.R
import com.example.recwell.api.RetrofitClient
import com.example.recwell.model.Occupancy
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CrowdsFragment : Fragment(R.layout.fragment_crowds) {

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        // Summary card views
        val overallPercentage =
            view.findViewById<TextView>(R.id.tvOverallPercentage)

        val busyStatus =
            view.findViewById<TextView>(R.id.tvBusyStatus)

        val overallFacilityName =
            view.findViewById<TextView>(R.id.tvOverallFacilityName)

        val overallCount =
            view.findViewById<TextView>(R.id.tvOverallCount)

        val overallProgress =
            view.findViewById<ProgressBar>(R.id.progressOverall)

        // Area occupancy RecyclerView
        val recyclerView =
            view.findViewById<RecyclerView>(R.id.rvFacilities)

        recyclerView.layoutManager =
            LinearLayoutManager(requireContext())

        // Get occupancy data from Mockfly
        RetrofitClient.apiService
            .getOccupancy()
            .enqueue(object : Callback<List<Occupancy>> {

                override fun onResponse(
                    call: Call<List<Occupancy>>,
                    response: Response<List<Occupancy>>
                ) {

                    if (response.isSuccessful) {

                        val occupancyList = response.body()

                        if (!occupancyList.isNullOrEmpty()) {

                            // First API item = overall facility
                            val overallFacility =
                                occupancyList.first()

                            val percentage =
                                if (overallFacility.capacity > 0) {

                                    kotlin.math.round(
                                        overallFacility.currentCount.toDouble() /
                                                overallFacility.capacity * 100
                                    ).toInt()

                                } else {
                                    0
                                }

                            // Update overall facility card
                            overallPercentage.text =
                                "$percentage%"

                            overallFacilityName.text =
                                overallFacility.facilityName

                            overallCount.text =
                                "${overallFacility.currentCount} / " +
                                        "${overallFacility.capacity} active"

                            overallProgress.progress =
                                percentage

                            // Overall crowd status
                            busyStatus.text =
                                when {
                                    percentage < 30 -> "NOT BUSY"
                                    percentage < 60 -> "MODERATE"
                                    percentage < 80 -> "BUSY"
                                    else -> "VERY BUSY"
                                }

                            // Everything after the first item
                            // is an individual gym area
                            val areaList =
                                occupancyList.drop(1)

                            recyclerView.adapter =
                                FacilityAdapter(areaList)
                        }

                    } else {

                        Toast.makeText(
                            requireContext(),
                            "Failed to load occupancy",
                            Toast.LENGTH_SHORT
                        ).show()

                        Log.e(
                            "CrowdsFragment",
                            "API error: ${response.code()}"
                        )
                    }
                }

                override fun onFailure(
                    call: Call<List<Occupancy>>,
                    t: Throwable
                ) {

                    Toast.makeText(
                        requireContext(),
                        "Network error",
                        Toast.LENGTH_SHORT
                    ).show()

                    Log.e(
                        "CrowdsFragment",
                        "Network error: ${t.message}",
                        t
                    )
                }
            })
    }
}