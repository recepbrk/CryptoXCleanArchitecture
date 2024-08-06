package com.recepguzel.cryptoxcleanarchitecture.ui.news.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.recepguzel.cryptoxcleanarchitecture.data.model.NewsResponse
import com.recepguzel.cryptoxcleanarchitecture.databinding.NewsItemBinding

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.CryptoNewsViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<NewsResponse.Article>() {
        override fun areItemsTheSame(
            oldItem: NewsResponse.Article,
            newItem: NewsResponse.Article
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: NewsResponse.Article,
            newItem: NewsResponse.Article
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    inner class CryptoNewsViewHolder(private val binding: NewsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(news: NewsResponse.Article) {
            binding.apply {
                tvTitle.text = news.title
                tvNewsDetails.text = news.description
                tvReference.text = news.source.name
                Glide.with(imgNews.context).load(news.urlToImage).into(imgNews)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoNewsViewHolder {
        val binding = NewsItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CryptoNewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CryptoNewsViewHolder, position: Int) {
        val news = differ.currentList[position]
        holder.bind(news)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}
