package com.example.mvvmmovieapp.models


import com.google.gson.annotations.SerializedName

data class PopularMovie(
    val page: Int,
    val results: List<PopularMovieDetails>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)