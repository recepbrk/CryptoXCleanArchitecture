package com.recepguzel.cryptoxcleanarchitecture.ui.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.recepguzel.cryptoxcleanarchitecture.databinding.FragmentNewsCategoryBinding
import com.recepguzel.cryptoxcleanarchitecture.ui.news.fragment.NewsFragment


class NewsCategoryFragment : Fragment() {
    private lateinit var binding: FragmentNewsCategoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentAdapter = NewsFragmentAdapter(childFragmentManager)

        fragmentAdapter.addFragment(
            createNewsFragment("Bitcoin"), "Bitcoin"
        )
        fragmentAdapter.addFragment(
            createNewsFragment("Altcoin"), "Altcoin"
        )
        fragmentAdapter.addFragment(
            createNewsFragment("Blockchain"), "Blockchain"
        )
        fragmentAdapter.addFragment(
            createNewsFragment("Nft"), "Nft"
        )
        fragmentAdapter.addFragment(
            createNewsFragment("Metaverse"), "Metaverse"
        )
        fragmentAdapter.addFragment(
            createNewsFragment("Web3"), "Web3"
        )

        binding.newsViewPager.adapter = fragmentAdapter
        binding.newsTabLayout.setupWithViewPager(binding.newsViewPager)
    }

    private fun createNewsFragment(category: String): Fragment {
        val action =
          NewsCategoryFragmentDirections.actionNewsCategoryFragmentToNewsFragment(category)
        findNavController().navigate(action)
        return NewsFragment() // Return the fragment instance here
    }
}