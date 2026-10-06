package com.example.recwell.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recwell.R
import com.example.recwell.model.ClassItem

class ClassAdapter(
    private val classList: List<ClassItem>
) : RecyclerView.Adapter<ClassAdapter.ClassViewHolder>() {

    class ClassViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

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
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ClassViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_class, parent, false)

        return ClassViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ClassViewHolder,
        position: Int
    ) {

        val classItem = classList[position]

        holder.className.text = classItem.name

        holder.instructor.text =
            "Instructor: ${classItem.instructor}"

        holder.facility.text =
            classItem.facilityName

        holder.date.text =
            classItem.date

        holder.time.text =
            "${classItem.startTime} - ${classItem.endTime}"
    }

    override fun getItemCount(): Int {
        return classList.size
    }
}