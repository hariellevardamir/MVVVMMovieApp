package com.example.mvvmmovieapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmmovieapp.db.MovieDatabase
import com.example.mvvmmovieapp.models.MovieDetails
import com.example.mvvmmovieapp.models.PopularMovie
import com.example.mvvmmovieapp.models.PopularMovieDetails
import com.example.mvvmmovieapp.models.SearchMovieDetails
import com.example.mvvmmovieapp.models.TopRatedMovie
import com.example.mvvmmovieapp.models.TopRatedMovieDetails
import com.example.mvvmmovieapp.models.UpComingMovie
import com.example.mvvmmovieapp.models.UpComingMovieDetails
import com.example.mvvmmovieapp.retrofit.RetrofitInstance
import com.example.mvvmmovieapp.util.Constants
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.http.Query

class HomeViewModel(private val movieDatabase: MovieDatabase) : ViewModel() {

    private var popularMovieLiveData = MutableLiveData<List<PopularMovieDetails>>()
    private var topRatedMovieLiveData = MutableLiveData<List<TopRatedMovieDetails>>()
    private var upComingMovieLiveData = MutableLiveData<List<UpComingMovieDetails>>()
    private var favoritesMoviesLiveData = movieDatabase.movieDao().getAllMovies()
    private var searchMoviesLiveData = MutableLiveData<List<MovieDetails>>()
    private var TAG = "HomeViewModel"

    init {
        getPopularMovie()
        getTopRatedMovie()
        getUpComingMovie()
    }

    fun getPopularMovie() = viewModelScope.launch {
        try {
            val response: Response<PopularMovie> =
                RetrofitInstance.api.getPopularMovies(1, Constants.API_KEY)
            Log.d(TAG, "getPopularMovie")
            if (response.isSuccessful) {
                val popularMovies = response.body()!!.results
                popularMovieLiveData.postValue(popularMovies)
                Log.d(TAG, "getPopularMovie Successful response")

            } else {
                Log.d(TAG, "An Error Occurred")
            }
        } catch (e: Exception) {
            Log.d(TAG, "Exception1: $e")
        }
    }

    fun observePopularMovieLiveData(): LiveData<List<PopularMovieDetails>> {
        Log.d(TAG, "observePopularMovieLiveDataObserve")
        return popularMovieLiveData
    }

    fun getTopRatedMovie() = viewModelScope.launch {
        try {
            val response: Response<TopRatedMovie> =
                RetrofitInstance.api.getTopRatedMovies(1, Constants.API_KEY)
            Log.d(TAG, "getTopRatedMovie")
            if (response.isSuccessful) {
                val topRatedMovie = response.body()!!.results
                topRatedMovieLiveData.postValue(topRatedMovie)
                Log.d(TAG, "getTopRatedMovie Successful response")
            } else {
                Log.d(TAG, "An Error Occurred")
            }
        } catch (e: Exception) {
            Log.d(TAG, "Exception2: $e")
        }
    }

    fun observeTopRatedMovie(): LiveData<List<TopRatedMovieDetails>> {
        Log.d(TAG, "observeTopRatedMovieObserve")
        return topRatedMovieLiveData
    }

    fun getUpComingMovie() = viewModelScope.launch {
        try {
            val response: Response<UpComingMovie> =
                RetrofitInstance.api.getUpComingMovies(1, Constants.API_KEY)
            Log.d(TAG, "getUpComingMovie")
            if (response.isSuccessful) {
                val upComingMovie = response.body()!!.results
                upComingMovieLiveData.postValue(upComingMovie)
                Log.d(TAG, "getUpComingMovie Successful response")
            }
        } catch (e: Exception) {
            Log.d(TAG, "Exception3: $e")
        }
    }

    fun observeUpComingMovie(): LiveData<List<UpComingMovieDetails>> {
        Log.d(TAG, "observeUpComingMovieObserve")
        return upComingMovieLiveData
    }

    fun observeFavoritesMoviesLiveData(): LiveData<List<MovieDetails>> {
        return favoritesMoviesLiveData
    }

    fun upsertMovie(movie: MovieDetails) {
        viewModelScope.launch {
            movieDatabase.movieDao().upsert(movie)
        }
    }

    fun deleteMovie(movie: MovieDetails) {
        viewModelScope.launch {
            movieDatabase.movieDao().delete(movie)
        }
    }

    fun getSearchMovie(searchQuery: String) = viewModelScope.launch {
        Log.d(TAG, "getSearchMovie")
        try {
            val response: Response<SearchMovieDetails> =
                RetrofitInstance.api.getSearchMovie(1, Constants.API_KEY, searchQuery)
            Log.d(TAG, "getSearchMovie1")
            if (response.isSuccessful) {
                Log.d(TAG, "getSearchMovie2")
                val searchedMovies = response.body()!!.results
                searchedMovies?.let {
                    searchMoviesLiveData.postValue(searchedMovies)
                    Log.d(TAG, "Searched Movie ...")
                }
                Log.d(TAG, "Search Movie LiveData Updated: ${searchedMovies.size} items")
                Log.d(TAG, "getSearchMovie3")
            } else {
                Log.d(TAG, "Search Movie API Call Failed. Response: ${response.errorBody()}")
            }
        } catch (e: Exception) {
            Log.d(TAG, "An error occurred.")
            Log.d(TAG, e.toString())
        }
    }

    fun observerSearchedMovieLiveData(): LiveData<List<MovieDetails>> {
        return searchMoviesLiveData
    }

    fun clearSearchMovies() {
        searchMoviesLiveData.value = null
    }

}