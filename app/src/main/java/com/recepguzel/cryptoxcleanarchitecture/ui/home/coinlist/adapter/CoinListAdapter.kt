package com.recepguzel.cryptoxcleanarchitecture.ui.home.coinlist.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.recepguzel.cryptoxcleanarchitecture.data.model.CryptoData
import com.recepguzel.cryptoxcleanarchitecture.databinding.CoinlistItemBinding
import java.text.DecimalFormat

class CoinListAdapter() : RecyclerView.Adapter<CoinListAdapter.CoinViewHolder>() {
    private val differCallBack = object : DiffUtil.ItemCallback<CryptoData>() {
        override fun areItemsTheSame(oldItem: CryptoData, newItem: CryptoData): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: CryptoData, newItem: CryptoData): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer<CryptoData>(this, differCallBack)

    inner class CoinViewHolder(var binding: CoinlistItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("ResourceAsColor", "SetTextI18n")
        fun bind(coin: CryptoData) {
            binding.apply {
                coinTitle.text = "${coin.symbol}/${coin.quotes.firstOrNull()?.name}"

                val priceFormat = DecimalFormat("#,##0.#####")
                coinPrice.text = "${priceFormat.format(coin.quotes.firstOrNull()?.price)}$"

                val percentChange24HoursFormat = DecimalFormat("#.###")
                val percentChange24Hours: Double? = coin.quotes.firstOrNull()?.percentChange24Hours


                if (percentChange24Hours != null) {
                    if (percentChange24Hours >= 0.0) {
                        Log.d("changecolor", "Yeşil renk seçildi.")
                        coinCard.setCardBackgroundColor(Color.parseColor("#25cf86"))
                        coin24price.text =
                            percentChange24HoursFormat.format(coin.quotes.firstOrNull()?.percentChange24Hours)

                    } else {
                        Log.d("changecolor", "kırmızı renk seçildi.")
                        coinCard.setCardBackgroundColor(Color.parseColor("#de2b4a"))
                        coin24price.text =
                            percentChange24HoursFormat.format(coin.quotes.firstOrNull()?.percentChange24Hours)

                    }

                    val coinId = coin.id
                    val imageUrl = "https://s2.coinmarketcap.com/static/img/coins/64x64/$coinId.png"
                    Glide.with(itemView).load(imageUrl).into(coinImage)


                    binding.root.setOnClickListener {
                        onItemClickListener?.let {
                            it(coin)
                        }
                    }
                }

            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
        return CoinViewHolder(
            CoinlistItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    private var onItemClickListener: ((CryptoData) -> Unit)? = null

    fun setOnItemClickListener(listener: (CryptoData) -> Unit) {
        onItemClickListener = listener
    }

}