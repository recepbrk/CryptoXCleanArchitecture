package com.recepguzel.cryptoxcleanarchitecture.data.repository.news.datasourceImpl

import com.recepguzel.cryptoxcleanarchitecture.data.repository.news.datasource.NewsRemoteDataSource
import com.recepguzel.cryptoxcleanarchitecture.data.model.NewsResponse
import com.recepguzel.cryptoxcleanarchitecture.data.source.remote.NewsNetworkService
import retrofit2.Response
import javax.inject.Inject

class NewsRemoteDataSourceImp @Inject constructor(
    private val newsService: NewsNetworkService
) : NewsRemoteDataSource {

    override suspend fun getEverythingNews(
        query: String,
        pageNumber: Int,
        apiKey: String
    ): Response<NewsResponse> {
        return newsService.getEverythingNews(query, pageNumber, apiKey)
    }
}
