package com.recepguzel.cryptoxcleanarchitecture.ui.home.coinlist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.recepguzel.cryptoxcleanarchitecture.data.model.CryptoData
import com.recepguzel.cryptoxcleanarchitecture.domain.usecase.GetCoinsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val getCoinsUseCase: GetCoinsUseCase
) : ViewModel() {



    fun fetchCoins() = liveData {
        val result = getCoinsUseCase.getCoins()
        emit(result)
    }

    fun saveCoin(coin: CryptoData) = viewModelScope.launch {
        getCoinsUseCase.saveCoin(coin)
    }

}
