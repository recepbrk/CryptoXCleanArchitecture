package com.recepguzel.cryptoxcleanarchitecture.data.repository.coin.datasource

import androidx.lifecycle.LiveData
import com.recepguzel.cryptoxcleanarchitecture.data.model.CryptoData

interface CoinLocaleDataSource {

    suspend fun saveCoinDataToLocal(coin: CryptoData): Long

    suspend fun deleteCoinDataToLocal(coin: CryptoData)

    fun getCoinDataFromLocal(): LiveData<List<CryptoData>>
    suspend fun isCoinFavorite(coinName: String): Boolean
}