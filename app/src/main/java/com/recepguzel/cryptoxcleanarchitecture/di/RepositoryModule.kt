package com.recepguzel.cryptoxcleanarchitecture.di

import com.recepguzel.cryptoxcleanarchitecture.data.repository.coin.CoinRepositoryImpl
import com.recepguzel.cryptoxcleanarchitecture.data.repository.news.NewsRepositoryImpl
import com.recepguzel.cryptoxcleanarchitecture.domain.repository.CoinRepository
import com.recepguzel.cryptoxcleanarchitecture.domain.repository.NewsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCoinRepository(
        coinRepositoryImpl: CoinRepositoryImpl
    ): CoinRepository

    @Binds
    @Singleton
    abstract fun bindNewsRepository(
        newsRepositoryImpl: NewsRepositoryImpl
    ): NewsRepository
}
