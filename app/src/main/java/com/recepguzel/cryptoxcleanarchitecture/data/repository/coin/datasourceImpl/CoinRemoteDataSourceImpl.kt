package com.recepguzel.cryptoxcleanarchitecture.data.repository.coin.datasourceImpl

import com.recepguzel.cryptoxcleanarchitecture.data.model.CryptoData
import com.recepguzel.cryptoxcleanarchitecture.data.repository.coin.datasource.CoinRemoteDataSource
import com.recepguzel.cryptoxcleanarchitecture.data.source.remote.CoinNetworkService
import com.recepguzel.cryptoxcleanarchitecture.util.resource.Resource
import javax.inject.Inject

class CoinRemoteDataSourceImpl @Inject constructor(
    private val coinNetworkService: CoinNetworkService
) : CoinRemoteDataSource {

    override suspend fun getCoinFromRemote(): Resource<List<CryptoData>> {
        return try {
            val start = 1
            val limit = 200
            val response = coinNetworkService.getCoinList(start, limit)

            // Convert CoinResponse to Resource<List<CryptoData>>
            val cryptoDataList = response.data.cryptoCurrencyList
            Resource.Success(cryptoDataList)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "An error occurred")
        }
    }
}
