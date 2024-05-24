package com.recepguzel.cryptoxcleanarchitecture.ui.dictionary.fragment

import DictionaryAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.recepguzel.cryptoxcleanarchitecture.databinding.FragmentDictionaryBinding
import com.recepguzel.cryptoxcleanarchitecture.ui.dictionary.viewmodel.DictionaryViewModel

class DictionaryFragment : Fragment() {
    private lateinit var binding: FragmentDictionaryBinding
    private val viewModel: DictionaryViewModel by viewModels()
    private lateinit var adapter: DictionaryAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDictionaryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = DictionaryAdapter()
        binding.recyclerView.adapter = adapter
        viewModel.cryptoTermList.observe(viewLifecycleOwner) { terms ->
            Log.d("DictionaryFragment", "Alınan terimler: $terms")
            binding.progressBar.visibility=View.GONE
            adapter.differ.submitList(terms)
        }

        // ViewModel'de verileri yükle
        viewModel.loadCryptoTerms()
    }


}