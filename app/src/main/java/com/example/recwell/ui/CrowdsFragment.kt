package com.example.recwell.ui

import android.content.res.ColorStateList
import android.graphics.Color
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
import com.example.recwell.model.Forecast
import com.example.recwell.model.Occupancy
import com.google.android.material.button.MaterialButtonToggleGroup
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.round

class CrowdsFragment : Fragment(R.layout.fragment_crowds) {

    private var allOccupancyData: List<Occupancy> =
        emptyList()

    private var allForecastData: List<Forecast> =
        emptyList()

    private var selectedLocation =
        "east_bank"

    private lateinit var overallPercentage: TextView
    private lateinit var busyStatus: TextView
    private lateinit var overallFacilityName: TextView
    private lateinit var overallCount: TextView
    private lateinit var overallProgress: ProgressBar
    private lateinit var recyclerView: RecyclerView

    private lateinit var forecastPercentViews:
            List<TextView>

    private lateinit var forecastHourViews:
            List<TextView>

    private lateinit var forecastBars:
            List<View>

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {

        super.onViewCreated(
            view,
            savedInstanceState
        )

        // -------------------------
        // Overall occupancy views
        // -------------------------

        overallPercentage =
            view.findViewById(
                R.id.tvOverallPercentage
            )

        busyStatus =
            view.findViewById(
                R.id.tvBusyStatus
            )

        overallFacilityName =
            view.findViewById(
                R.id.tvOverallFacilityName
            )

        overallCount =
            view.findViewById(
                R.id.tvOverallCount
            )

        overallProgress =
            view.findViewById(
                R.id.progressOverall
            )

        recyclerView =
            view.findViewById(
                R.id.rvFacilities
            )

        recyclerView.layoutManager =
            LinearLayoutManager(
                requireContext()
            )

        // -------------------------
        // Location selector
        // -------------------------

        val locationToggleGroup =
            view.findViewById<
                    MaterialButtonToggleGroup
                    >(
                R.id.locationToggleGroup
            )

        // -------------------------
        // Forecast percentages
        // -------------------------

        forecastPercentViews =
            listOf(
                view.findViewById(
                    R.id.tvForecastPercent1
                ),
                view.findViewById(
                    R.id.tvForecastPercent2
                ),
                view.findViewById(
                    R.id.tvForecastPercent3
                ),
                view.findViewById(
                    R.id.tvForecastPercent4
                ),
                view.findViewById(
                    R.id.tvForecastPercent5
                ),
                view.findViewById(
                    R.id.tvForecastPercent6
                )
            )

        // -------------------------
        // Forecast hours
        // -------------------------

        forecastHourViews =
            listOf(
                view.findViewById(
                    R.id.tvForecastHour1
                ),
                view.findViewById(
                    R.id.tvForecastHour2
                ),
                view.findViewById(
                    R.id.tvForecastHour3
                ),
                view.findViewById(
                    R.id.tvForecastHour4
                ),
                view.findViewById(
                    R.id.tvForecastHour5
                ),
                view.findViewById(
                    R.id.tvForecastHour6
                )
            )

        // -------------------------
        // Forecast bars
        // -------------------------

        forecastBars =
            listOf(
                view.findViewById(
                    R.id.barForecast1
                ),
                view.findViewById(
                    R.id.barForecast2
                ),
                view.findViewById(
                    R.id.barForecast3
                ),
                view.findViewById(
                    R.id.barForecast4
                ),
                view.findViewById(
                    R.id.barForecast5
                ),
                view.findViewById(
                    R.id.barForecast6
                )
            )

        // -------------------------
        // Location switching
        // -------------------------

        locationToggleGroup
            .addOnButtonCheckedListener {
                    _,
                    checkedId,
                    isChecked ->

                if (!isChecked) {
                    return@addOnButtonCheckedListener
                }

                selectedLocation =
                    when (checkedId) {

                        R.id.btnEastBank ->
                            "east_bank"

                        R.id.btnStPaul ->
                            "st_paul"

                        R.id.btnCookeHall ->
                            "cooke_hall"

                        else ->
                            "east_bank"
                    }

                showLocation(
                    selectedLocation
                )

                showForecast(
                    selectedLocation
                )
            }

        loadOccupancy()
        loadForecast()
    }

    // =====================================================
    // LOAD OCCUPANCY
    // =====================================================

    private fun loadOccupancy() {

        RetrofitClient
            .apiService
            .getOccupancy()
            .enqueue(
                object :
                    Callback<List<Occupancy>> {

                    override fun onResponse(
                        call:
                        Call<List<Occupancy>>,
                        response:
                        Response<List<Occupancy>>
                    ) {

                        if (
                            response.isSuccessful
                        ) {

                            allOccupancyData =
                                response.body()
                                    ?: emptyList()

                            showLocation(
                                selectedLocation
                            )

                        } else {

                            Toast.makeText(
                                requireContext(),
                                "Failed to load occupancy",
                                Toast.LENGTH_SHORT
                            ).show()

                            Log.e(
                                "CrowdsFragment",
                                "Occupancy API error: ${response.code()}"
                            )
                        }
                    }

                    override fun onFailure(
                        call:
                        Call<List<Occupancy>>,
                        t: Throwable
                    ) {

                        Toast.makeText(
                            requireContext(),
                            "Occupancy network error",
                            Toast.LENGTH_SHORT
                        ).show()

                        Log.e(
                            "CrowdsFragment",
                            "Occupancy network error",
                            t
                        )
                    }
                }
            )
    }

