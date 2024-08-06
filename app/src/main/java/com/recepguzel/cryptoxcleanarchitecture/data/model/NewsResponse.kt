package com.recepguzel.cryptoxcleanarchitecture.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
) : Parcelable {
    @Parcelize
    data class Article(
        val id: Int,
        val author: String,
        val content: String,
        val description: String,
        val publishedAt: String,
        val source: Source,
        val title: String,
        val url: String,
        val urlToImage: String
    ): Parcelable
    @Parcelize
    data class Source(
        val name: String
    ): Parcelable
}

