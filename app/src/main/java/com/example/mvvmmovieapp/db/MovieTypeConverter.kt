package com.example.mvvmmovieapp.db

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.mvvmmovieapp.models.Genre
import com.example.mvvmmovieapp.models.ProductionCompany
import com.example.mvvmmovieapp.models.ProductionCountry
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@TypeConverters
class MovieTypeConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromProductionCountryList(list: List<ProductionCountry>?): String? {
        return gson.toJson(list)
    }

    @TypeConverter
    fun toProductionCountryList(json: String?): List<ProductionCountry>? {
        return gson.fromJson(json, object : TypeToken<List<ProductionCountry>>() {}.type)
    }

    @TypeConverter
    fun fromGenreList(list: List<Genre>?): String? {
        return gson.toJson(list)
    }

    @TypeConverter
    fun toGenreList(json: String?): List<Genre>? {
        return gson.fromJson(json, object : TypeToken<List<Genre>>() {}.type)
    }

    @TypeConverter
    fun fromProductionCompanyList(list: List<ProductionCompany>?): String? {
        return gson.toJson(list)
    }

    @TypeConverter
    fun toProductionCompanyList(json: String?): List<ProductionCompany>? {
        return gson.fromJson(json, object : TypeToken<List<ProductionCompany>>() {}.type)
    }
}