package com.recepguzel.cryptoxcleanarchitecture

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.firebase.auth.FirebaseAuth
import com.recepguzel.cryptoxcleanarchitecture.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        sharedPreferences = getSharedPreferences("app_preferences", MODE_PRIVATE)

        val isFirstRun = sharedPreferences.getBoolean("is_first_run", true)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        if (firebaseAuth.currentUser != null && !isFirstRun) {
            // Kullanıcı giriş yapmış ve bu ilk çalışma değilse ana ekrana yönlendir
            navController.navigate(R.id.homeFragment)
        } else {
            // İlk çalışma ya da kullanıcı giriş yapmamışsa giriş ekranını göster
            navController.navigate(R.id.signInFragment)
        }

        // İlk çalışmada bayrağı false yap
        if (isFirstRun) {
            sharedPreferences.edit().putBoolean("is_first_run", false).apply()
        }

        NavigationUI.setupWithNavController(binding.bottomNav, navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment,
                R.id.newsFragment,
                R.id.dictionaryFragment,
                R.id.profileFragment -> {
                    binding.bottomNav.visibility = View.VISIBLE
                }

                else -> {
                    binding.bottomNav.visibility = View.GONE
                }
            }
        }
    }
}
