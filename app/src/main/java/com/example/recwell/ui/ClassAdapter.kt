package com.example.recwell.ui

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.recwell.R
import com.example.recwell.model.ClassItem
import com.google.android.material.button.MaterialButton

class ClassAdapter(
    private var classList: List<ClassItem>
) : RecyclerView.Adapter<ClassAdapter.ClassViewHolder>() {

    private val bookedClassIds =
        mutableSetOf<String>()

    class ClassViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        val className: TextView =
            itemView.findViewById(R.id.tvClassName)

        val instructor: TextView =
            itemView.findViewById(R.id.tvInstructor)

        val facility: TextView =
            itemView.findViewById(R.id.tvClassFacility)

        val date: TextView =
            itemView.findViewById(R.id.tvClassDate)

        val time: TextView =
            itemView.findViewById(R.id.tvClassTime)

        val duration: TextView =
            itemView.findViewById(R.id.tvClassDuration)

        val category: TextView =
            itemView.findViewById(R.id.tvClassCategory)

        val skillLevel: TextView =
            itemView.findViewById(R.id.tvSkillLevel)

        val spotsAvailable: TextView =
            itemView.findViewById(R.id.tvSpotsAvailable)

        val bookButton: MaterialButton =
            itemView.findViewById(R.id.btnBookSpot)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ClassViewHolder {

        val view =
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.item_class,
                    parent,
                    false
                )

        return ClassViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ClassViewHolder,
        position: Int
    ) {

        val classItem =
            classList[position]

        val isBooked =
            bookedClassIds.contains(classItem.id)

        // Basic class information
        holder.className.text =
            classItem.name

        holder.instructor.text =
            "led by ${classItem.instructor}"

        holder.facility.text =
            classItem.facility

        holder.date.text =
            classItem.date

        holder.time.text =
            classItem.time

        holder.duration.text =
            "${classItem.duration} min"

        holder.category.text =
            classItem.category.uppercase()

        holder.skillLevel.text =
            classItem.skillLevel

        // Category color
        val categoryColor =
            when (classItem.category) {

                "Yoga & Mind" ->
                    Color.parseColor("#7B1FA2")

                "Cardio" ->
                    Color.parseColor("#C62828")

                "Strength" ->
                    Color.parseColor("#8B0015")

                else ->
                    Color.parseColor("#555555")
            }

        holder.category.setTextColor(
            categoryColor
        )

        // If booked, visually subtract one spot
        val displayedSpots =
            if (isBooked) {
                (classItem.spotsAvailable - 1)
                    .coerceAtLeast(0)
            } else {
                classItem.spotsAvailable
            }

        updateSpotsDisplay(
            holder,
            displayedSpots
        )

        updateBookingButton(
            holder,
            classItem,
            isBooked
        )

        // Book / cancel reservation
        holder.bookButton.setOnClickListener {

            val currentlyBooked =
                bookedClassIds.contains(
                    classItem.id
                )

            if (currentlyBooked) {

                bookedClassIds.remove(
                    classItem.id
                )

                Toast.makeText(
                    holder.itemView.context,
                    "Reservation cancelled for ${classItem.name}",
                    Toast.LENGTH_SHORT
                ).show()

            } else {

                if (classItem.spotsAvailable <= 0) {

                    Toast.makeText(
                        holder.itemView.context,
                        "This class is full",
                        Toast.LENGTH_SHORT
                    ).show()

                    return@setOnClickListener
                }

                bookedClassIds.add(
                    classItem.id
                )

                Toast.makeText(
                    holder.itemView.context,
                    "You're booked for ${classItem.name}",
                    Toast.LENGTH_SHORT
                ).show()
            }

            val currentPosition =
                holder.bindingAdapterPosition

            if (
                currentPosition !=
                RecyclerView.NO_POSITION
            ) {
                notifyItemChanged(
                    currentPosition
                )
            }
        }
    }

    // THIS IS THE METHOD YOUR ERROR SAYS WAS MISSING
    override fun getItemCount(): Int {
        return classList.size
    }

    private fun updateSpotsDisplay(
        holder: ClassViewHolder,
        spots: Int
    ) {

        if (spots <= 0) {

            holder.spotsAvailable.text =
                "Class Full"

            holder.spotsAvailable.setTextColor(
                Color.parseColor("#C62828")
            )

            return
        }

        holder.spotsAvailable.text =
            "$spots spots left"

        val spotsColor =
            when {

                spots <= 5 ->
                    Color.parseColor("#C62828")

                spots <= 10 ->
                    Color.parseColor("#E65100")

                else ->
                    Color.parseColor("#008A55")
            }

        holder.spotsAvailable.setTextColor(
            spotsColor
        )
    }

    private fun updateBookingButton(
        holder: ClassViewHolder,
        classItem: ClassItem,
        isBooked: Boolean
    ) {

        if (isBooked) {

            holder.bookButton.text =
                "Booked ✓"

            holder.bookButton.isEnabled =
                true

            holder.bookButton.backgroundTintList =
                ColorStateList.valueOf(
                    Color.parseColor("#2E7D32")
                )

            return
        }

        if (classItem.spotsAvailable <= 0) {

            holder.bookButton.text =
                "Full"

            holder.bookButton.isEnabled =
                false

            holder.bookButton.backgroundTintList =
                ColorStateList.valueOf(
                    Color.parseColor("#999999")
                )

            return
        }

        holder.bookButton.text =
            "Book Spot"

        holder.bookButton.isEnabled =
            true

        holder.bookButton.backgroundTintList =
            ColorStateList.valueOf(
                Color.parseColor("#8B0015")
            )
    }

    fun updateClasses(
        newClasses: List<ClassItem>
    ) {

        classList =
            newClasses

        notifyDataSetChanged()
    }
}