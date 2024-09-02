package com.recepguzel.cryptoxcleanarchitecture.ui.dictionary.fragment

import DictionaryAdapter
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
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
    private var doubleBackToExitPressedOnce = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDictionaryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeUI()
        observeViewModel()
        handleBackPress()
    }

    private fun initializeUI() {
        setupAdapter()
        setupSearch()
        viewModel.loadCryptoTerms()
    }

    private fun observeViewModel() {
        viewModel.cryptoTermList.observe(viewLifecycleOwner) { terms ->
            onCryptoTermsLoaded(terms)
        }
    }

    private fun onCryptoTermsLoaded(terms: List<CryptoTerm>) {
        Log.d("DictionaryFragment", "Alınan terimler: $terms")
        binding.progressBar.visibility = View.GONE
        allCoins = terms
        adapter.differ.submitList(terms)
    }

    private fun setupAdapter() {
        adapter = DictionaryAdapter()
        binding.recyclerView.adapter = adapter
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupSearch() {
        binding.searchEditText.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                if (isDrawableRightClicked(v as EditText, event)) {
                    clearSearchField(v)
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

    private fun isDrawableRightClicked(editText: EditText, event: MotionEvent): Boolean {
        val drawableRight = editText.compoundDrawables[2]
        return drawableRight != null && event.rawX >= (editText.right - drawableRight.bounds.width())
    }

    private fun clearSearchField(editText: EditText) {
        editText.text = null
        hideKeyboard(editText)
    }

    private fun filterCoins(query: String?) {
        val filteredList = if (query.isNullOrEmpty()) {
            allCoins
        } else {
            allCoins.filter { it.term.contains(query, ignoreCase = true) }
        }
        adapter.differ.submitList(filteredList)
    }

    private fun hideKeyboard(view: View) {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun handleBackPress() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (doubleBackToExitPressedOnce) {
                        activity?.finish()
                    } else {
                        notifyUserToExit()
                        resetDoubleBackPressFlagWithDelay()
                    }
                }
            })
    }

    private fun notifyUserToExit() {
        doubleBackToExitPressedOnce = true
        Toast.makeText(requireContext(), "Press back again to exit.", Toast.LENGTH_SHORT).show()
    }

    private fun resetDoubleBackPressFlagWithDelay() {
        Handler(Looper.getMainLooper()).postDelayed({
            doubleBackToExitPressedOnce = false
        }, 2000)
    }
}
