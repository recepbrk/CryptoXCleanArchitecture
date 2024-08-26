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
import androidx.navigation.fragment.navArgs
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.recepguzel.cryptoxcleanarchitecture.data.model.NewsResponse
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
    private var mInterstitialAd: InterstitialAd? = null
    private final val TAG = "NewsFragment"
    private val args: NewsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("NewsFragment", "onCreateView: Inflating FragmentNewsBinding")
        binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val category = args.category // Get the category from SafeArgs
        Log.d("NewsFragment", "onViewCreated: Category received: $category")

        setupRecyclerView()
        observeData()
        loadInterAds()

        category?.let {
            newsViewModel.getCryptoNews(it)
        }
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        binding.newsRecyclerview.adapter = newsAdapter
        Log.d("NewsFragment", "setupRecyclerView: RecyclerView initialized")

        newsAdapter.OnItemClickListener { article ->
            if (mInterstitialAd != null) {
                mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        navigateToNewsDetail(article)
                    }

                    override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                        navigateToNewsDetail(article)
                    }
                }
                mInterstitialAd?.show(requireActivity())
            } else {
                navigateToNewsDetail(article)
            }
        }
    }

    private fun loadInterAds() {
        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(requireContext(), "ca-app-pub-3940256099942544/1033173712", adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d(TAG, "Ad failed to load: ${adError.message}")
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                Log.d(TAG, "Ad was loaded.")
                mInterstitialAd = interstitialAd
            }
        })
    }

    private fun observeData() {
        newsViewModel.searchNews.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> {
                    Log.d("NewsFragment", "observeData: Data loaded successfully")
                    newsAdapter.differ.submitList(resource.data?.articles)
                    binding.newsProgress.visibility = View.GONE
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

    private fun navigateToNewsDetail(article: NewsResponse.Article) {
        Log.d("NewsFragment", "navigateToNewsDetail: Navigating to details")
        val action = NewsFragmentDirections.actionNewsFragmentToNewsDetailFragment(article)
        findNavController().navigate(action)
    }
}
