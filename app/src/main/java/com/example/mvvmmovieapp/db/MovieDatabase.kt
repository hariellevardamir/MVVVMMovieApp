package com.example.mvvmmovieapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mvvmmovieapp.models.MovieDetails

@Database(entities = [MovieDetails::class], version = 4)
@TypeConverters(MovieTypeConverter::class)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {
        @Volatile
        var INSTANCE: MovieDatabase? = null

        @Synchronized
        fun getInstance(context: Context): MovieDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context, MovieDatabase::class.java, "movie.db")
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE as MovieDatabase
        }
    }
}