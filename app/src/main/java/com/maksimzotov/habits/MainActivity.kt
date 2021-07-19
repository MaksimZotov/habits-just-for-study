package com.maksimzotov.habits

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity() : AppCompatActivity(), DrawerLockModeListener {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = findNavController(R.id.nav_host_fragment)
        navigation_view.setupWithNavController(navController)

        navigation_view.setNavigationItemSelectedListener { menuItem ->
            if (menuItem.itemId == R.id.menu_item_about) {
                navController.navigate(R.id.action_listOfHabitsFragment_to_aboutAppFragment)
                navigation_drawer_layout.closeDrawers()
            } else if (menuItem.itemId == R.id.menu_item_home) {
                if (navController.currentDestination?.id == R.id.aboutAppFragment) {
                    navController.navigate(R.id.action_aboutAppFragment_to_listOfHabitsFragment)
                }
                navigation_drawer_layout.closeDrawers()
            }
            return@setNavigationItemSelectedListener true
        }

        appBarConfiguration = AppBarConfiguration(navController.graph, navigation_drawer_layout)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun lock() {
        navigation_drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }

    override fun unlock() {
        navigation_drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
    }
}