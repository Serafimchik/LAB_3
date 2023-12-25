package com.example.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.news.databinding.NewsItemBinding
import com.squareup.picasso.Picasso

class NewsAdapter : ListAdapter<NewsModel, NewsAdapter.Holder>(Comporator()) {

    class Holder(view: View) : RecyclerView.ViewHolder(view){
        val binding = NewsItemBinding.bind(view)

        fun bind(item: NewsModel) = with(binding){
            tvCategory.text = item.category
            tvDate.text = item.date
            tvDescription.text = item.description
            tvTitel.text = item.titel
            tvLink.text = item.news_link
            Picasso.get().load(item.image_url).into(imageView)
        }
    }

    class Comporator : DiffUtil.ItemCallback<NewsModel>(){
        override fun areItemsTheSame(oldItem: NewsModel, newItem: NewsModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: NewsModel, newItem: NewsModel): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false)
        return Holder(view)

    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }
}