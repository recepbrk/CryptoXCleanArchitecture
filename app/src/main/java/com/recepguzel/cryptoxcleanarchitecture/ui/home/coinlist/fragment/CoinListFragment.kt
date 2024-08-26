package com.recepguzel.cryptoxcleanarchitecture.ui.home.coinlist.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
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
    private var allCoins: List<CryptoData> = listOf()

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
        coinListAdapter = CoinListAdapter()
        coinListViewModel.fetchCoins()
        initObserve()
        searchCoins()


    }

    @SuppressLint("ClickableViewAccessibility")
    private fun searchCoins() {
        binding.searchEditText.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val drawableRight = (v as EditText).compoundDrawables[2]
                if (drawableRight != null && event.rawX >= (v.right - drawableRight.bounds.width())) {
                    // Close iconuna tıklandığında metni sil
                    (v as EditText).text = null
                    return@setOnTouchListener true
                }
            }
            false
        }

        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                filterCoins(s.toString())
            }
        })
    }

    private fun filterCoins(query: String?) {
        val filteredList = if (query.isNullOrEmpty()) {
            allCoins
        } else {
            allCoins.filter {
                it.name.contains(query, ignoreCase = true) || it.symbol.contains(
                    query,
                    ignoreCase = true
                )
            }
        }
        coinListAdapter.differ.submitList(filteredList)
    }

    private fun initObserve() {
        coinListViewModel.fetchCoins().observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let {
                        allCoins = it
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

