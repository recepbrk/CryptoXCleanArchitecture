package com.recepguzel.cryptoxcleanarchitecture.data.repository.coin.datasourceImpl

import androidx.lifecycle.LiveData
import com.recepguzel.cryptoxcleanarchitecture.data.model.CryptoData
import com.recepguzel.cryptoxcleanarchitecture.data.repository.coin.datasource.CoinLocaleDataSource
import com.recepguzel.cryptoxcleanarchitecture.data.source.locale.CoinFavoriteDao
import javax.inject.Inject


class CoinLocaleDataSourceImpl @Inject constructor(private val coinFavoriteDao: CoinFavoriteDao) :
    CoinLocaleDataSource {

    override suspend fun saveCoinDataToLocal(coin: CryptoData): Long {
        return if (!isCoinFavorite(coin.name)) {
            coinFavoriteDao.addCoin(coin)
        } else
            -1
    }

    override suspend fun deleteCoinDataToLocal(coin: CryptoData) {
        coinFavoriteDao.deleteCoin(coin)
    }

    override fun getCoinDataFromLocal(): LiveData<List<CryptoData>> {
        return coinFavoriteDao.getFavoriteList()
    }

    override suspend fun isCoinFavorite(coinName: String): Boolean {
        return coinFavoriteDao.isCoinFavorite(coinName) > 0
    }
}
