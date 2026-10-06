package com.example.recwell.ui

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.recwell.R
import com.example.recwell.repository.RecWellRepository

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val repository = RecWellRepository()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Find the TextViews from fragment_profile.xml
        val userName =
            view.findViewById<TextView>(R.id.tvUserName)

        val userEmail =
            view.findViewById<TextView>(R.id.tvUserEmail)

        // Get the user from our repository
        val user = repository.getUser()

        // Put the user's information on the screen
        userName.text = user.name
        userEmail.text = user.email
    }
}