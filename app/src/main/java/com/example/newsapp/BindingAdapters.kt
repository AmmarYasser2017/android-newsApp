package com.example.newsapp

import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("app:imageUrl")
fun loadImageFromUrl(imageView: ImageView, url: String) {
    Glide.with(imageView)
        .load(url)
        .into(imageView)
}

@BindingAdapter("app:imageResource")
fun setImageResource(imageView: ImageView, id: Int) {
    imageView.setImageResource(id)
}

@BindingAdapter("app:changeBackGroundColor")
fun changeBackGroundColor(cardView: CardView, color: Int) {
    cardView.setCardBackgroundColor(
        cardView.context.getColor(color)
    )
}