package com.example.mvvmmovieapp.retrofit

import com.example.mvvmmovieapp.models.MovieDetails
import com.example.mvvmmovieapp.models.PopularMovie
import com.example.mvvmmovieapp.models.SearchMovieDetails
import com.example.mvvmmovieapp.models.TopRatedMovie
import com.example.mvvmmovieapp.models.UpComingMovie

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String
    ): Response<PopularMovie>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String
    ): Response<TopRatedMovie>

    @GET("movie/upcoming")
    suspend fun getUpComingMovies(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String
    ): Response<UpComingMovie>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") searchQuery: String,
        @Query("api_key") apiKey: String
    ): Response<MovieDetails>

    @GET("search/movie")
    suspend fun getSearchMovie(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String,
        @Query("query") searchQuery: String
    ): Response<SearchMovieDetails>

}