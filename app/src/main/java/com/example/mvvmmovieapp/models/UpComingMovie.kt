package com.example.mvvmmovieapp.models


import com.google.gson.annotations.SerializedName

data class UpComingMovie(
    val dates: UpComingMoviesDate,
    val page: Int,
    val results: List<UpComingMovieDetails>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)