package com.recepguzel.cryptoxcleanarchitecture.di

import com.recepguzel.cryptoxcleanarchitecture.data.repository.coin.CoinRepositoryImpl
import com.recepguzel.cryptoxcleanarchitecture.data.repository.coin.datasource.CoinLocaleDataSource
import com.recepguzel.cryptoxcleanarchitecture.data.repository.coin.datasource.CoinRemoteDataSource
import com.recepguzel.cryptoxcleanarchitecture.domain.repository.CoinRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideCoinRepository(
        coinRemoteDataSource: CoinRemoteDataSource,
        coinLocaleDataSource: CoinLocaleDataSource
    ): CoinRepository {
        return CoinRepositoryImpl(coinRemoteDataSource, coinLocaleDataSource)
    }
}