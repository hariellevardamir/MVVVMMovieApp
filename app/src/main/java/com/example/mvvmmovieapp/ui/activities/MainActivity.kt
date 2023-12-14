package com.example.mvvmmovieapp.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.mvvmmovieapp.R
import com.example.mvvmmovieapp.databinding.ActivityMainBinding
import com.example.mvvmmovieapp.db.MovieDatabase
import com.example.mvvmmovieapp.viewModel.HomeViewModel
import com.example.mvvmmovieapp.viewModel.HomeViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNavigationMenu: BottomNavigationView
    private lateinit var navController: NavController

    val viewModel: HomeViewModel by lazy {
        val movieDatabase = MovieDatabase.getInstance(this)
        val homeViewModelFactory = HomeViewModelFactory(movieDatabase)
        ViewModelProvider(this, homeViewModelFactory)[HomeViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setTheme(R.style.Theme_MVVMMovieApp)
        setContentView(binding.root)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        navController = Navigation.findNavController(this, R.id.hostFragment)
        bottomNavigationMenu = binding.bottomNavMenu
        NavigationUI.setupWithNavController(bottomNavigationMenu, navController)

    }
}