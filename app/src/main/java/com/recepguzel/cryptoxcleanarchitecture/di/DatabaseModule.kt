package com.recepguzel.cryptoxcleanarchitecture.di

import android.app.Application
import androidx.room.Room
import com.recepguzel.cryptoxcleanarchitecture.data.source.locale.CoinFavoriteDao
import com.recepguzel.cryptoxcleanarchitecture.data.source.locale.FavoriteDatabase
import com.recepguzel.cryptoxcleanarchitecture.util.constants.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideFavoriteDatabase(app: Application): FavoriteDatabase {
        return Room.databaseBuilder(app, FavoriteDatabase::class.java, Constants.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideCoinFavoriteDao(favoriteDatabase: FavoriteDatabase): CoinFavoriteDao {
        return favoriteDatabase.favoriteDao()
    }
}