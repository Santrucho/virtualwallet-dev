package com.santrucho.virtualwalletdev

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.santrucho.virtualwalletdev.databinding.ActivityMainBinding
import com.santrucho.virtualwalletdev.ui.HomeFragment
import com.santrucho.virtualwalletdev.ui.MoreOptionFragment
import com.santrucho.virtualwalletdev.ui.MovementFragment
import com.santrucho.virtualwalletdev.ui.NotificationFragment

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var bottomNavigationView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        navController = NavHostController(this)

        binding.bottomNavigationView.setOnItemSelectedListener {
            handleBottomNavigation(it.itemId)
        }
        setContentView(binding.root)
    }

    private fun handleBottomNavigation(
        menuItemId: Int
    ): Boolean = when (menuItemId) {
        R.id.menu_home -> {
            supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment_container, HomeFragment())
                .commit()
            true
        }
        R.id.menu_activity -> {
            swapFragments(MovementFragment())
            true
        }
        R.id.menu_notifications -> {
            swapFragments(NotificationFragment())
            true
        }
        R.id.menu_more -> {
            swapFragments(MoreOptionFragment())
            true
        }
        else -> false
    }

    private fun swapFragments(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment_container, fragment)
            .commit()
    }

}