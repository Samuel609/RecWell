package com.example.recwell.repository

import com.example.recwell.api.MockApi
import com.example.recwell.model.Announcement
import com.example.recwell.model.ClassItem
import com.example.recwell.model.Facility
import com.example.recwell.model.Occupancy
import com.example.recwell.model.User

class RecWellRepository {

    private val api = MockApi()

    fun getFacilities(): List<Facility>{
        return api.getFacilities()
    }

    fun getOccupancy(): List<Occupancy>{
        return api.getOccupancy()
    }

    fun getClasses(): List<ClassItem>{
        return api.getClasses()
    }

    fun getUser(): User{
        return api.getUser()
    }

    fun getAnnouncements(): List<Announcement>{
        return api.getAnnouncements()
    }
}