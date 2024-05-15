package com.recepguzel.cryptoxcleanarchitecture.ui.details

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.recepguzel.cryptoxcleanarchitecture.R
import com.recepguzel.cryptoxcleanarchitecture.databinding.FragmentCoinDetailBinding
import com.recepguzel.cryptoxcleanarchitecture.ui.home.coinlist.viewmodel.CoinListViewModel
import java.text.DecimalFormat


class CoinDetailFragment : Fragment() {
    private lateinit var binding: FragmentCoinDetailBinding
    private val args: CoinDetailFragmentArgs by navArgs()
    private val coinListViewModel: CoinListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCoinDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        backButton()


        binding.apply {


            coinDetailToolbarTitle.text =
                "${args.argDetails.symbol}/${args.argDetails.quotes.firstOrNull()?.name}"

            val priceFormat = DecimalFormat("#,##0.#####")
            coinDetailsPrice.text =
                "${priceFormat.format(args.argDetails.quotes.firstOrNull()?.price)}$"

            val percentChange24HoursFormat = DecimalFormat("#.###")
            val percentChange24Hours: Double? =
                args.argDetails.quotes.firstOrNull()?.percentChange24Hours


            if (percentChange24Hours != null) {
                if (percentChange24Hours >= 0.0) {
                    Log.d("changecolor", "Yeşil renk seçildi.")
                    coinDetails24hours.setTextColor(Color.parseColor("#25cf86"))
                    coinArrowImage.setImageResource(R.drawable.arrow_up)
                    coinDetails24hours.text =
                        percentChange24HoursFormat.format(args.argDetails.quotes.firstOrNull()?.percentChange24Hours)

                } else {
                    Log.d("changecolor", "kırmızı renk seçildi.")
                    coinDetails24hours.setTextColor(Color.parseColor("#de2b4a"))
                    coinArrowImage.setImageResource(R.drawable.arrow_down)
                    coinDetails24hours.text =
                        percentChange24HoursFormat.format(args.argDetails.quotes.firstOrNull()?.percentChange24Hours)

                }
                val coinId = args.argDetails.id
                val imageUrl = "https://s2.coinmarketcap.com/static/img/coins/64x64/$coinId.png"
                Glide.with(this@CoinDetailFragment).load(imageUrl).into(coinDetailImage)

                val webSettings: WebSettings = coinDetailsWebview.settings
                webSettings.javaScriptEnabled = true
                coinDetailsWebview.loadUrl("https://www.tradingview.com/chart/?symbol=${args.argDetails.symbol}${args.argDetails.quotes.firstOrNull()?.name}")


                coinDetailsMarketRank.text = args.argDetails.rank.toString()
                coinDetailsMarketCap.text =
                    args.argDetails.quotes.firstOrNull()?.marketCap.toString() + "$"
                coinDetailsDominance.text =
                    args.argDetails.quotes.firstOrNull()?.dominance.toString()

                coinDetails24hChange.text = "%" + percentChange24HoursFormat.format(
                    args.argDetails.quotes.firstOrNull()?.percentChange24Hours.toString()
                        .toDouble()
                )
                coinDetails7dChange.text = "%" + percentChange24HoursFormat.format(
                    args.argDetails.quotes.firstOrNull()?.percentChange7Days.toString()
                        .toDouble()
                )
                coinDetails1mChange.text = "%" + percentChange24HoursFormat.format(
                    args.argDetails.quotes.firstOrNull()?.percentChange30Days.toString()
                        .toDouble()
                )


                favoriteIcon.setOnClickListener {
                    try {
                        coinListViewModel.saveCoin(args.argDetails)
                        Toast.makeText(context, "Coin added to favorites.", Toast.LENGTH_SHORT)
                            .show()

                        // Favoriye eklenen coin'in durumunu kontrol etmek
                        val isFav = args.argDetails.isFav
                        // Coin favorilere eklenmediyse ve favorilere eklenmişse, görünümü güncelle
                        if (!args.fromFavorites && isFav) {
                            binding.favoriteIcon.setImageResource(R.drawable.star_fill)
                        }
                    } catch (e: Exception) {
                        // Favoriye ekleme işlemi sırasında hata oluşursa hatayı yazdır
                        e.printStackTrace()
                        // Kullanıcıya hata mesajını göster
                        Toast.makeText(
                            context,
                            "An error occurred while adding coin to favorites.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }


    private fun backButton() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

}