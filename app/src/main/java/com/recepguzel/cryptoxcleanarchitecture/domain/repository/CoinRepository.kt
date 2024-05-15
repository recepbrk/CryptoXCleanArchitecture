package com.recepguzel.cryptoxcleanarchitecture.domain.repository

import androidx.lifecycle.LiveData
import com.recepguzel.cryptoxcleanarchitecture.data.model.CryptoData
import com.recepguzel.cryptoxcleanarchitecture.util.resource.Resource

interface CoinRepository {
    suspend fun getCoins(): Resource<List<CryptoData>>
    suspend fun saveCoin(coin: CryptoData): Long
    suspend fun deleteCoin(coin: CryptoData)
    fun getFavoriteCoins(): LiveData<List<CryptoData>>
}
