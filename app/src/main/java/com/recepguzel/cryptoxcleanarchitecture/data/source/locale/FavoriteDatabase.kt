package com.recepguzel.cryptoxcleanarchitecture.data.source.locale

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.recepguzel.cryptoxcleanarchitecture.data.model.CryptoData

@Database(entities = [CryptoData::class], version = 5, exportSchema = false)
@TypeConverters(TypeConvertor::class)
abstract class FavoriteDatabase : RoomDatabase() {
    abstract fun favoriteDao(): CoinFavoriteDao
}