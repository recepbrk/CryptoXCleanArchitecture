package com.recepguzel.cryptoxcleanarchitecture.ui.dictionary.fragment

import DictionaryAdapter
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.recepguzel.cryptoxcleanarchitecture.data.model.CryptoTerm
import com.recepguzel.cryptoxcleanarchitecture.databinding.FragmentDictionaryBinding
import com.recepguzel.cryptoxcleanarchitecture.ui.dictionary.viewmodel.DictionaryViewModel

class DictionaryFragment : Fragment() {
    private lateinit var binding: FragmentDictionaryBinding
    private val viewModel: DictionaryViewModel by viewModels()
    private lateinit var adapter: DictionaryAdapter
    private var allCoins: List<CryptoTerm> = listOf()

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
        searchCoins()
        viewModel.cryptoTermList.observe(viewLifecycleOwner) { terms ->
            Log.d("DictionaryFragment", "Alınan terimler: $terms")
            binding.progressBar.visibility = View.GONE
            allCoins = terms // allCoins listesini güncelle
            adapter.differ.submitList(terms)
        }

        // ViewModel'de verileri yükle
        viewModel.loadCryptoTerms()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun searchCoins() {
        binding.searchEditText.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val drawableRight = (v as EditText).compoundDrawables[2]
                if (drawableRight != null && event.rawX >= (v.right - drawableRight.bounds.width())) {
                    // Close iconuna tıklandığında metni sil ve klavyeyi kapat
                    v.text = null
                    hideKeyboard(v)
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
                it.term.contains(query, ignoreCase = true) || it.meaning.contains(
                    query,
                    ignoreCase = true
                )
            }
        }
        adapter.differ.submitList(filteredList)
    }

    private fun hideKeyboard(view: View) {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
