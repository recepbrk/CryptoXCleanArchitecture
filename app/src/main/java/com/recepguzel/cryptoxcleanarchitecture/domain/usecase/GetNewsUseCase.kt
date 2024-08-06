package com.recepguzel.cryptoxcleanarchitecture.domain.usecase

import com.recepguzel.cryptoxcleanarchitecture.domain.repository.NewsRepository
import com.recepguzel.cryptoxcleanarchitecture.data.model.NewsResponse
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(searchQuery: String, pageNumber: Int): NewsResponse? {
        return newsRepository.getCryptoNews(searchQuery, pageNumber)
    }
}
