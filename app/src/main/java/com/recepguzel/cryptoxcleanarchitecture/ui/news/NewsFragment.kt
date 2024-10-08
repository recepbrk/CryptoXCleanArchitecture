package com.recepguzel.cryptoxcleanarchitecture.ui.news

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
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
    private val TAG = "NewsFragment"
    private var doubleBackToExitPressedOnce = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "onCreateView: Inflating FragmentABinding")
        binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeData()
        loadInterAds()
        handleBackPress()
        initializeViewModel()
    }

    private fun initializeViewModel() {

        newsViewModel.getCryptoNews("Bitcoin")

    }

    private fun handleBackPress() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (doubleBackToExitPressedOnce) {
                        activity?.finish() // Exit the application
                    } else {
                        doubleBackToExitPressedOnce = true
                        Toast.makeText(
                            requireContext(),
                            "Press back again to exit.",
                            Toast.LENGTH_SHORT
                        ).show()

                        Handler(Looper.getMainLooper()).postDelayed({
                            doubleBackToExitPressedOnce = false
                        }, 2000) // Reset flag after 2 seconds
                    }
                }
            })
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        binding.newsRecyclerview.adapter = newsAdapter
        Log.d(TAG, "setupRecyclerView: RecyclerView initialized")

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
        InterstitialAd.load(
            requireContext(),
            "ca-app-pub-7925100098336203/2351373425",
            adRequest,
            object : InterstitialAdLoadCallback() {
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
                    Log.d(TAG, "observeData: Data loaded successfully")
                    newsAdapter.differ.submitList(resource.data?.articles)
                    binding.newsProgress.visibility = View.GONE
                }

                is Resource.Loading -> {
                    Log.d(TAG, "observeData: Data loading in progress")
                    binding.newsProgress.visibility = View.VISIBLE
                }

                is Resource.Error -> {
                    Log.e(TAG, "observeData: Error occurred - ${resource.message}")
                    binding.newsProgress.visibility = View.GONE
                    Toast.makeText(context, resource.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun navigateToNewsDetail(article: NewsResponse.Article) {
        Log.d(TAG, "navigateToNewsDetail: Navigating to details")
        val action = NewsFragmentDirections.actionAFragmentToNewsDetailFragment(article)
        findNavController().navigate(action)
    }
}
