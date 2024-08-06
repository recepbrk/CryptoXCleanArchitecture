package com.recepguzel.cryptoxcleanarchitecture.di

import com.recepguzel.cryptoxcleanarchitecture.data.repository.coin.datasource.CoinRemoteDataSource
import com.recepguzel.cryptoxcleanarchitecture.data.repository.coin.datasourceImpl.CoinRemoteDataSourceImpl
import com.recepguzel.cryptoxcleanarchitecture.data.repository.news.datasource.NewsRemoteDataSource
import com.recepguzel.cryptoxcleanarchitecture.data.repository.news.datasourceImpl.NewsRemoteDataSourceImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindCoinRemoteDataSource(
        coinRemoteDataSourceImpl: CoinRemoteDataSourceImpl
    ): CoinRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindNewsRemoteDataSource(
        newsRemoteDataSourceImpl: NewsRemoteDataSourceImp
    ): NewsRemoteDataSource


}
