package com.example.recwell.ui

import android.content.res.ColorStateList
import android.graphics.Color
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

        val view =
            LayoutInflater.from(parent.context)
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

        val occupancy =
            occupancyList[position]

        val percentage =
            if (occupancy.capacity > 0) {

                round(
                    occupancy.currentCount.toDouble() /
                            occupancy.capacity * 100
                ).toInt()

            } else {
                0
            }

        // Determine status
        val status =
            when {
                percentage < 30 -> "NOT BUSY"
                percentage < 60 -> "MODERATE"
                percentage < 80 -> "BUSY"
                else -> "VERY BUSY"
            }

        // Determine color
        val crowdColor =
            when {
                percentage < 30 ->
                    Color.parseColor("#2E7D32")

                percentage < 60 ->
                    Color.parseColor("#C69214")

                percentage < 80 ->
                    Color.parseColor("#E65100")

                else ->
                    Color.parseColor("#8B0015")
            }

        // Name
        holder.facilityName.text =
            occupancy.facilityName

        // Percentage
        holder.percentage.text =
            "$percentage%"

        holder.percentage.setTextColor(
            crowdColor
        )

        // Current occupancy
        holder.facilityCount.text =
            "${occupancy.currentCount} / ${occupancy.capacity} active"

        // Status
        holder.areaStatus.text =
            status

        holder.areaStatus.setTextColor(
            crowdColor
        )

        // Progress bar
        holder.progressBar.progress =
            percentage

        holder.progressBar.progressTintList =
            ColorStateList.valueOf(
                crowdColor
            )
    }

    override fun getItemCount(): Int {
        return occupancyList.size
    }
}