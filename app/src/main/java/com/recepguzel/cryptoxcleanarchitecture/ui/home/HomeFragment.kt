package com.recepguzel.cryptoxcleanarchitecture.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.recepguzel.cryptoxcleanarchitecture.databinding.FragmentHomeBinding
import com.recepguzel.cryptoxcleanarchitecture.ui.home.coinlist.fragment.CoinListFragment
import com.recepguzel.cryptoxcleanarchitecture.ui.home.favorite.fragment.FavoriteFragment


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentAdapter = FragmentAdapter(childFragmentManager)
        fragmentAdapter.addFragment(CoinListFragment(), "CoinList")
        fragmentAdapter.addFragment(FavoriteFragment(), "Favorite")

        binding.viewPager.adapter = fragmentAdapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }

}