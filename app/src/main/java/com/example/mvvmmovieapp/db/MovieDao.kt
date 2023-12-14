package com.example.mvvmmovieapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mvvmmovieapp.models.MovieDetails

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(movie: MovieDetails)

    @Delete
    suspend fun delete(movie: MovieDetails)

    @Query("SELECT * FROM movieInformation")
    fun getAllMovies(): LiveData<List<MovieDetails>>
}