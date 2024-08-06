package com.recepguzel.cryptoxcleanarchitecture.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.recepguzel.cryptoxcleanarchitecture.data.source.remote.CoinNetworkService
import com.recepguzel.cryptoxcleanarchitecture.data.source.remote.NewsNetworkService
import com.recepguzel.cryptoxcleanarchitecture.util.constants.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    @Named("NewsRetrofit")
    fun provideNewsRetrofit(
        okHttpClient: OkHttpClient,
        gson: Gson
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL_NEWS)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Singleton
    @Provides
    fun provideNewsNetworkService(
        @Named("NewsRetrofit") retrofit: Retrofit
    ): NewsNetworkService {
        return retrofit.create(NewsNetworkService::class.java)
    }

    @Singleton
    @Provides
    @Named("CoinRetrofit")
    fun provideCoinRetrofit(
        okHttpClient: OkHttpClient,
        gson: Gson
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL_COİN)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Singleton
    @Provides
    fun provideCoinNetworkService(
        @Named("CoinRetrofit") retrofit: Retrofit
    ): CoinNetworkService {
        return retrofit.create(CoinNetworkService::class.java)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .readTimeout(Constants.NETWORK_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(Constants.NETWORK_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return GsonBuilder()
            .setLenient()
            .create()
    }
}
