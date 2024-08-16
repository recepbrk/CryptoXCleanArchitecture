package com.recepguzel.cryptoxcleanarchitecture.ui.news.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.recepguzel.cryptoxcleanarchitecture.domain.usecase.GetNewsUseCase
import com.recepguzel.cryptoxcleanarchitecture.data.model.NewsResponse
import com.recepguzel.cryptoxcleanarchitecture.util.resource.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase
) : ViewModel() {

    private val _searchNews = MutableLiveData<Resource<NewsResponse>>()
    val searchNews: LiveData<Resource<NewsResponse>> get() = _searchNews

    fun getCryptoNews(searchQuery: String) = viewModelScope.launch {
        _searchNews.value = Resource.Loading()
        try {
            Log.d("NewsViewModel", "getCryptoNews: Fetching news for query: $searchQuery")
            val newsResponse = getNewsUseCase(searchQuery, 1)
            if (newsResponse != null) {
                _searchNews.value = Resource.Success(newsResponse)
            } else {
                _searchNews.value = Resource.Error("No data found")
            }
        } catch (e: Exception) {
            _searchNews.value = Resource.Error(e.message.orEmpty())
        }
    }
}