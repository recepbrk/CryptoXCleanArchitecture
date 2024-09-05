package com.recepguzel.cryptoxcleanarchitecture.ui.news.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.recepguzel.cryptoxcleanarchitecture.data.model.NewsResponse
import com.recepguzel.cryptoxcleanarchitecture.databinding.NewsItemBinding


private var onItemClickListener: ((NewsResponse.Article) -> Unit)? = null

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

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

    inner class NewsViewHolder(private val binding: NewsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(news: NewsResponse.Article) {
            binding.apply {
                tvTitle.text = news.title
                tvNewsDetails.text = news.description
                tvReference.text = news.source?.name
                Glide.with(imgNews.context).load(news.urlToImage).into(imgNews)

                root.setOnClickListener {
                    onItemClickListener?.let { listener ->
                        listener(news)

                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = NewsItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val news = differ.currentList[position]
        holder.bind(news)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun OnItemClickListener(listener: (NewsResponse.Article) -> Unit) {
        onItemClickListener = listener
    }


}
