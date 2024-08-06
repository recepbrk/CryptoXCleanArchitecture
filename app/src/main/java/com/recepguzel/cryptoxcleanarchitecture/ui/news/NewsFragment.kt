package com.recepguzel.cryptoxcleanarchitecture.ui.news

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.recepguzel.cryptoxcleanarchitecture.databinding.FragmentNewsBinding
import com.recepguzel.cryptoxcleanarchitecture.ui.news.adapter.NewsAdapter
import com.recepguzel.cryptoxcleanarchitecture.util.resource.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFragment : Fragment() {

    private lateinit var binding: FragmentNewsBinding
    private val newsViewModel: NewsViewModel by viewModels()
    private lateinit var newsAdapter: NewsAdapter
    private var category: String? = "Bitcoin"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            category = it.getString("category")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeData()
        category?.let { newsViewModel.getCryptoNews(it) }
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        binding.newsRecyclerview.adapter = newsAdapter
    }

    private fun observeData() {
        newsViewModel.searchNews.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> {
                    newsAdapter.differ.submitList(resource.data?.articles)
                    binding.newsProgress.visibility = View.GONE
                }

                is Resource.Loading -> {
                    binding.newsProgress.visibility = View.VISIBLE
                }

                is Resource.Error -> {
                    binding.newsProgress.visibility = View.GONE
                    Toast.makeText(context, resource.message, Toast.LENGTH_SHORT).show()
                    Log.e("NewsFragment", "Error: ${resource.message}")
                }
            }
        }
    }
}