    // =====================================================
    // LOAD FORECAST
    // =====================================================

    private fun loadForecast() {

        RetrofitClient
            .apiService
            .getForecast()
            .enqueue(
                object :
                    Callback<List<Forecast>> {

                    override fun onResponse(
                        call:
                        Call<List<Forecast>>,
                        response:
                        Response<List<Forecast>>
                    ) {

                        if (
                            response.isSuccessful
                        ) {

                            allForecastData =
                                response.body()
                                    ?: emptyList()

                            showForecast(
                                selectedLocation
                            )

                        } else {

                            Toast.makeText(
                                requireContext(),
                                "Failed to load forecast",
                                Toast.LENGTH_SHORT
                            ).show()

                            Log.e(
                                "CrowdsFragment",
                                "Forecast API error: ${response.code()}"
                            )
                        }
                    }

                    override fun onFailure(
                        call:
                        Call<List<Forecast>>,
                        t: Throwable
                    ) {

                        Toast.makeText(
                            requireContext(),
                            "Forecast network error",
                            Toast.LENGTH_SHORT
                        ).show()

                        Log.e(
                            "CrowdsFragment",
                            "Forecast network error",
                            t
                        )
                    }
                }
            )
    }

    // =====================================================
    // SHOW OCCUPANCY
    // =====================================================

    private fun showLocation(
        location: String
    ) {

        val locationData =
            allOccupancyData.filter {
                it.location == location
            }

        if (locationData.isEmpty()) {
            return
        }

        val facility =
            locationData.first()

        val percentage =
            if (
                facility.capacity > 0
            ) {

                round(
                    facility
                        .currentCount
                        .toDouble() /
                            facility.capacity *
                            100
                ).toInt()

            } else {
                0
            }

        val status =
            getCrowdStatus(
                percentage
            )

        val crowdColor =
            getCrowdColor(
                percentage
            )

        overallPercentage.text =
            "$percentage%"

        overallPercentage.setTextColor(
            crowdColor
        )

        overallFacilityName.text =
            facility.facilityName

        overallCount.text =
            "${facility.currentCount} / ${facility.capacity} active"

        busyStatus.text =
            status

        busyStatus.setTextColor(
            crowdColor
        )

        overallProgress.progress =
            percentage

        overallProgress
            .progressTintList =
            ColorStateList.valueOf(
                crowdColor
            )

        val areaList =
            locationData.drop(1)

        recyclerView.adapter =
            FacilityAdapter(
                areaList
            )
    }

    // =====================================================
    // SHOW FORECAST
    // =====================================================

    private fun showForecast(
        location: String
    ) {

        val locationForecast =
            allForecastData.filter {
                it.location == location
            }

        if (
            locationForecast.isEmpty()
        ) {
            return
        }

        val density =
            resources
                .displayMetrics
                .density

        val maxBarHeightDp =
            120

        locationForecast
            .take(6)
            .forEachIndexed {
                    index,
                    forecast ->

                forecastPercentViews[
                    index
                ].text =
                    "${forecast.percentage}%"

                forecastHourViews[
                    index
                ].text =
                    forecast.hour

                val crowdColor =
                    getCrowdColor(
                        forecast.percentage
                    )

                forecastPercentViews[
                    index
                ].setTextColor(
                    crowdColor
                )

                forecastBars[
                    index
                ].setBackgroundColor(
                    crowdColor
                )

                val barHeightDp =
                    (
                            maxBarHeightDp *
                                    forecast.percentage /
                                    100f
                            ).toInt()

                val barHeightPx =
                    (
                            barHeightDp *
                                    density
                            ).toInt()

                forecastBars[
                    index
                ].layoutParams.height =
                    barHeightPx

                forecastBars[
                    index
                ].requestLayout()
            }
    }

    // =====================================================
    // CROWD STATUS
    // =====================================================

    private fun getCrowdStatus(
        percentage: Int
    ): String {

        return when {

            percentage < 30 ->
                "NOT BUSY"

            percentage < 60 ->
                "MODERATE"

            percentage < 80 ->
                "BUSY"

            else ->
                "VERY BUSY"
        }
    }

    // =====================================================
    // CROWD COLOR
    // =====================================================

    private fun getCrowdColor(
        percentage: Int
    ): Int {

        return when {

            percentage < 30 ->
                Color.parseColor(
                    "#2E7D32"
                )

            percentage < 60 ->
                Color.parseColor(
                    "#C69214"
                )

            percentage < 80 ->
                Color.parseColor(
                    "#E65100"
                )

            else ->
                Color.parseColor(
                    "#8B0015"
                )
        }
    }
}