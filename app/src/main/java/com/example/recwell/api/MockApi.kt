package com.example.recwell.api

import com.example.recwell.model.Announcement
import com.example.recwell.model.ClassItem
import com.example.recwell.model.Facility
import com.example.recwell.model.Occupancy
import com.example.recwell.model.User

class MockApi {

    fun getFacilities(): List<Facility> {
        return listOf(
            Facility(
                id = "urw",
                name = "University Recreation Center",
                location = "East Bank",
                description = "Recreation and fitness facility",
                capacity = 800
            )
        )
    }

    fun getOccupancy(): List<Occupancy> {
        return listOf(
            Occupancy(
                facilityId = "urw",
                facilityName = "University Recreation Center",
                location = "east_bank",
                currentCount = 1247,
                capacity = 2500,
                lastUpdated = "4:30 PM"
            ),

            Occupancy(
                facilityId = "iron-haven",
                facilityName = "Lower Level Iron Haven",
                location = "east_bank",
                currentCount = 90,
                capacity = 180,
                lastUpdated = "4:30 PM"
            ),

            Occupancy(
                facilityId = "fitness-lounge",
                facilityName = "2nd Floor Fitness Lounge & Cardio",
                location = "east_bank",
                currentCount = 108,
                capacity = 250,
                lastUpdated = "4:30 PM"
            )
        )
    }

    fun getClasses(): List<ClassItem> {
        return listOf(
            ClassItem(
                id = "class-001",
                name = "Sunrise Vinyasa Flow",
                instructor = "Hannah L.",
                facility = "East Bank Studio",
                location = "east_bank",
                date = "Today",
                time = "6:30 AM",
                duration = 50,
                category = "Yoga & Mind",
                skillLevel = "All Levels",
                spotsAvailable = 47,
                capacity = 50
            ),

            ClassItem(
                id = "class-002",
                name = "Power Cycle",
                instructor = "Marcus T.",
                facility = "East Bank Cycle Studio",
                location = "east_bank",
                date = "Today",
                time = "8:00 AM",
                duration = 45,
                category = "Cardio",
                skillLevel = "Intermediate",
                spotsAvailable = 12,
                capacity = 30
            ),

            ClassItem(
                id = "class-003",
                name = "Total Body Strength",
                instructor = "Jordan M.",
                facility = "East Bank Fitness Studio",
                location = "east_bank",
                date = "Today",
                time = "12:00 PM",
                duration = 50,
                category = "Strength",
                skillLevel = "All Levels",
                spotsAvailable = 8,
                capacity = 24
            )
        )
    }

    fun getUser(): User {
        return User(
            id = "user-001",
            name = "Samuel Fitta",
            email = "fitta@umn.edu"
        )
    }

    fun getAnnouncements(): List<Announcement> {
        return listOf(
            Announcement(
                id = "announcement-001",
                title = "Welcome to RecWell",
                message = "Check the app for facility updates and announcements.",
                date = "Today"
            ),

            Announcement(
                id = "announcement-002",
                title = "Facility Update",
                message = "Remember to check facility hours before visiting.",
                date = "This Week"
            )
        )
    }
}