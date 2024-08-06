package com.recepguzel.cryptoxcleanarchitecture.data.repository.news.datasource

import com.recepguzel.cryptoxcleanarchitecture.data.model.NewsResponse
import retrofit2.Response

interface NewsRemoteDataSource {
    suspend fun getEverythingNews(
        query: String,
        pageNumber: Int,
        apiKey: String
    ): Response<NewsResponse>
}
