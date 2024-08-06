package com.recepguzel.cryptoxcleanarchitecture.data.source.remote

import com.recepguzel.cryptoxcleanarchitecture.data.model.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsNetworkService {
    @GET("v2/everything")
    suspend fun getEverythingNews(
        @Query("q") query: String,
        @Query("page") pageNumber: Int = 1,
        @Query("apiKey") apiKey: String
    ): Response<NewsResponse>
}
