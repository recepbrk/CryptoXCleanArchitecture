package com.recepguzel.cryptoxcleanarchitecture.data.repository.news

import com.recepguzel.cryptoxcleanarchitecture.data.repository.news.datasource.NewsRemoteDataSource
import com.recepguzel.cryptoxcleanarchitecture.data.model.NewsResponse
import com.recepguzel.cryptoxcleanarchitecture.domain.repository.NewsRepository
import com.recepguzel.cryptoxcleanarchitecture.util.constants.Constants
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsDataSource: NewsRemoteDataSource
) : NewsRepository {

    override suspend fun getCryptoNews(searchQuery: String, pageNumber: Int): NewsResponse? {
        val response =
            newsDataSource.getEverythingNews(searchQuery, pageNumber, Constants.NEWS_API_KEY)
        return if (response.isSuccessful) response.body() else null
    }
}
