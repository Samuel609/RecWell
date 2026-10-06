package com.example.recwell.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recwell.R
import com.example.recwell.model.Occupancy

class FacilityAdapter(
    private val occupancyList: List<Occupancy>
) : RecyclerView.Adapter<FacilityAdapter.FacilityViewHolder>() {

    class FacilityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val facilityName: TextView =
            itemView.findViewById(R.id.tvFacilityName)

        val percentage: TextView =
            itemView.findViewById(R.id.tvPercentage)

        val facilityCount: TextView =
            itemView.findViewById(R.id.tvFacilityCount)

        val progressBar: ProgressBar =
            itemView.findViewById(R.id.progressOccupancy)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FacilityViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_facility, parent, false)

        return FacilityViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: FacilityViewHolder,
        position: Int
    ) {

        val occupancy = occupancyList[position]

        val percentage = if (occupancy.capacity > 0) {
            (occupancy.currentCount * 100) / occupancy.capacity
        } else {
            0
        }

        holder.facilityName.text = occupancy.facilityName

        holder.percentage.text = "$percentage%"

        holder.facilityCount.text =
            "${occupancy.currentCount} / ${occupancy.capacity} active"

        holder.progressBar.progress = percentage
    }

    override fun getItemCount(): Int {
        return occupancyList.size
    }
}