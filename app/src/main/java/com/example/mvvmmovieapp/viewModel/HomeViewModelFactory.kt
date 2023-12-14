package com.example.mvvmmovieapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmmovieapp.db.MovieDatabase

class HomeViewModelFactory(private val movieDatabase: MovieDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(movieDatabase) as T
    }
}