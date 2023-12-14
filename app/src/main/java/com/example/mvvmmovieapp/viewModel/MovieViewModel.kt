package com.example.mvvmmovieapp.viewModel

import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmmovieapp.db.MovieDatabase
import com.example.mvvmmovieapp.models.MovieDetails
import com.example.mvvmmovieapp.retrofit.RetrofitInstance
import com.example.mvvmmovieapp.util.Constants
import kotlinx.coroutines.launch
import retrofit2.Response

class MovieViewModel(private val movieDatabase: MovieDatabase) : ViewModel() {

    private var movieDetailsLiveData = MutableLiveData<MovieDetails>()
    private var TAG = "MovieViewModel"

    fun getMovieDetails(movie_id: String) = viewModelScope.launch {
        Log.d(TAG, "MovieViewModel")
        try {
            Log.d(TAG, "MovieViewModel1")
            val response: Response<MovieDetails> =
                RetrofitInstance.api.getMovieDetails(movie_id, Constants.API_KEY)
            Log.d(TAG, "MovieViewModel2")
            if (response.isSuccessful) {
                Log.d(TAG, "MovieViewModel3")
                val popularMovies = response.body()!!
                movieDetailsLiveData.postValue(popularMovies)
            } else {
                Log.d(TAG, "An Error Occurred")
                if (response.errorBody() != null) {
                    Log.d(TAG, "Error Body: ${response.errorBody()!!.string()}")
                }
            }
        } catch (e: Exception) {
            Log.d(TAG, "Exception: $e")
        }
    }

    fun observeMovieDetails(): LiveData<MovieDetails> {
        return movieDetailsLiveData
    }

    fun upsertMovie(movie: MovieDetails) {
        viewModelScope.launch {
            movieDatabase.movieDao().upsert(movie)
        }
    }

}