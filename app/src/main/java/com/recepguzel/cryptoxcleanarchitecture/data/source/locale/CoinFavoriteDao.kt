package com.recepguzel.cryptoxcleanarchitecture.data.source.locale

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.recepguzel.cryptoxcleanarchitecture.data.model.CryptoData
@Dao
interface CoinFavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCoin(coin: CryptoData): Long

    @Delete
    suspend fun deleteCoin(coin: CryptoData)

    @Query("SELECT*FROM favorite_table")
    fun getFavoriteList(): LiveData<List<CryptoData>>

    @Query("SELECT name FROM favorite_table")
    fun getCoinTitles(): List<String>
}