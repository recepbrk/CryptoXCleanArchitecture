package com.recepguzel.cryptoxcleanarchitecture.data.repository.coin

import androidx.lifecycle.LiveData
import com.recepguzel.cryptoxcleanarchitecture.data.model.CryptoData
import com.recepguzel.cryptoxcleanarchitecture.data.repository.coin.datasource.CoinLocaleDataSource
import com.recepguzel.cryptoxcleanarchitecture.data.repository.coin.datasource.CoinRemoteDataSource
import com.recepguzel.cryptoxcleanarchitecture.domain.repository.CoinRepository
import com.recepguzel.cryptoxcleanarchitecture.util.resource.Resource
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val coinRemoteDataSource: CoinRemoteDataSource,
    private val coinLocaleDataSource: CoinLocaleDataSource
) : CoinRepository {

    override suspend fun getCoins(): Resource<List<CryptoData>> {
        return coinRemoteDataSource.getCoinFromRemote()
    }

    override suspend fun saveCoin(coin: CryptoData): Long {
        return coinLocaleDataSource.saveCoinDataToLocal(coin)
    }

    override suspend fun deleteCoin(coin: CryptoData) {
        coinLocaleDataSource.deleteCoinDataToLocal(coin)
    }

    override fun getFavoriteCoins(): LiveData<List<CryptoData>> {
        return coinLocaleDataSource.getCoinDataFromLocal()
    }

    override suspend fun isCoinFavorite(coinName: String): Boolean {
        return coinLocaleDataSource.isCoinFavorite(coinName)
    }
}
