package com.recepguzel.cryptoxcleanarchitecture

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.recepguzel.cryptoxcleanarchitecture.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

private lateinit var binding: ActivityMainBinding
private lateinit var navController: NavController

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.bottomNav, navController)
        navHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->

            when (destination.id) {
                R.id.homeFragment,
                R.id.newsFragment,
                R.id.dictionaryFragment,

                    //  R.id.coinListFragment,
                R.id.profileFragment -> {
                    binding.bottomNav.visibility = View.VISIBLE

                }

                else -> {
                    //  R.id.coinDetailFragment
                    binding.bottomNav.visibility = View.GONE

                }
            }
        }

    }
}