package com.recepguzel.cryptoxcleanarchitecture.data.source.remote

import com.recepguzel.cryptoxcleanarchitecture.data.model.CoinResponse
import com.recepguzel.cryptoxcleanarchitecture.util.constants.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface CoinNetworkService {

    @GET(Constants.COIN_ENDPOINT)
    suspend fun getCoinList(@Query("start") start: Int, @Query("limit") limit: Int): CoinResponse
}