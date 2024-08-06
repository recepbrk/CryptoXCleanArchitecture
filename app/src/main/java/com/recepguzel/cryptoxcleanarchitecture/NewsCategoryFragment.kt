package com.recepguzel.cryptoxcleanarchitecture

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.recepguzel.cryptoxcleanarchitecture.databinding.FragmentNewsCategoryBinding
import com.recepguzel.cryptoxcleanarchitecture.ui.news.NewsFragment
import com.recepguzel.cryptoxcleanarchitecture.ui.news.NewsFragmentAdapter

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

        fragmentAdapter.addFragment(NewsFragment().apply {
            arguments = createBundle("NFT")
        }, "NFT")
        fragmentAdapter.addFragment(NewsFragment().apply {
            arguments = createBundle("ETH")
        }, "ETH")
        fragmentAdapter.addFragment(NewsFragment().apply {
            arguments = createBundle("METAVERSE")
        }, "METAVERSE")

        binding.newsViewPager.adapter = fragmentAdapter
        binding.newsTabLayout.setupWithViewPager(binding.newsViewPager)
    }

    private fun createBundle(category: String): Bundle {
        val bundle = Bundle()
        bundle.putString("category", category)
        return bundle
    }
}

