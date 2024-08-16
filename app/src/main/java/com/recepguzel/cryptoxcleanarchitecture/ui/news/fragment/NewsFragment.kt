package com.recepguzel.cryptoxcleanarchitecture.ui.news.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.recepguzel.cryptoxcleanarchitecture.databinding.FragmentNewsBinding
import com.recepguzel.cryptoxcleanarchitecture.ui.news.adapter.NewsAdapter
import com.recepguzel.cryptoxcleanarchitecture.ui.news.viewmodel.NewsViewModel
import com.recepguzel.cryptoxcleanarchitecture.util.resource.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFragment : Fragment() {

    private lateinit var binding: FragmentNewsBinding
    private val newsViewModel: NewsViewModel by viewModels()
    private lateinit var newsAdapter: NewsAdapter
    private var category: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("NewsFragment", "onCreateView: Inflating FragmentNewsBinding")
        binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            category = it.getString("category")
            Log.d("NewsFragment", "onViewCreated: Category received: $category")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("NewsFragment", "onViewCreated: View created")

        arguments?.let {
            category = it.getString("category")
            Log.d("NewsFragment", "onViewCreated: Category received: $category")

            setupRecyclerView()
            observeData()

            category?.let { category ->
                newsViewModel.getCryptoNews(category)
            }
        }
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        binding.newsRecyclerview.adapter = newsAdapter
        Log.d("NewsFragment", "setupRecyclerView: RecyclerView initialized")
    }

    private fun observeData() {
        newsViewModel.searchNews.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> {
                    Log.d("NewsFragment", "observeData: Data loaded successfully")
                    newsAdapter.differ.submitList(resource.data?.articles)
                    binding.newsProgress.visibility = View.GONE

                    newsAdapter.OnItemClickListener { article ->
                        Log.d("NewsFragment", "observeData: Item clicked - Navigating to details")
                        val action =
                            NewsFragmentDirections.actionNewsFragmentToNewsDetailFragment(article)
                        findNavController().navigate(action)
                    }
                }

                is Resource.Loading -> {
                    Log.d("NewsFragment", "observeData: Data loading in progress")
                    binding.newsProgress.visibility = View.VISIBLE
                }

                is Resource.Error -> {
                    Log.e("NewsFragment", "observeData: Error occurred - ${resource.message}")
                    binding.newsProgress.visibility = View.GONE
                    Toast.makeText(context, resource.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
