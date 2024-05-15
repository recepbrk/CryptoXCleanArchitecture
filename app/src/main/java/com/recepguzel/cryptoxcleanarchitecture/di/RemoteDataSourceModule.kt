package com.recepguzel.cryptoxcleanarchitecture.di

import com.recepguzel.cryptoxcleanarchitecture.data.repository.coin.datasource.CoinRemoteDataSource
import com.recepguzel.cryptoxcleanarchitecture.data.repository.coin.datasourceImpl.CoinRemoteDataSourceImpl
import com.recepguzel.cryptoxcleanarchitecture.data.source.remote.CoinNetworkService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteDataSourceModule {

    @Singleton
    @Provides
    fun provideCoinRemoteDataSource(coinNetworkService: CoinNetworkService): CoinRemoteDataSource {
        return CoinRemoteDataSourceImpl(coinNetworkService)
    }
}