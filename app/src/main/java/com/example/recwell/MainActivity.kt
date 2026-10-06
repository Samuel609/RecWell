package com.example.recwell

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.recwell.ui.AnnouncementsFragment
import com.example.recwell.ui.ClassesFragment
import com.example.recwell.ui.CrowdsFragment
import com.example.recwell.ui.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val bottomNavigation =
            findViewById<BottomNavigationView>(R.id.bottomNavigation)

        // Show Crowds when the app first opens
        if (savedInstanceState == null) {
            loadFragment(CrowdsFragment())
        }

        // Change screens when a bottom button is pressed
        bottomNavigation.setOnItemSelectedListener { item ->

            when (item.itemId) {

                R.id.nav_crowds -> {
                    loadFragment(CrowdsFragment())
                    true
                }

                R.id.nav_classes -> {
                    loadFragment(ClassesFragment())
                    true
                }

                R.id.nav_profile -> {
                    loadFragment(ProfileFragment())
                    true
                }

                R.id.nav_updates -> {
                    loadFragment(AnnouncementsFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}