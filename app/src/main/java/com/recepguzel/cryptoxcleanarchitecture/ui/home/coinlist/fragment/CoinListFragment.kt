package com.recepguzel.cryptoxcleanarchitecture.ui.home.coinlist.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.recepguzel.cryptoxcleanarchitecture.R
import com.recepguzel.cryptoxcleanarchitecture.data.model.CryptoData
import com.recepguzel.cryptoxcleanarchitecture.databinding.FragmentCoinListBinding
import com.recepguzel.cryptoxcleanarchitecture.ui.home.HomeFragmentDirections
import com.recepguzel.cryptoxcleanarchitecture.ui.home.coinlist.adapter.CoinListAdapter
import com.recepguzel.cryptoxcleanarchitecture.ui.home.coinlist.viewmodel.CoinListViewModel
import com.recepguzel.cryptoxcleanarchitecture.util.resource.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoinListFragment : Fragment() {
    private lateinit var binding: FragmentCoinListBinding
    private val coinListViewModel: CoinListViewModel by viewModels()
    private lateinit var coinListAdapter: CoinListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCoinListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.swipeRefreshLayout.setColorSchemeResources(R.color.blue)
        binding.swipeRefreshLayout.setOnRefreshListener {
            coinListViewModel.fetchCoins()
            initObserve()
        }
        coinListViewModel.fetchCoins()
        initObserve()


    }

    private fun initObserve() {
        coinListViewModel.fetchCoins().observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let {
                        initAdapter(it)
                    }
                    binding.coinlistProgressBar.visibility = View.GONE
                    binding.swipeRefreshLayout.isRefreshing = false
                    coinListAdapter.setOnItemClickListener {
                          val action =
                           HomeFragmentDirections.actionHomeFragmentToCoinDetailFragment(it)
                           findNavController().navigate(action)
                    }
                }

                is Resource.Error -> {
                    Toast.makeText(context, "${response.message}", Toast.LENGTH_SHORT).show()
                    Log.e("hata", "${response.message}")
                    binding.coinlistProgressBar.visibility = View.VISIBLE
                    binding.swipeRefreshLayout.isRefreshing = true


                }

                is Resource.Loading -> {
                    binding.coinlistProgressBar.visibility = View.VISIBLE
                    binding.swipeRefreshLayout.isRefreshing = true


                }

                else -> {}
            }

        }
    }

    private fun initAdapter(coin: List<CryptoData>) {
        coinListAdapter = CoinListAdapter()
        binding.coinlistRecyclerView.adapter = coinListAdapter
        binding.coinlistRecyclerView.setHasFixedSize(true)
        coinListAdapter.differ.submitList(coin)
    }

}

