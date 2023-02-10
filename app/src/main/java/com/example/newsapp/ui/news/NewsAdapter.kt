package com.example.newsapp.ui.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.databinding.ItemNewsBinding
import com.example.newsapp.model.ArticlesItem

class NewsAdapter(var items: List<ArticlesItem?>?) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {


    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val view=LayoutInflater.from(parent.context)
//            .inflate(R.layout.item_news,parent,false)
//        return ViewHolder(view)
        val viewBinding: ItemNewsBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_news,
            parent,
            false
        )
        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.title.setText(item?.title)
//        holder.author.setText(item?.author)
//        holder.dateTime.setText(item?.publishedAt)

        val item = items?.get(position)
        holder.bind(item)
//        Glide.with(holder.itemView)
//            .load(item?.urlToImage)
//            .into(holder.itemBinding.image)
    }

    fun changeData(data: List<ArticlesItem?>?) {
        items = data
        notifyDataSetChanged()
    }

    class ViewHolder(val itemBinding: ItemNewsBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(item: ArticlesItem?) {
            itemBinding.item = item
            itemBinding.invalidateAll()
        }
    }


}