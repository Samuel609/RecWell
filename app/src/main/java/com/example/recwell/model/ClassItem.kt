package com.example.recwell.model

data class ClassItem(
    val id: String,
    val name: String,
    val instructor: String,
    val facility: String,
    val location: String,
    val date: String,
    val time: String,
    val duration: Int,
    val category: String,
    val skillLevel: String,
    val spotsAvailable: Int,
    val capacity: Int
)