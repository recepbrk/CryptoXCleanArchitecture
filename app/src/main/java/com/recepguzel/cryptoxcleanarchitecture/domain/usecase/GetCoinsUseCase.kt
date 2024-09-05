package com.recepguzel.cryptoxcleanarchitecture.domain.usecase

import com.recepguzel.cryptoxcleanarchitecture.data.model.CryptoData
import com.recepguzel.cryptoxcleanarchitecture.domain.repository.CoinRepository
import com.recepguzel.cryptoxcleanarchitecture.util.resource.Resource
import javax.inject.Inject

class GetCoinsUseCase @Inject constructor(private val coinRepository: CoinRepository) {


    suspend fun getCoins(): Resource<List<CryptoData>> {
        return coinRepository.getCoins()
    }


    suspend fun saveCoin(coin: CryptoData): Long {
        return coinRepository.saveCoin(coin)
    }


    suspend fun deleteCoin(coin: CryptoData) {
        coinRepository.deleteCoin(coin)
    }
}