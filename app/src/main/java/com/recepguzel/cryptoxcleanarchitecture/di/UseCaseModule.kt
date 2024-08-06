package com.recepguzel.cryptoxcleanarchitecture.di

import com.recepguzel.cryptoxcleanarchitecture.domain.repository.CoinRepository
import com.recepguzel.cryptoxcleanarchitecture.domain.repository.NewsRepository
import com.recepguzel.cryptoxcleanarchitecture.domain.usecase.GetCoinsUseCase
import com.recepguzel.cryptoxcleanarchitecture.domain.usecase.GetNewsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Singleton
    @Provides
    fun provideGetCoinsUseCase(coinRepository: CoinRepository): GetCoinsUseCase {
        return GetCoinsUseCase(coinRepository)
    }

    @Singleton
    @Provides
    fun provideGetNewsUseCase(newsRepository: NewsRepository): GetNewsUseCase {
        return GetNewsUseCase(newsRepository)
    }
}
