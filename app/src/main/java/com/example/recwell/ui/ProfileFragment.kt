package com.example.recwell.ui

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.recwell.R
import com.example.recwell.repository.RecWellRepository
import com.google.android.material.button.MaterialButtonToggleGroup

class ProfileFragment :
    Fragment(R.layout.fragment_profile) {

    private val repository =
        RecWellRepository()

    private var selectedLocation =
        "University Recreation Center • East Bank"

    private var monthlySessions =
        12

    private val recentVisits =
        mutableListOf<String>()

    private lateinit var activityContainer:
            LinearLayout

    private lateinit var activityEmpty:
            TextView

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {

        super.onViewCreated(
            view,
            savedInstanceState
        )

        val userName =
            view.findViewById<TextView>(
                R.id.tvUserName
            )

        val userEmail =
            view.findViewById<TextView>(
                R.id.tvUserEmail
            )

        val memberId =
            view.findViewById<TextView>(
                R.id.tvMemberId
            )

        val locationText =
            view.findViewById<TextView>(
                R.id.tvTurnstileLocation
            )

        val scanStatus =
            view.findViewById<TextView>(
                R.id.tvScanStatus
            )

        val sessionsText =
            view.findViewById<TextView>(
                R.id.tvMonthlySessions
            )

        val gopherCard =
            view.findViewById<View>(
                R.id.gopherCard
            )

        activityContainer =
            view.findViewById(
                R.id.recentActivityContainer
            )

        activityEmpty =
            view.findViewById(
                R.id.tvActivityEmpty
            )

        val turnstileToggleGroup =
            view.findViewById<
                    MaterialButtonToggleGroup
                    >(
                R.id.turnstileToggleGroup
            )

        // =========================================
        // LOAD USER
        // =========================================

        val user =
            repository.getUser()

        userName.text =
            user.name

        userEmail.text =
            user.email

        memberId.text =
            "GOPHER ID: ${user.id.uppercase()}"

        sessionsText.text =
            monthlySessions.toString()

        // =========================================
        // LOCATION SELECTION
        // =========================================

        turnstileToggleGroup
            .addOnButtonCheckedListener {
                    _,
                    checkedId,
                    isChecked ->

                if (!isChecked) {
                    return@addOnButtonCheckedListener
                }

                selectedLocation =
                    when (checkedId) {

                        R.id.btnTurnstileStPaul ->
                            "St. Paul Gymnasium"

                        R.id.btnTurnstileCooke ->
                            "Cooke Hall"

                        else ->
                            "University Recreation Center • East Bank"
                    }

                locationText.text =
                    selectedLocation

                scanStatus.text =
                    "Ready to scan"

                scanStatus.setTextColor(
                    Color.parseColor("#777777")
                )
            }

        // =========================================
        // GOPHER CARD SCAN
        // =========================================

        gopherCard.setOnClickListener {

            monthlySessions++

            sessionsText.text =
                monthlySessions.toString()

            scanStatus.text =
                "✓ ACCESS GRANTED • $selectedLocation"

            scanStatus.setTextColor(
                Color.parseColor("#008A55")
            )

            addRecentVisit(
                selectedLocation
            )

            Toast.makeText(
                requireContext(),
                "Access granted at $selectedLocation",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    // =============================================
    // ADD RECENT VISIT
    // =============================================

    private fun addRecentVisit(
        location: String
    ) {

        recentVisits.add(
            0,
            location
        )

        // Keep only the 3 most recent visits
        if (recentVisits.size > 3) {
            recentVisits.removeAt(
                recentVisits.lastIndex
            )
        }

        updateRecentActivity()
    }

    // =============================================
    // UPDATE ACTIVITY UI
    // =============================================

    private fun updateRecentActivity() {

        activityContainer.removeAllViews()

        if (recentVisits.isEmpty()) {

            activityEmpty.visibility =
                View.VISIBLE

            return
        }

        activityEmpty.visibility =
            View.GONE

        recentVisits.forEachIndexed {
                index,
                location ->

            val activityView =
                TextView(requireContext())

            activityView.text =
                when (index) {

                    0 ->
                        "✓  $location\n     Just now"

                    1 ->
                        "✓  $location\n     Previous visit"

                    else ->
                        "✓  $location\n     Earlier visit"
                }

            activityView.setTextColor(
                Color.parseColor("#333333")
            )

            activityView.textSize =
                12f

            activityView.setPadding(
                0,
                12,
                0,
                12
            )

            activityContainer.addView(
                activityView
            )
        }
    }
}