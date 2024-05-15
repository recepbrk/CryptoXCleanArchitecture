package com.recepguzel.cryptoxcleanarchitecture.di

import com.recepguzel.cryptoxcleanarchitecture.data.repository.coin.datasource.CoinLocaleDataSource
import com.recepguzel.cryptoxcleanarchitecture.data.repository.coin.datasourceImpl.CoinLocaleDataSourceImpl
import com.recepguzel.cryptoxcleanarchitecture.data.source.locale.CoinFavoriteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocaleDataSourceModule {

    @Singleton
    @Provides

    fun provideCoinLocaleDataSource(coinFavoriteDao: CoinFavoriteDao): CoinLocaleDataSource {
        return CoinLocaleDataSourceImpl(coinFavoriteDao)
    }
}