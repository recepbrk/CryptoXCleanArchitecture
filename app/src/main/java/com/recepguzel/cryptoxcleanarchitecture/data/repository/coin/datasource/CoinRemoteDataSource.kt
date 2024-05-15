package com.recepguzel.cryptoxcleanarchitecture.data.repository.coin.datasource

import com.recepguzel.cryptoxcleanarchitecture.data.model.CryptoData
import com.recepguzel.cryptoxcleanarchitecture.util.resource.Resource

interface CoinRemoteDataSource {
    suspend fun getCoinFromRemote(): Resource<List<CryptoData>>
}