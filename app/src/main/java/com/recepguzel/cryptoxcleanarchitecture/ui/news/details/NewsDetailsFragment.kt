package com.recepguzel.cryptoxcleanarchitecture.ui.news.details

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.recepguzel.cryptoxcleanarchitecture.databinding.FragmentNewsDetailsBinding


class NewsDetailsFragment : Fragment() {
    private lateinit var binding: FragmentNewsDetailsBinding
    private val args: NewsDetailsFragmentArgs by navArgs()
    private var url = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentNewsDetailsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        webView()
        backButton()
        shareNews()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun webView() {
        url = args.argsDetail.url.toString()
        binding.webwiew.webViewClient = WebViewClient()
        binding.webwiew.loadUrl(url)
        binding.webwiew.settings.javaScriptEnabled = true
    }

    private fun backButton() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()

        }
    }

    private fun shareNews() {
        binding.shareIcon.setOnClickListener {
            Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, url)
                startActivity(Intent.createChooser(this, "Share News."))
            }

        }
    }

}
