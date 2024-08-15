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

        fragmentAdapter.addFragment(NewsFragment().apply {
            arguments = createBundle("Bitcoin")
        }, "Bitcoin")
        fragmentAdapter.addFragment(NewsFragment().apply {
            arguments = createBundle("Altcoin")
        }, "Altcoin")

        fragmentAdapter.addFragment(NewsFragment().apply {
            arguments = createBundle("Blockchain")
        }, "Blockchain")

        fragmentAdapter.addFragment(NewsFragment().apply {
            arguments = createBundle("Nft")
        }, "Nft")

        fragmentAdapter.addFragment(NewsFragment().apply {
            arguments = createBundle("Metaverse")
        }, "Metaverse")

        fragmentAdapter.addFragment(NewsFragment().apply {
            arguments = createBundle("Web3")
        }, "Web3")


        binding.newsViewPager.adapter = fragmentAdapter
        binding.newsTabLayout.setupWithViewPager(binding.newsViewPager)
    }

    private fun createBundle(category: String): Bundle {
        val bundle = Bundle()
        bundle.putString("category", category)
        return bundle
    }
}