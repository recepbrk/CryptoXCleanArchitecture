package com.recepguzel.cryptoxcleanarchitecture.ui.home.favorite.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.recepguzel.cryptoxcleanarchitecture.data.model.CryptoData
import com.recepguzel.cryptoxcleanarchitecture.domain.repository.CoinRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val coinRepository: CoinRepository) :
    ViewModel() {

    fun addFavoriteCoin(coin: CryptoData) = viewModelScope.launch {
        if (!coinRepository.isCoinFavorite(coin.name)) {
            coinRepository.saveCoin(coin)
        }
    }

    fun deleteFavoriteCoin(coin: CryptoData) = viewModelScope.launch {
        coinRepository.deleteCoin(coin)
    }

    fun getFavoriteList() = coinRepository.getFavoriteCoins()
}