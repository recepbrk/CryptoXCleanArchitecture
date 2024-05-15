package com.recepguzel.cryptoxcleanarchitecture.data.source.locale

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.recepguzel.cryptoxcleanarchitecture.data.model.Quote

class TypeConvertor {

    // For List<String>
    @TypeConverter
    fun fromString(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    // For List<String>
    @TypeConverter
    fun toString(list: List<String>): String {
        return Gson().toJson(list)
    }

    // For List<Quote>
    @TypeConverter
    fun fromQuoteList(value: String): List<Quote> {
        val listType = object : TypeToken<List<Quote>>() {}.type
        return Gson().fromJson(value, listType)
    }

    // For List<Quote>
    @TypeConverter
    fun toQuoteList(list: List<Quote>): String {
        return Gson().toJson(list)
    }

    // For List<Int>
    @TypeConverter
    fun fromIntList(value: String): List<Int> {
        val listType = object : TypeToken<List<Int>>() {}.type
        return Gson().fromJson(value, listType)
    }

    // For List<Int>
    @TypeConverter
    fun toIntList(list: List<Int>): String {
        return Gson().toJson(list)
    }

}
