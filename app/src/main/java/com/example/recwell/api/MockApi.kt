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
                location = "Minneapolis",
                description = "Main RecWell recreation facility",
                capacity = 2500
            ),
            Facility(
                id = "cook",
                name = "Cook County Recreation Center",
                location = "Minneapolis",
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
            currentCount = 1247,
            capacity = 2500,
            lastUpdated = "10:32 PM"
            )
        )
    }
    fun getClasses(): List<ClassItem> {
        return listOf(
            ClassItem(
                id = "class1",
                name = "Yoga",
                instructor = "Jane Smith",
                facilityName = "University Recreation Center",
                date = "October 10, 2026",
                startTime = "5:00 PM",
                endTime = "6:00 PM"
            ),
            ClassItem(
                id = "class2",
                name = "Group Fitness",
                instructor = "John Smith",
                facilityName = "University Recreation Center",
                date = "October 11, 2026",
                startTime = "6:00 PM",
                endTime = "7:00 PM"
            )
        )
    }

    fun getUser(): User {
        return User(
            id = "user1",
            name = "Samuel",
            email = "student@umn.edu"
        )
    }

    fun getAnnouncements(): List<Announcement> {
        return listOf(
            Announcement(
                id = "announcement1",
                title = "Welcome to RecWell",
                message = "Check out the latest RecWell facilities and programs.",
                date = "October 6, 2026"
            ),
            Announcement(
                id = "announcement2",
                title = "Facility Update",
                message = "Check the facility schedule before visiting.",
                date = "October 6, 2026"
            )
        )
    }

}