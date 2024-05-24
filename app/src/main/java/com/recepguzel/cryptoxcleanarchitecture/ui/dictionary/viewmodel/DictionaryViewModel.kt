package com.recepguzel.cryptoxcleanarchitecture.ui.dictionary.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.recepguzel.cryptoxcleanarchitecture.data.model.CryptoTerm
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DictionaryViewModel @Inject constructor() : ViewModel() {
    private val _cryptoTermList = MutableLiveData<List<CryptoTerm>>()
    var cryptoTermList: LiveData<List<CryptoTerm>> = _cryptoTermList

    fun loadCryptoTerms() {


        val db = FirebaseFirestore.getInstance()
        val terms = mutableListOf<CryptoTerm>()

        db.collection("cryptoTerms").orderBy("terms").get()
            .addOnSuccessListener { result ->
                Log.d("DictionaryFragment", "Veriler başarıyla alındı: $result")
                for (document in result) {
                    val term = document.getString("terms") ?: ""
                    val meaning = document.getString("meaning") ?: ""
                    terms.add(CryptoTerm(term, meaning))
                }
                _cryptoTermList.value = terms
            }
            .addOnFailureListener{exception->
                Log.e("DictionaryFragment", "Verileri yüklerken hata oluştu: $exception")
            }
    }
}