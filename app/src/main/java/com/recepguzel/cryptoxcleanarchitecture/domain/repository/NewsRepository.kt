package com.recepguzel.cryptoxcleanarchitecture.domain.repository

import com.recepguzel.cryptoxcleanarchitecture.data.model.NewsResponse

interface NewsRepository {
    suspend fun getCryptoNews(searchQuery: String, pageNumber: Int): NewsResponse?
}
