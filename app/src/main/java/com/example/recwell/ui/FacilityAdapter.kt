package com.example.recwell.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recwell.R
import com.example.recwell.model.Occupancy
import kotlin.math.round

class FacilityAdapter(
    private val occupancyList: List<Occupancy>
) : RecyclerView.Adapter<FacilityAdapter.FacilityViewHolder>() {

    class FacilityViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val facilityName: TextView =
            itemView.findViewById(R.id.tvFacilityName)

        val percentage: TextView =
            itemView.findViewById(R.id.tvPercentage)

        val facilityCount: TextView =
            itemView.findViewById(R.id.tvFacilityCount)

        val areaStatus: TextView =
            itemView.findViewById(R.id.tvAreaStatus)

        val progressBar: ProgressBar =
            itemView.findViewById(R.id.progressOccupancy)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FacilityViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.item_facility,
                parent,
                false
            )

        return FacilityViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: FacilityViewHolder,
        position: Int
    ) {

        val occupancy = occupancyList[position]

        // Calculate occupancy percentage and round normally
        val percentage = if (occupancy.capacity > 0) {
            round(
                occupancy.currentCount.toDouble() /
                        occupancy.capacity * 100
            ).toInt()
        } else {
            0
        }

        // Area name
        holder.facilityName.text =
            occupancy.facilityName

        // Percentage
        holder.percentage.text =
            "$percentage%"

        // Current occupancy
        holder.facilityCount.text =
            "${occupancy.currentCount} / ${occupancy.capacity} active"

        // Progress bar
        holder.progressBar.progress =
            percentage

        // Crowd status
        holder.areaStatus.text =
            when {
                percentage < 30 -> "NOT BUSY"
                percentage < 60 -> "MODERATE"
                percentage < 80 -> "BUSY"
                else -> "VERY BUSY"
            }
    }

    override fun getItemCount(): Int {
        return occupancyList.size
    }
}